package org.example.sit.rest.frontend;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.AbstractMap;
import java.util.Map;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.example.sit.common.resources.ResourceHandler;
import org.example.sit.rest.frontend.dto.DtoHelper;
import org.example.sit.rest.frontend.dto.ResourceFDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests for {@code ResourceF}.
 */
@ExtendWith(MockitoExtension.class)
class ResourceFTest {
   @Mock
   private ResourceHandler<ResourceFDto> resourceHandler;
   
   @InjectMocks
   private ResourceF resourceF;
   
   @Test
   @SuppressWarnings("unchecked")
   void test_getAvailability_Success() {
      final Response response = this.resourceF.getAvailability();
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.OK);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(Map.class);
      final Map<Object, Object> entity = (Map<Object, Object>) response.getEntity();
      assertThat(entity).hasSize(2).containsOnlyKeys("available", "timestamp").containsEntry("available", Boolean.TRUE);
   }
   
   @Test
   void test_createResourceF_BadRequest() {
      final Response response = this.resourceF.createResourceF(null);
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.BAD_REQUEST);
      assertThat(response.hasEntity()).isFalse();
      assertThat(response.getEntity()).isNull();
   }
   
   @Test
   void test_createResourceF_Conflict() {
      final ResourceFDto resourceFDto = DtoHelper.createResourceFDto();
      when(this.resourceHandler.hasResource(resourceFDto.getIdF())).thenReturn(true);
      
      final Response response = this.resourceF.createResourceF(resourceFDto);
      
      verify(this.resourceHandler).hasResource(resourceFDto.getIdF());
      verify(this.resourceHandler, never()).addResource(anyLong(), any(ResourceFDto.class));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.CONFLICT);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("id", resourceFDto.getIdF()));
   }
   
   @Test
   void test_createResourceF_Success() {
      final ResourceFDto resourceFDto = DtoHelper.createResourceFDto();
      when(this.resourceHandler.hasResource(resourceFDto.getIdF())).thenReturn(false);
      
      final Response response = this.resourceF.createResourceF(resourceFDto);
      
      verify(this.resourceHandler).hasResource(resourceFDto.getIdF());
      verify(this.resourceHandler).addResource(resourceFDto.getIdF(), resourceFDto);
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.CREATED);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(ResourceFDto.class).isEqualTo(resourceFDto);
   }
   
   @Test
   void test_deleteResourceF_NotFound() {
      final long id = RandomUtils.nextLong();
      when(this.resourceHandler.hasResource(id)).thenReturn(false);
      
      final Response response = this.resourceF.deleteResourceF(id);
      
      verify(this.resourceHandler).hasResource(id);
      verify(this.resourceHandler, never()).removeResource(id);
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.NOT_FOUND);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("id", id));
   }
   
   @Test
   void test_deleteResourceF_Success() {
      final long id = RandomUtils.nextLong();
      when(this.resourceHandler.hasResource(id)).thenReturn(true);
      
      final Response response = this.resourceF.deleteResourceF(id);
      
      verify(this.resourceHandler).hasResource(id);
      verify(this.resourceHandler).removeResource(id);
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.OK);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("deleted", id));
   }
   
   @Test
   void test_updateResourceF_NotFound() {
      final long id = RandomUtils.nextLong();
      when(this.resourceHandler.hasResource(id)).thenReturn(false);
      
      final Response response = this.resourceF.updateResourceF(id, ResourceFDto.builder().build());
      
      verify(this.resourceHandler).hasResource(id);
      verify(this.resourceHandler, never()).getResource(id);
      verify(this.resourceHandler, never()).replaceResource(eq(id), any(ResourceFDto.class));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.NOT_FOUND);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("id", id));
   }
   
   @Test
   void test_updateResourceF_BadRequest() {
      final long id = RandomUtils.nextLong();
      
      final Response response = this.resourceF.updateResourceF(id, null);
      
      verify(this.resourceHandler, never()).hasResource(id);
      verify(this.resourceHandler, never()).getResource(id);
      verify(this.resourceHandler, never()).replaceResource(eq(id), any(ResourceFDto.class));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.BAD_REQUEST);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("id", id));
   }
   
   @Test
   void test_updateResourceF_Success() {
      final ResourceFDto resourceFDto = DtoHelper.createResourceFDto();
      when(this.resourceHandler.hasResource(resourceFDto.getIdF())).thenReturn(true);
      when(this.resourceHandler.getResource(resourceFDto.getIdF()))
            .thenReturn(DtoHelper.cloneResourceFDto(resourceFDto));
      
      final ResourceFDto resourceFDtoToUpdate = DtoHelper.createResourceFDto();
      final Response response = this.resourceF.updateResourceF(resourceFDto.getIdF(), resourceFDtoToUpdate);
      
      verify(this.resourceHandler).hasResource(resourceFDto.getIdF());
      verify(this.resourceHandler).getResource(resourceFDto.getIdF());
      verify(this.resourceHandler).replaceResource(eq(resourceFDto.getIdF()), any(ResourceFDto.class));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.ACCEPTED);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(ResourceFDto.class)
            .hasFieldOrPropertyWithValue("idF", resourceFDto.getIdF())
            .hasFieldOrPropertyWithValue("paramF1", resourceFDtoToUpdate.getParamF1())
            .hasFieldOrPropertyWithValue("paramF2", resourceFDtoToUpdate.getParamF2())
            .hasFieldOrPropertyWithValue("paramF3", resourceFDtoToUpdate.getParamF3())
            .hasFieldOrPropertyWithValue("paramF4", resourceFDtoToUpdate.getParamF4())
            .hasFieldOrPropertyWithValue("paramF5", resourceFDtoToUpdate.getParamF5())
            .hasFieldOrPropertyWithValue("paramF6", resourceFDtoToUpdate.getParamF6())
            .hasFieldOrPropertyWithValue("paramF7", resourceFDtoToUpdate.getParamF7())
            .hasFieldOrPropertyWithValue("paramF8", resourceFDtoToUpdate.getParamF8());
   }
   
   @Test
   void test_updateResourceF_OnlyParam1_Success() {
      final ResourceFDto resourceFDto = DtoHelper.createResourceFDto();
      when(this.resourceHandler.hasResource(resourceFDto.getIdF())).thenReturn(true);
      when(this.resourceHandler.getResource(resourceFDto.getIdF()))
            .thenReturn(DtoHelper.cloneResourceFDto(resourceFDto));
      
      final ResourceFDto resourceFDtoToUpdate = ResourceFDto.builder()
            .idF(resourceFDto.getIdF())
            .paramF1(RandomStringUtils.randomAlphanumeric(20))
            .build();
      final Response response = this.resourceF.updateResourceF(resourceFDto.getIdF(), resourceFDtoToUpdate);
      
      verify(this.resourceHandler).hasResource(resourceFDto.getIdF());
      verify(this.resourceHandler).getResource(resourceFDto.getIdF());
      verify(this.resourceHandler).replaceResource(eq(resourceFDto.getIdF()), any(ResourceFDto.class));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.ACCEPTED);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(ResourceFDto.class)
            .hasFieldOrPropertyWithValue("idF", resourceFDto.getIdF())
            .hasFieldOrPropertyWithValue("paramF1", resourceFDtoToUpdate.getParamF1())
            .hasFieldOrPropertyWithValue("paramF2", resourceFDto.getParamF2())
            .hasFieldOrPropertyWithValue("paramF3", resourceFDto.getParamF3())
            .hasFieldOrPropertyWithValue("paramF4", resourceFDto.getParamF4())
            .hasFieldOrPropertyWithValue("paramF5", resourceFDto.getParamF5())
            .hasFieldOrPropertyWithValue("paramF6", resourceFDto.getParamF6())
            .hasFieldOrPropertyWithValue("paramF7", resourceFDto.getParamF7())
            .hasFieldOrPropertyWithValue("paramF8", resourceFDto.getParamF8());
   }
   
   @Test
   void test_updateResourceF_OnlyParam2_Success() {
      final ResourceFDto resourceFDto = DtoHelper.createResourceFDto();
      when(this.resourceHandler.hasResource(resourceFDto.getIdF())).thenReturn(true);
      when(this.resourceHandler.getResource(resourceFDto.getIdF()))
            .thenReturn(DtoHelper.cloneResourceFDto(resourceFDto));
      
      final ResourceFDto resourceFDtoToUpdate = ResourceFDto.builder()
            .idF(resourceFDto.getIdF())
            .paramF2(RandomUtils.nextInt())
            .build();
      final Response response = this.resourceF.updateResourceF(resourceFDto.getIdF(), resourceFDtoToUpdate);
      
      verify(this.resourceHandler).hasResource(resourceFDto.getIdF());
      verify(this.resourceHandler).getResource(resourceFDto.getIdF());
      verify(this.resourceHandler).replaceResource(eq(resourceFDto.getIdF()), any(ResourceFDto.class));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.ACCEPTED);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(ResourceFDto.class)
            .hasFieldOrPropertyWithValue("idF", resourceFDto.getIdF())
            .hasFieldOrPropertyWithValue("paramF1", resourceFDto.getParamF1())
            .hasFieldOrPropertyWithValue("paramF2", resourceFDtoToUpdate.getParamF2())
            .hasFieldOrPropertyWithValue("paramF3", resourceFDto.getParamF3())
            .hasFieldOrPropertyWithValue("paramF4", resourceFDto.getParamF4())
            .hasFieldOrPropertyWithValue("paramF5", resourceFDto.getParamF5())
            .hasFieldOrPropertyWithValue("paramF6", resourceFDto.getParamF6())
            .hasFieldOrPropertyWithValue("paramF7", resourceFDto.getParamF7())
            .hasFieldOrPropertyWithValue("paramF8", resourceFDto.getParamF8());
   }
   
   @Test
   void test_updateResourceF_OnlyParam3_Success() {
      final ResourceFDto resourceFDto = DtoHelper.createResourceFDto();
      when(this.resourceHandler.hasResource(resourceFDto.getIdF())).thenReturn(true);
      when(this.resourceHandler.getResource(resourceFDto.getIdF()))
            .thenReturn(DtoHelper.cloneResourceFDto(resourceFDto));
      
      final ResourceFDto resourceFDtoToUpdate = ResourceFDto.builder()
            .idF(resourceFDto.getIdF())
            .paramF3(RandomStringUtils.randomAlphanumeric(21))
            .build();
      final Response response = this.resourceF.updateResourceF(resourceFDto.getIdF(), resourceFDtoToUpdate);
      
      verify(this.resourceHandler).hasResource(resourceFDto.getIdF());
      verify(this.resourceHandler).getResource(resourceFDto.getIdF());
      verify(this.resourceHandler).replaceResource(eq(resourceFDto.getIdF()), any(ResourceFDto.class));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.ACCEPTED);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(ResourceFDto.class)
            .hasFieldOrPropertyWithValue("idF", resourceFDto.getIdF())
            .hasFieldOrPropertyWithValue("paramF1", resourceFDto.getParamF1())
            .hasFieldOrPropertyWithValue("paramF2", resourceFDto.getParamF2())
            .hasFieldOrPropertyWithValue("paramF3", resourceFDtoToUpdate.getParamF3())
            .hasFieldOrPropertyWithValue("paramF4", resourceFDto.getParamF4())
            .hasFieldOrPropertyWithValue("paramF5", resourceFDto.getParamF5())
            .hasFieldOrPropertyWithValue("paramF6", resourceFDto.getParamF6())
            .hasFieldOrPropertyWithValue("paramF7", resourceFDto.getParamF7())
            .hasFieldOrPropertyWithValue("paramF8", resourceFDto.getParamF8());
   }
   
   @Test
   void test_updateResourceF_OnlyParam4_Success() {
      final ResourceFDto resourceFDto = DtoHelper.createResourceFDto();
      when(this.resourceHandler.hasResource(resourceFDto.getIdF())).thenReturn(true);
      when(this.resourceHandler.getResource(resourceFDto.getIdF()))
            .thenReturn(DtoHelper.cloneResourceFDto(resourceFDto));
      
      final ResourceFDto resourceFDtoToUpdate = ResourceFDto.builder()
            .idF(resourceFDto.getIdF())
            .paramF4(RandomStringUtils.randomAlphanumeric(22))
            .build();
      final Response response = this.resourceF.updateResourceF(resourceFDto.getIdF(), resourceFDtoToUpdate);
      
      verify(this.resourceHandler).hasResource(resourceFDto.getIdF());
      verify(this.resourceHandler).getResource(resourceFDto.getIdF());
      verify(this.resourceHandler).replaceResource(eq(resourceFDto.getIdF()), any(ResourceFDto.class));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.ACCEPTED);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(ResourceFDto.class)
            .hasFieldOrPropertyWithValue("idF", resourceFDto.getIdF())
            .hasFieldOrPropertyWithValue("paramF1", resourceFDto.getParamF1())
            .hasFieldOrPropertyWithValue("paramF2", resourceFDto.getParamF2())
            .hasFieldOrPropertyWithValue("paramF3", resourceFDto.getParamF3())
            .hasFieldOrPropertyWithValue("paramF4", resourceFDtoToUpdate.getParamF4())
            .hasFieldOrPropertyWithValue("paramF5", resourceFDto.getParamF5())
            .hasFieldOrPropertyWithValue("paramF6", resourceFDto.getParamF6())
            .hasFieldOrPropertyWithValue("paramF7", resourceFDto.getParamF7())
            .hasFieldOrPropertyWithValue("paramF8", resourceFDto.getParamF8());
   }
   
   @Test
   void test_updateResourceF_OnlyParam5_Success() {
      final ResourceFDto resourceFDto = DtoHelper.createResourceFDto();
      when(this.resourceHandler.hasResource(resourceFDto.getIdF())).thenReturn(true);
      when(this.resourceHandler.getResource(resourceFDto.getIdF()))
            .thenReturn(DtoHelper.cloneResourceFDto(resourceFDto));
      
      final ResourceFDto resourceFDtoToUpdate = ResourceFDto.builder()
            .idF(resourceFDto.getIdF())
            .paramF5(!resourceFDto.getParamF5())
            .build();
      final Response response = this.resourceF.updateResourceF(resourceFDto.getIdF(), resourceFDtoToUpdate);
      
      verify(this.resourceHandler).hasResource(resourceFDto.getIdF());
      verify(this.resourceHandler).getResource(resourceFDto.getIdF());
      verify(this.resourceHandler).replaceResource(eq(resourceFDto.getIdF()), any(ResourceFDto.class));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.ACCEPTED);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(ResourceFDto.class)
            .hasFieldOrPropertyWithValue("idF", resourceFDto.getIdF())
            .hasFieldOrPropertyWithValue("paramF1", resourceFDto.getParamF1())
            .hasFieldOrPropertyWithValue("paramF2", resourceFDto.getParamF2())
            .hasFieldOrPropertyWithValue("paramF3", resourceFDto.getParamF3())
            .hasFieldOrPropertyWithValue("paramF4", resourceFDto.getParamF4())
            .hasFieldOrPropertyWithValue("paramF5", resourceFDtoToUpdate.getParamF5())
            .hasFieldOrPropertyWithValue("paramF6", resourceFDto.getParamF6())
            .hasFieldOrPropertyWithValue("paramF7", resourceFDto.getParamF7())
            .hasFieldOrPropertyWithValue("paramF8", resourceFDto.getParamF8());
   }
   
   @Test
   void test_updateResourceF_OnlyParam6_Success() {
      final ResourceFDto resourceFDto = DtoHelper.createResourceFDto();
      when(this.resourceHandler.hasResource(resourceFDto.getIdF())).thenReturn(true);
      when(this.resourceHandler.getResource(resourceFDto.getIdF()))
            .thenReturn(DtoHelper.cloneResourceFDto(resourceFDto));
      
      final ResourceFDto resourceFDtoToUpdate = ResourceFDto.builder()
            .idF(resourceFDto.getIdF())
            .paramF6(RandomUtils.nextLong())
            .build();
      final Response response = this.resourceF.updateResourceF(resourceFDto.getIdF(), resourceFDtoToUpdate);
      
      verify(this.resourceHandler).hasResource(resourceFDto.getIdF());
      verify(this.resourceHandler).getResource(resourceFDto.getIdF());
      verify(this.resourceHandler).replaceResource(eq(resourceFDto.getIdF()), any(ResourceFDto.class));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.ACCEPTED);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(ResourceFDto.class)
            .hasFieldOrPropertyWithValue("idF", resourceFDto.getIdF())
            .hasFieldOrPropertyWithValue("paramF1", resourceFDto.getParamF1())
            .hasFieldOrPropertyWithValue("paramF2", resourceFDto.getParamF2())
            .hasFieldOrPropertyWithValue("paramF3", resourceFDto.getParamF3())
            .hasFieldOrPropertyWithValue("paramF4", resourceFDto.getParamF4())
            .hasFieldOrPropertyWithValue("paramF5", resourceFDto.getParamF5())
            .hasFieldOrPropertyWithValue("paramF6", resourceFDtoToUpdate.getParamF6())
            .hasFieldOrPropertyWithValue("paramF7", resourceFDto.getParamF7())
            .hasFieldOrPropertyWithValue("paramF8", resourceFDto.getParamF8());
   }
   
   @Test
   void test_updateResourceF_OnlyParam7_Success() {
      final ResourceFDto resourceFDto = DtoHelper.createResourceFDto();
      when(this.resourceHandler.hasResource(resourceFDto.getIdF())).thenReturn(true);
      when(this.resourceHandler.getResource(resourceFDto.getIdF()))
            .thenReturn(DtoHelper.cloneResourceFDto(resourceFDto));
      
      final ResourceFDto resourceFDtoToUpdate = ResourceFDto.builder()
            .idF(resourceFDto.getIdF())
            .paramF7(RandomStringUtils.randomAlphanumeric(23))
            .build();
      final Response response = this.resourceF.updateResourceF(resourceFDto.getIdF(), resourceFDtoToUpdate);
      
      verify(this.resourceHandler).hasResource(resourceFDto.getIdF());
      verify(this.resourceHandler).getResource(resourceFDto.getIdF());
      verify(this.resourceHandler).replaceResource(eq(resourceFDto.getIdF()), any(ResourceFDto.class));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.ACCEPTED);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(ResourceFDto.class)
            .hasFieldOrPropertyWithValue("idF", resourceFDto.getIdF())
            .hasFieldOrPropertyWithValue("paramF1", resourceFDto.getParamF1())
            .hasFieldOrPropertyWithValue("paramF2", resourceFDto.getParamF2())
            .hasFieldOrPropertyWithValue("paramF3", resourceFDto.getParamF3())
            .hasFieldOrPropertyWithValue("paramF4", resourceFDto.getParamF4())
            .hasFieldOrPropertyWithValue("paramF5", resourceFDto.getParamF5())
            .hasFieldOrPropertyWithValue("paramF6", resourceFDto.getParamF6())
            .hasFieldOrPropertyWithValue("paramF7", resourceFDtoToUpdate.getParamF7())
            .hasFieldOrPropertyWithValue("paramF8", resourceFDto.getParamF8());
   }
   
   @Test
   void test_updateResourceF_OnlyParam8_Success() {
      final ResourceFDto resourceFDto = DtoHelper.createResourceFDto();
      when(this.resourceHandler.hasResource(resourceFDto.getIdF())).thenReturn(true);
      when(this.resourceHandler.getResource(resourceFDto.getIdF()))
            .thenReturn(DtoHelper.cloneResourceFDto(resourceFDto));
      
      final ResourceFDto resourceFDtoToUpdate = ResourceFDto.builder()
            .idF(resourceFDto.getIdF())
            .paramF8(DtoHelper.createResourceFDtoParam8List())
            .build();
      final Response response = this.resourceF.updateResourceF(resourceFDtoToUpdate.getIdF(), resourceFDtoToUpdate);
      
      verify(this.resourceHandler).hasResource(resourceFDto.getIdF());
      verify(this.resourceHandler).getResource(resourceFDto.getIdF());
      verify(this.resourceHandler).replaceResource(eq(resourceFDto.getIdF()), any(ResourceFDto.class));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.ACCEPTED);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(ResourceFDto.class)
            .hasFieldOrPropertyWithValue("idF", resourceFDto.getIdF())
            .hasFieldOrPropertyWithValue("paramF1", resourceFDto.getParamF1())
            .hasFieldOrPropertyWithValue("paramF2", resourceFDto.getParamF2())
            .hasFieldOrPropertyWithValue("paramF3", resourceFDto.getParamF3())
            .hasFieldOrPropertyWithValue("paramF4", resourceFDto.getParamF4())
            .hasFieldOrPropertyWithValue("paramF5", resourceFDto.getParamF5())
            .hasFieldOrPropertyWithValue("paramF6", resourceFDto.getParamF6())
            .hasFieldOrPropertyWithValue("paramF7", resourceFDto.getParamF7())
            .hasFieldOrPropertyWithValue("paramF8", resourceFDtoToUpdate.getParamF8());
   }
}
