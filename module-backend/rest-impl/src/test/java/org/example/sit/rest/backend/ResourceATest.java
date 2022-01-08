package org.example.sit.rest.backend;

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
import org.example.sit.rest.backend.dto.DtoHelper;
import org.example.sit.rest.backend.dto.ResourceADto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests for {@code ResourceA}.
 */
@ExtendWith(MockitoExtension.class)
class ResourceATest {
   @Mock
   private ResourceHandler<ResourceADto> resourceHandler;
   
   @InjectMocks
   private ResourceA resourceA;
   
   @Test
   @SuppressWarnings("unchecked")
   void test_getAvailability_Success() {
      final Response response = this.resourceA.getAvailability();
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.OK);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(Map.class);
      final Map<Object, Object> entity = (Map<Object, Object>) response.getEntity();
      assertThat(entity).hasSize(2).containsOnlyKeys("available", "timestamp").containsEntry("available", Boolean.TRUE);
   }
   
   @Test
   void test_getAllResourceA_NotFound() {
      when(this.resourceHandler.hasResources()).thenReturn(false);
      
      final Response response = this.resourceA.getAllResourceA();
      
      verify(this.resourceHandler).hasResources();
      verify(this.resourceHandler, never()).getAllResources();
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.NOT_FOUND);
      assertThat(response.hasEntity()).isFalse();
      assertThat(response.getEntity()).isNull();
   }
   
   @Test
   void test_getAllResourceA_Success() {
      when(this.resourceHandler.hasResources()).thenReturn(true);
      final ResourceADto resourceADto1 = DtoHelper.createResourceADto();
      final ResourceADto resourceADto2 = DtoHelper.createResourceADto();
      final ResourceADto resourceADto3 = DtoHelper.createResourceADto();
      when(this.resourceHandler.getAllResources()).thenReturn(Arrays.asList(resourceADto1, resourceADto2,
            resourceADto3));
      
      final Response response = this.resourceA.getAllResourceA();
      
      verify(this.resourceHandler).hasResources();
      verify(this.resourceHandler).getAllResources();
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.OK);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(List.class).asList()
            .containsExactly(resourceADto1, resourceADto2, resourceADto3);
   }
   
   @Test
   void test_deleteAllResourceA_Success() {
      final Response response = this.resourceA.deleteAllResourceA();
      
      verify(this.resourceHandler).removeAllResources();
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.OK);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("cleared", Boolean.TRUE));
   }
   
   @Test
   void test_createResourceA_BadRequest() {
      final Response response = this.resourceA.createResourceA(null);
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.BAD_REQUEST);
      assertThat(response.hasEntity()).isFalse();
      assertThat(response.getEntity()).isNull();
   }
   
   @Test
   void test_createResourceA_Conflict() {
      final ResourceADto resourceADto = DtoHelper.createResourceADto();
      when(this.resourceHandler.hasResource(resourceADto.getIdA())).thenReturn(true);
      
      final Response response = this.resourceA.createResourceA(resourceADto);
      
      verify(this.resourceHandler).hasResource(resourceADto.getIdA());
      verify(this.resourceHandler, never()).addResource(anyLong(), any(ResourceADto.class));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.CONFLICT);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("id", resourceADto.getIdA()));
   }
   
   @Test
   void test_createResourceA_Success() {
      final ResourceADto resourceADto = DtoHelper.createResourceADto();
      when(this.resourceHandler.hasResource(resourceADto.getIdA())).thenReturn(false);
      
      final Response response = this.resourceA.createResourceA(resourceADto);
      
      verify(this.resourceHandler).hasResource(resourceADto.getIdA());
      verify(this.resourceHandler).addResource(resourceADto.getIdA(), resourceADto);
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.CREATED);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(ResourceADto.class).isEqualTo(resourceADto);
   }
   
   @Test
   void test_getResourceA_NotFound() {
      final long id = RandomUtils.nextLong();
      when(this.resourceHandler.hasResource(id)).thenReturn(false);
      
      final Response response = this.resourceA.getResourceA(id);
      
      verify(this.resourceHandler).hasResource(id);
      verify(this.resourceHandler, never()).getResource(id);
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.NOT_FOUND);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("id", id));
   }
   
   @Test
   void test_getResourceA_Success() {
      final ResourceADto resourceADto = DtoHelper.createResourceADto();
      when(this.resourceHandler.hasResource(resourceADto.getIdA())).thenReturn(true);
      when(this.resourceHandler.getResource(resourceADto.getIdA()))
            .thenReturn(DtoHelper.cloneResourceADto(resourceADto));
      
      final Response response = this.resourceA.getResourceA(resourceADto.getIdA());
      
      verify(this.resourceHandler).hasResource(resourceADto.getIdA());
      verify(this.resourceHandler).getResource(resourceADto.getIdA());
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.OK);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(ResourceADto.class).isEqualTo(resourceADto);
   }
   
   @Test
   void test_deleteResourceA_NotFound() {
      final long id = RandomUtils.nextLong();
      when(this.resourceHandler.hasResource(id)).thenReturn(false);
      
      final Response response = this.resourceA.deleteResourceA(id);
      
      verify(this.resourceHandler).hasResource(id);
      verify(this.resourceHandler, never()).removeResource(id);
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.NOT_FOUND);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("id", id));
   }
   
   @Test
   void test_deleteResourceA_Success() {
      final long id = RandomUtils.nextLong();
      when(this.resourceHandler.hasResource(id)).thenReturn(true);
      
      final Response response = this.resourceA.deleteResourceA(id);
      
      verify(this.resourceHandler).hasResource(id);
      verify(this.resourceHandler).removeResource(id);
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.OK);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("deleted", id));
   }
   
   @Test
   void test_updateResourceA_NotFound() {
      final long id = RandomUtils.nextLong();
      when(this.resourceHandler.hasResource(id)).thenReturn(false);
      
      final Response response = this.resourceA.updateResourceA(id, ResourceADto.builder().build());
      
      verify(this.resourceHandler).hasResource(id);
      verify(this.resourceHandler, never()).getResource(id);
      verify(this.resourceHandler, never()).replaceResource(eq(id), any(ResourceADto.class));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.NOT_FOUND);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("id", id));
   }
   
   @Test
   void test_updateResourceA_BadRequest() {
      final long id = RandomUtils.nextLong();
      
      final Response response = this.resourceA.updateResourceA(id, null);
      
      verify(this.resourceHandler, never()).hasResource(id);
      verify(this.resourceHandler, never()).getResource(id);
      verify(this.resourceHandler, never()).replaceResource(eq(id), any(ResourceADto.class));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.BAD_REQUEST);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("id", id));
   }
   
   @Test
   void test_updateResourceA_Success() {
      final ResourceADto resourceADto = DtoHelper.createResourceADto();
      when(this.resourceHandler.hasResource(resourceADto.getIdA())).thenReturn(true);
      when(this.resourceHandler.getResource(resourceADto.getIdA()))
            .thenReturn(DtoHelper.cloneResourceADto(resourceADto));
      
      final ResourceADto resourceADtoToUpdate = DtoHelper.createResourceADto();
      final Response response = this.resourceA.updateResourceA(resourceADto.getIdA(), resourceADtoToUpdate);
      
      verify(this.resourceHandler).hasResource(resourceADto.getIdA());
      verify(this.resourceHandler).getResource(resourceADto.getIdA());
      verify(this.resourceHandler).replaceResource(eq(resourceADto.getIdA()), any(ResourceADto.class));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.ACCEPTED);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(ResourceADto.class)
            .hasFieldOrPropertyWithValue("idA", resourceADto.getIdA())
            .hasFieldOrPropertyWithValue("paramA1", resourceADtoToUpdate.getParamA1())
            .hasFieldOrPropertyWithValue("paramA2", resourceADtoToUpdate.getParamA2())
            .hasFieldOrPropertyWithValue("paramA3", resourceADtoToUpdate.getParamA3());
   }
   
   @Test
   void test_updateResourceA_OnlyParam1_Success() {
      final ResourceADto resourceADto = DtoHelper.createResourceADto();
      when(this.resourceHandler.hasResource(resourceADto.getIdA())).thenReturn(true);
      when(this.resourceHandler.getResource(resourceADto.getIdA()))
            .thenReturn(DtoHelper.cloneResourceADto(resourceADto));
      
      final ResourceADto resourceADtoToUpdate = ResourceADto.builder()
            .idA(resourceADto.getIdA())
            .paramA1(RandomStringUtils.randomAlphanumeric(12))
            .build();
      final Response response = this.resourceA.updateResourceA(resourceADto.getIdA(), resourceADtoToUpdate);
      
      verify(this.resourceHandler).hasResource(resourceADto.getIdA());
      verify(this.resourceHandler).getResource(resourceADto.getIdA());
      verify(this.resourceHandler).replaceResource(eq(resourceADto.getIdA()), any(ResourceADto.class));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.ACCEPTED);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(ResourceADto.class)
            .hasFieldOrPropertyWithValue("idA", resourceADto.getIdA())
            .hasFieldOrPropertyWithValue("paramA1", resourceADtoToUpdate.getParamA1())
            .hasFieldOrPropertyWithValue("paramA2", resourceADto.getParamA2())
            .hasFieldOrPropertyWithValue("paramA3", resourceADto.getParamA3());
   }
   
   @Test
   void test_updateResourceA_OnlyParam2_Success() {
      final ResourceADto resourceADto = DtoHelper.createResourceADto();
      when(this.resourceHandler.hasResource(resourceADto.getIdA())).thenReturn(true);
      when(this.resourceHandler.getResource(resourceADto.getIdA()))
            .thenReturn(DtoHelper.cloneResourceADto(resourceADto));
      
      final ResourceADto resourceADtoToUpdate = ResourceADto.builder()
            .idA(resourceADto.getIdA())
            .paramA2(!resourceADto.getParamA2())
            .build();
      final Response response = this.resourceA.updateResourceA(resourceADto.getIdA(), resourceADtoToUpdate);
      
      verify(this.resourceHandler).hasResource(resourceADto.getIdA());
      verify(this.resourceHandler).getResource(resourceADto.getIdA());
      verify(this.resourceHandler).replaceResource(eq(resourceADto.getIdA()), any(ResourceADto.class));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.ACCEPTED);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(ResourceADto.class)
            .hasFieldOrPropertyWithValue("idA", resourceADto.getIdA())
            .hasFieldOrPropertyWithValue("paramA1", resourceADto.getParamA1())
            .hasFieldOrPropertyWithValue("paramA2", resourceADtoToUpdate.getParamA2())
            .hasFieldOrPropertyWithValue("paramA3", resourceADto.getParamA3());
   }
   
   @Test
   void test_updateResourceA_OnlyParam3_Success() {
      final ResourceADto resourceADto = DtoHelper.createResourceADto();
      when(this.resourceHandler.hasResource(resourceADto.getIdA())).thenReturn(true);
      when(this.resourceHandler.getResource(resourceADto.getIdA()))
            .thenReturn(DtoHelper.cloneResourceADto(resourceADto));
      
      final ResourceADto resourceADtoToUpdate = ResourceADto.builder()
            .idA(resourceADto.getIdA())
            .paramA3(DtoHelper.createResourceADtoParam3List())
            .build();
      final Response response = this.resourceA.updateResourceA(resourceADto.getIdA(), resourceADtoToUpdate);
      
      verify(this.resourceHandler).hasResource(resourceADto.getIdA());
      verify(this.resourceHandler).getResource(resourceADto.getIdA());
      verify(this.resourceHandler).replaceResource(eq(resourceADto.getIdA()), any(ResourceADto.class));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.ACCEPTED);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(ResourceADto.class)
            .hasFieldOrPropertyWithValue("idA", resourceADto.getIdA())
            .hasFieldOrPropertyWithValue("paramA1", resourceADto.getParamA1())
            .hasFieldOrPropertyWithValue("paramA2", resourceADto.getParamA2())
            .hasFieldOrPropertyWithValue("paramA3", resourceADtoToUpdate.getParamA3());
   }
}
