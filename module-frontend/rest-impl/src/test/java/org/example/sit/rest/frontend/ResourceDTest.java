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
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.example.sit.common.resources.ResourceHandler;
import org.example.sit.rest.frontend.dto.DtoHelper;
import org.example.sit.rest.frontend.dto.ResourceDDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Tests for {@code ResourceD}.
 */
@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings({"javadoc", "boxing"})
public class ResourceDTest {
   @Mock
   private ResourceHandler<ResourceDDto> resources;
   
   @InjectMocks
   private ResourceD resourceD;
   
   @Test
   public void test_getAllResourceD_NotFound() {
      when(this.resources.hasResources()).thenReturn(false);
      
      final Response response = this.resourceD.getAllResourceD();
      
      verify(this.resources).hasResources();
      verify(this.resources, never()).getAllResources();
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.NOT_FOUND);
      assertThat(response.hasEntity()).isFalse();
      assertThat(response.getEntity()).isNull();
   }
   
   @Test
   public void test_getAllResourceD_Success() {
      when(this.resources.hasResources()).thenReturn(true);
      final ResourceDDto resourceDDto1 = DtoHelper.createResourceDDto();
      final ResourceDDto resourceDDto2 = DtoHelper.createResourceDDto();
      final ResourceDDto resourceDDto3 = DtoHelper.createResourceDDto();
      when(this.resources.getAllResources()).thenReturn(Arrays.asList(resourceDDto1, resourceDDto2,
            resourceDDto3));
      
      final Response response = this.resourceD.getAllResourceD();
      
      verify(this.resources).hasResources();
      verify(this.resources).getAllResources();
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.OK);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(List.class).asList()
            .containsExactly(resourceDDto1, resourceDDto2, resourceDDto3);
   }
   
   @Test
   public void test_createResourceD_BadRequest() {
      final Response response = this.resourceD.createResourceD(null);
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.BAD_REQUEST);
      assertThat(response.hasEntity()).isFalse();
      assertThat(response.getEntity()).isNull();
   }
   
   @Test
   public void test_createResourceD_Conflict() {
      final ResourceDDto resourceDDto = DtoHelper.createResourceDDto();
      when(this.resources.hasResource(resourceDDto.getIdD())).thenReturn(true);
      
      final Response response = this.resourceD.createResourceD(resourceDDto);
      
      verify(this.resources).hasResource(eq(resourceDDto.getIdD()));
      verify(this.resources, never()).addResource(anyLong(), any(ResourceDDto.class));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.CONFLICT);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("id", resourceDDto.getIdD()));
   }
   
   @Test
   public void test_createResourceD_Success() {
      final ResourceDDto resourceDDto = DtoHelper.createResourceDDto();
      when(this.resources.hasResource(resourceDDto.getIdD())).thenReturn(false);
      
      final Response response = this.resourceD.createResourceD(resourceDDto);
      
      verify(this.resources).hasResource(eq(resourceDDto.getIdD()));
      verify(this.resources).addResource(eq(resourceDDto.getIdD()), eq(resourceDDto));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.CREATED);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(ResourceDDto.class)
            .isEqualTo(resourceDDto);
   }
   
   @Test
   public void test_getResourceD_NotFound() {
      final long id = RandomUtils.nextLong();
      when(this.resources.hasResource(id)).thenReturn(false);
      
      final Response response = this.resourceD.getResourceD(id);
      
      verify(this.resources).hasResource(eq(id));
      verify(this.resources, never()).getResource(id);
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.NOT_FOUND);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("id", id));
   }
   
   @Test
   public void test_getResourceD_Success() {
      final ResourceDDto resourceDDto = DtoHelper.createResourceDDto();
      when(this.resources.hasResource(resourceDDto.getIdD())).thenReturn(true);
      when(this.resources.getResource(resourceDDto.getIdD()))
            .thenReturn(DtoHelper.cloneResourceDDto(resourceDDto));
      
      final Response response = this.resourceD.getResourceD(resourceDDto.getIdD());
      
      verify(this.resources).hasResource(eq(resourceDDto.getIdD()));
      verify(this.resources).getResource(eq(resourceDDto.getIdD()));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.OK);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(ResourceDDto.class)
            .isEqualTo(resourceDDto);
   }
   
   @Test
   public void test_deleteResourceD_NotFound() {
      final long id = RandomUtils.nextLong();
      when(this.resources.hasResource(id)).thenReturn(false);
      
      final Response response = this.resourceD.deleteResourceD(id);
      
      verify(this.resources).hasResource(eq(id));
      verify(this.resources, never()).removeResource(eq(id));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.NOT_FOUND);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("id", id));
   }
   
   @Test
   public void test_deleteResourceD_Success() {
      final long id = RandomUtils.nextLong();
      when(this.resources.hasResource(id)).thenReturn(true);
      
      final Response response = this.resourceD.deleteResourceD(id);
      
      verify(this.resources).hasResource(eq(id));
      verify(this.resources).removeResource(eq(id));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.OK);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("deleted", id));
   }
   
   @Test
   public void test_updateResourceD_NotFound() {
      final long id = RandomUtils.nextLong();
      when(this.resources.hasResource(id)).thenReturn(false);
      
      final Response response = this.resourceD.updateResourceD(id, null);
      
      verify(this.resources).hasResource(eq(id));
      verify(this.resources, never()).getResource(eq(id));
      verify(this.resources, never()).replaceResource(eq(id), any(ResourceDDto.class));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.NOT_FOUND);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("id", id));
   }
   
   @Test
   public void test_updateResourceD_BadRequest() {
      final long id = RandomUtils.nextLong();
      when(this.resources.hasResource(id)).thenReturn(true);
      
      final Response response = this.resourceD.updateResourceD(id, null);
      
      verify(this.resources).hasResource(eq(id));
      verify(this.resources, never()).getResource(eq(id));
      verify(this.resources, never()).replaceResource(eq(id), any(ResourceDDto.class));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.BAD_REQUEST);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("id", id));
   }
   
   @Test
   public void test_updateResourceD_Success() {
      final ResourceDDto resourceDDto = DtoHelper.createResourceDDto();
      when(this.resources.hasResource(resourceDDto.getIdD())).thenReturn(true);
      when(this.resources.getResource(resourceDDto.getIdD()))
            .thenReturn(DtoHelper.cloneResourceDDto(resourceDDto));
      
      final ResourceDDto resourceDDtoToUpdate = DtoHelper.createResourceDDto();
      final Response response = this.resourceD.updateResourceD(resourceDDto.getIdD(),
            resourceDDtoToUpdate);
      
      verify(this.resources).hasResource(eq(resourceDDto.getIdD()));
      verify(this.resources).getResource(eq(resourceDDto.getIdD()));
      verify(this.resources).replaceResource(eq(resourceDDto.getIdD()), any(ResourceDDto.class));
      
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
   public void test_updateResourceD_OnlyParam1_Success() {
      final ResourceDDto resourceDDto = DtoHelper.createResourceDDto();
      when(this.resources.hasResource(resourceDDto.getIdD())).thenReturn(true);
      when(this.resources.getResource(resourceDDto.getIdD()))
            .thenReturn(DtoHelper.cloneResourceDDto(resourceDDto));
      
      final ResourceDDto resourceDDtoToUpdate = new ResourceDDto(resourceDDto.getIdD())
            .setParamD1(RandomStringUtils.randomAlphanumeric(12));
      final Response response = this.resourceD.updateResourceD(resourceDDto.getIdD(),
            resourceDDtoToUpdate);
      
      verify(this.resources).hasResource(eq(resourceDDto.getIdD()));
      verify(this.resources).getResource(eq(resourceDDto.getIdD()));
      verify(this.resources).replaceResource(eq(resourceDDto.getIdD()), any(ResourceDDto.class));
      
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
   public void test_updateResourceD_OnlyParam2_Success() {
      final ResourceDDto resourceDDto = DtoHelper.createResourceDDto();
      when(this.resources.hasResource(resourceDDto.getIdD())).thenReturn(true);
      when(this.resources.getResource(resourceDDto.getIdD()))
            .thenReturn(DtoHelper.cloneResourceDDto(resourceDDto));
      
      final ResourceDDto resourceDDtoToUpdate = new ResourceDDto(resourceDDto.getIdD())
            .setParamD2(RandomUtils.nextInt());
      final Response response = this.resourceD.updateResourceD(resourceDDto.getIdD(),
            resourceDDtoToUpdate);
      
      verify(this.resources).hasResource(eq(resourceDDto.getIdD()));
      verify(this.resources).getResource(eq(resourceDDto.getIdD()));
      verify(this.resources).replaceResource(eq(resourceDDto.getIdD()), any(ResourceDDto.class));
      
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
   public void test_updateResourceD_OnlyParam3_Success() {
      final ResourceDDto resourceDDto = DtoHelper.createResourceDDto();
      when(this.resources.hasResource(resourceDDto.getIdD())).thenReturn(true);
      when(this.resources.getResource(resourceDDto.getIdD()))
            .thenReturn(DtoHelper.cloneResourceDDto(resourceDDto));
      
      final ResourceDDto resourceDDtoToUpdate = new ResourceDDto(resourceDDto.getIdD())
            .setParamD3(!resourceDDto.getParamD3());
      final Response response = this.resourceD.updateResourceD(resourceDDto.getIdD(),
            resourceDDtoToUpdate);
      
      verify(this.resources).hasResource(eq(resourceDDto.getIdD()));
      verify(this.resources).getResource(eq(resourceDDto.getIdD()));
      verify(this.resources).replaceResource(eq(resourceDDto.getIdD()), any(ResourceDDto.class));
      
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
   public void test_updateResourceD_OnlyParam4_Success() {
      final ResourceDDto resourceDDto = DtoHelper.createResourceDDto();
      when(this.resources.hasResource(resourceDDto.getIdD())).thenReturn(true);
      when(this.resources.getResource(resourceDDto.getIdD()))
            .thenReturn(DtoHelper.cloneResourceDDto(resourceDDto));
      
      final ResourceDDto resourceDDtoToUpdate = new ResourceDDto(resourceDDto.getIdD())
            .setParamD4(DtoHelper.createResourceDDtoParam4List());
      final Response response = this.resourceD.updateResourceD(resourceDDto.getIdD(),
            resourceDDtoToUpdate);
      
      verify(this.resources).hasResource(eq(resourceDDto.getIdD()));
      verify(this.resources).getResource(eq(resourceDDto.getIdD()));
      verify(this.resources).replaceResource(eq(resourceDDto.getIdD()), any(ResourceDDto.class));
      
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
