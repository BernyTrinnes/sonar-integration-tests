package org.example.sit.rest.frontend;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.example.sit.common.resources.ResourceHandler;
import org.example.sit.rest.frontend.dto.DtoHelper;
import org.example.sit.rest.frontend.dto.ResourceDDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests for {@code ResourceD}.
 */
@ExtendWith(MockitoExtension.class)
class ResourceDTest {
   @Mock
   private ResourceHandler<ResourceDDto> resourceHandler;
   
   @InjectMocks
   private ResourceD resourceD;
   
   @Test
   @SuppressWarnings("unchecked")
   void test_getAvailability_Success() {
      final Response response = this.resourceD.getAvailability();
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.OK);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(Map.class);
      final Map<Object, Object> entity = (Map<Object, Object>) response.getEntity();
      assertThat(entity).hasSize(2).containsOnlyKeys("available", "timestamp").containsEntry("available", Boolean.TRUE);
   }
   
   @Test
   void test_getAllResourceD_NotFound() {
      when(this.resourceHandler.hasResources()).thenReturn(false);
      
      final Response response = this.resourceD.getAllResourceD();
      
      verify(this.resourceHandler).hasResources();
      verify(this.resourceHandler, never()).getAllResources();
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.NOT_FOUND);
      assertThat(response.hasEntity()).isFalse();
      assertThat(response.getEntity()).isNull();
   }
   
   @Test
   void test_getAllResourceD_Success() {
      when(this.resourceHandler.hasResources()).thenReturn(true);
      final ResourceDDto resourceDDto1 = DtoHelper.createResourceDDto();
      final ResourceDDto resourceDDto2 = DtoHelper.createResourceDDto();
      final ResourceDDto resourceDDto3 = DtoHelper.createResourceDDto();
      when(this.resourceHandler.getAllResources()).thenReturn(Arrays.asList(resourceDDto1, resourceDDto2,
            resourceDDto3));
      
      final Response response = this.resourceD.getAllResourceD();
      
      verify(this.resourceHandler).hasResources();
      verify(this.resourceHandler).getAllResources();
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.OK);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(List.class).asList()
            .containsExactly(resourceDDto1, resourceDDto2, resourceDDto3);
   }
   
   @Test
   void test_deleteAllResourceD_Success() {
      final Response response = this.resourceD.deleteAllResourceD();
      
      verify(this.resourceHandler).removeAllResources();
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.OK);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("cleared", Boolean.TRUE));
   }
   
   @Test
   void test_createResourceD_BadRequest() {
      final Response response = this.resourceD.createResourceD(null);
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.BAD_REQUEST);
      assertThat(response.hasEntity()).isFalse();
      assertThat(response.getEntity()).isNull();
   }
   
   @Test
   void test_createResourceD_Conflict() {
      final ResourceDDto resourceDDto = DtoHelper.createResourceDDto();
      when(this.resourceHandler.hasResource(resourceDDto.getIdD())).thenReturn(true);
      
      final Response response = this.resourceD.createResourceD(resourceDDto);
      
      verify(this.resourceHandler).hasResource(resourceDDto.getIdD());
      verify(this.resourceHandler, never()).addResource(anyLong(), any(ResourceDDto.class));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.CONFLICT);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("id", resourceDDto.getIdD()));
   }
   
   @Test
   void test_createResourceD_Success() {
      final ResourceDDto resourceDDto = DtoHelper.createResourceDDto();
      when(this.resourceHandler.hasResource(resourceDDto.getIdD())).thenReturn(false);
      
      final Response response = this.resourceD.createResourceD(resourceDDto);
      
      verify(this.resourceHandler).hasResource(resourceDDto.getIdD());
      verify(this.resourceHandler).addResource(resourceDDto.getIdD(), resourceDDto);
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.CREATED);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(ResourceDDto.class).isEqualTo(resourceDDto);
   }
   
   @Test
   void test_getResourceD_NotFound() {
      final long id = RandomUtils.nextLong();
      when(this.resourceHandler.hasResource(id)).thenReturn(false);
      
      final Response response = this.resourceD.getResourceD(id);
      
      verify(this.resourceHandler).hasResource(id);
      verify(this.resourceHandler, never()).getResource(id);
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.NOT_FOUND);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("id", id));
   }
   
   @Test
   void test_getResourceD_Success() {
      final ResourceDDto resourceDDto = DtoHelper.createResourceDDto();
      when(this.resourceHandler.hasResource(resourceDDto.getIdD())).thenReturn(true);
      when(this.resourceHandler.getResource(resourceDDto.getIdD()))
            .thenReturn(DtoHelper.cloneResourceDDto(resourceDDto));
      
      final Response response = this.resourceD.getResourceD(resourceDDto.getIdD());
      
      verify(this.resourceHandler).hasResource(resourceDDto.getIdD());
      verify(this.resourceHandler).getResource(resourceDDto.getIdD());
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.OK);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(ResourceDDto.class).isEqualTo(resourceDDto);
   }
   
   @Test
   void test_deleteResourceD_NotFound() {
      final long id = RandomUtils.nextLong();
      when(this.resourceHandler.hasResource(id)).thenReturn(false);
      
      final Response response = this.resourceD.deleteResourceD(id);
      
      verify(this.resourceHandler).hasResource(id);
      verify(this.resourceHandler, never()).removeResource(id);
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.NOT_FOUND);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("id", id));
   }
   
   @Test
   void test_deleteResourceD_Success() {
      final long id = RandomUtils.nextLong();
      when(this.resourceHandler.hasResource(id)).thenReturn(true);
      
      final Response response = this.resourceD.deleteResourceD(id);
      
      verify(this.resourceHandler).hasResource(id);
      verify(this.resourceHandler).removeResource(id);
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.OK);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("deleted", id));
   }
   
   @Test
   void test_updateResourceD_NotFound() {
      final long id = RandomUtils.nextLong();
      when(this.resourceHandler.hasResource(id)).thenReturn(false);
      
      final Response response = this.resourceD.updateResourceD(id, ResourceDDto.builder().build());
      
      verify(this.resourceHandler).hasResource(id);
      verify(this.resourceHandler, never()).getResource(id);
      verify(this.resourceHandler, never()).replaceResource(eq(id), any(ResourceDDto.class));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.NOT_FOUND);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("id", id));
   }
   
   @Test
   void test_updateResourceD_BadRequest() {
      final long id = RandomUtils.nextLong();
      
      final Response response = this.resourceD.updateResourceD(id, null);
      
      verify(this.resourceHandler, never()).hasResource(id);
      verify(this.resourceHandler, never()).getResource(id);
      verify(this.resourceHandler, never()).replaceResource(eq(id), any(ResourceDDto.class));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.BAD_REQUEST);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("id", id));
   }
   
   @Test
   void test_updateResourceD_Success() {
      final ResourceDDto resourceDDto = DtoHelper.createResourceDDto();
      when(this.resourceHandler.hasResource(resourceDDto.getIdD())).thenReturn(true);
      when(this.resourceHandler.getResource(resourceDDto.getIdD()))
            .thenReturn(DtoHelper.cloneResourceDDto(resourceDDto));
      
      final ResourceDDto resourceDDtoToUpdate = DtoHelper.createResourceDDto();
      final Response response = this.resourceD.updateResourceD(resourceDDto.getIdD(), resourceDDtoToUpdate);
      
      verify(this.resourceHandler).hasResource(resourceDDto.getIdD());
      verify(this.resourceHandler).getResource(resourceDDto.getIdD());
      verify(this.resourceHandler).replaceResource(eq(resourceDDto.getIdD()), any(ResourceDDto.class));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.ACCEPTED);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(ResourceDDto.class)
            .hasFieldOrPropertyWithValue("idD", resourceDDto.getIdD())
            .hasFieldOrPropertyWithValue("paramD1", resourceDDtoToUpdate.getParamD1())
            .hasFieldOrPropertyWithValue("paramD2", resourceDDtoToUpdate.getParamD2())
            .hasFieldOrPropertyWithValue("paramD3", resourceDDtoToUpdate.getParamD3())
            .hasFieldOrPropertyWithValue("paramD4", resourceDDtoToUpdate.getParamD4());
   }
   
   @Test
   void test_updateResourceD_OnlyParam1_Success() {
      final ResourceDDto resourceDDto = DtoHelper.createResourceDDto();
      when(this.resourceHandler.hasResource(resourceDDto.getIdD())).thenReturn(true);
      when(this.resourceHandler.getResource(resourceDDto.getIdD()))
            .thenReturn(DtoHelper.cloneResourceDDto(resourceDDto));
      
      final ResourceDDto resourceDDtoToUpdate = ResourceDDto.builder()
            .idD(resourceDDto.getIdD())
            .paramD1(RandomStringUtils.randomAlphanumeric(12))
            .build();
      final Response response = this.resourceD.updateResourceD(resourceDDto.getIdD(), resourceDDtoToUpdate);
      
      verify(this.resourceHandler).hasResource(resourceDDto.getIdD());
      verify(this.resourceHandler).getResource(resourceDDto.getIdD());
      verify(this.resourceHandler).replaceResource(eq(resourceDDto.getIdD()), any(ResourceDDto.class));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.ACCEPTED);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(ResourceDDto.class)
            .hasFieldOrPropertyWithValue("idD", resourceDDto.getIdD())
            .hasFieldOrPropertyWithValue("paramD1", resourceDDtoToUpdate.getParamD1())
            .hasFieldOrPropertyWithValue("paramD2", resourceDDto.getParamD2())
            .hasFieldOrPropertyWithValue("paramD3", resourceDDto.getParamD3())
            .hasFieldOrPropertyWithValue("paramD4", resourceDDto.getParamD4());
   }
   
   @Test
   void test_updateResourceD_OnlyParam2_Success() {
      final ResourceDDto resourceDDto = DtoHelper.createResourceDDto();
      when(this.resourceHandler.hasResource(resourceDDto.getIdD())).thenReturn(true);
      when(this.resourceHandler.getResource(resourceDDto.getIdD()))
            .thenReturn(DtoHelper.cloneResourceDDto(resourceDDto));
      
      final ResourceDDto resourceDDtoToUpdate = ResourceDDto.builder()
            .idD(resourceDDto.getIdD())
            .paramD2(RandomUtils.nextInt())
            .build();
      final Response response = this.resourceD.updateResourceD(resourceDDto.getIdD(), resourceDDtoToUpdate);
      
      verify(this.resourceHandler).hasResource(resourceDDto.getIdD());
      verify(this.resourceHandler).getResource(resourceDDto.getIdD());
      verify(this.resourceHandler).replaceResource(eq(resourceDDto.getIdD()), any(ResourceDDto.class));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.ACCEPTED);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(ResourceDDto.class)
            .hasFieldOrPropertyWithValue("idD", resourceDDto.getIdD())
            .hasFieldOrPropertyWithValue("paramD1", resourceDDto.getParamD1())
            .hasFieldOrPropertyWithValue("paramD2", resourceDDtoToUpdate.getParamD2())
            .hasFieldOrPropertyWithValue("paramD3", resourceDDto.getParamD3())
            .hasFieldOrPropertyWithValue("paramD4", resourceDDto.getParamD4());
   }
   
   @Test
   void test_updateResourceD_OnlyParam3_Success() {
      final ResourceDDto resourceDDto = DtoHelper.createResourceDDto();
      when(this.resourceHandler.hasResource(resourceDDto.getIdD())).thenReturn(true);
      when(this.resourceHandler.getResource(resourceDDto.getIdD()))
            .thenReturn(DtoHelper.cloneResourceDDto(resourceDDto));
      
      final ResourceDDto resourceDDtoToUpdate = ResourceDDto.builder()
            .idD(resourceDDto.getIdD())
            .paramD3(!resourceDDto.getParamD3())
            .build();
      final Response response = this.resourceD.updateResourceD(resourceDDto.getIdD(), resourceDDtoToUpdate);
      
      verify(this.resourceHandler).hasResource(resourceDDto.getIdD());
      verify(this.resourceHandler).getResource(resourceDDto.getIdD());
      verify(this.resourceHandler).replaceResource(eq(resourceDDto.getIdD()), any(ResourceDDto.class));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.ACCEPTED);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(ResourceDDto.class)
            .hasFieldOrPropertyWithValue("idD", resourceDDto.getIdD())
            .hasFieldOrPropertyWithValue("paramD1", resourceDDto.getParamD1())
            .hasFieldOrPropertyWithValue("paramD2", resourceDDto.getParamD2())
            .hasFieldOrPropertyWithValue("paramD3", resourceDDtoToUpdate.getParamD3())
            .hasFieldOrPropertyWithValue("paramD4", resourceDDto.getParamD4());
   }
   
   @Test
   void test_updateResourceD_OnlyParam4_Success() {
      final ResourceDDto resourceDDto = DtoHelper.createResourceDDto();
      when(this.resourceHandler.hasResource(resourceDDto.getIdD())).thenReturn(true);
      when(this.resourceHandler.getResource(resourceDDto.getIdD()))
            .thenReturn(DtoHelper.cloneResourceDDto(resourceDDto));
      
      final ResourceDDto resourceDDtoToUpdate = ResourceDDto.builder()
            .idD(resourceDDto.getIdD())
            .paramD4(DtoHelper.createResourceDDtoParam4List())
            .build();
      final Response response = this.resourceD.updateResourceD(resourceDDto.getIdD(), resourceDDtoToUpdate);
      
      verify(this.resourceHandler).hasResource(resourceDDto.getIdD());
      verify(this.resourceHandler).getResource(resourceDDto.getIdD());
      verify(this.resourceHandler).replaceResource(eq(resourceDDto.getIdD()), any(ResourceDDto.class));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.ACCEPTED);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(ResourceDDto.class)
            .hasFieldOrPropertyWithValue("idD", resourceDDto.getIdD())
            .hasFieldOrPropertyWithValue("paramD1", resourceDDto.getParamD1())
            .hasFieldOrPropertyWithValue("paramD2", resourceDDto.getParamD2())
            .hasFieldOrPropertyWithValue("paramD3", resourceDDto.getParamD3())
            .hasFieldOrPropertyWithValue("paramD4", resourceDDtoToUpdate.getParamD4());
   }
}
