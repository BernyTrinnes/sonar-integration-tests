package org.example.sit.rest.backend;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.RandomUtils;
import org.example.sit.common.resources.ResourceHandler;
import org.example.sit.rest.backend.dto.DtoHelper;
import org.example.sit.rest.backend.dto.ResourceCDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests for {@code ResourceC}.
 */
@ExtendWith(MockitoExtension.class)
class ResourceCTest {
   @Mock
   private ResourceHandler<ResourceCDto> resourceHandler;
   
   @InjectMocks
   private ResourceC resourceC;
   
   @Test
   @SuppressWarnings("unchecked")
   void test_getAvailability_Success() {
      final Response response = this.resourceC.getAvailability();
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.OK);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(Map.class);
      final Map<Object, Object> entity = (Map<Object, Object>) response.getEntity();
      assertThat(entity).hasSize(2).containsOnlyKeys("available", "timestamp").containsEntry("available", Boolean.TRUE);
   }
   
   @Test
   void test_getAllResourceC_NotFound() {
      when(this.resourceHandler.hasResources()).thenReturn(false);
      
      final Response response = this.resourceC.getAllResourceC();
      
      verify(this.resourceHandler).hasResources();
      verify(this.resourceHandler, never()).getAllResources();
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.NOT_FOUND);
      assertThat(response.hasEntity()).isFalse();
      assertThat(response.getEntity()).isNull();
   }
   
   @Test
   void test_getAllResourceC_Success() {
      when(this.resourceHandler.hasResources()).thenReturn(true);
      final ResourceCDto resourceCDto1 = DtoHelper.createResourceCDto();
      final ResourceCDto resourceCDto2 = DtoHelper.createResourceCDto();
      final ResourceCDto resourceCDto3 = DtoHelper.createResourceCDto();
      when(this.resourceHandler.getAllResources()).thenReturn(Arrays.asList(resourceCDto1, resourceCDto2,
            resourceCDto3));
      
      final Response response = this.resourceC.getAllResourceC();
      
      verify(this.resourceHandler).hasResources();
      verify(this.resourceHandler).getAllResources();
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.OK);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(List.class).asList()
            .containsExactly(resourceCDto1, resourceCDto2, resourceCDto3);
   }
   
   @Test
   void test_getResourceC_NotFound() {
      final long id = RandomUtils.nextLong();
      when(this.resourceHandler.hasResource(id)).thenReturn(false);
      
      final Response response = this.resourceC.getResourceC(id);
      
      verify(this.resourceHandler).hasResource(id);
      verify(this.resourceHandler, never()).getResource(anyLong());
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.NOT_FOUND);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("id", id));
   }
   
   @Test
   void test_getResourceC_Success() {
      final ResourceCDto resourceCDto = DtoHelper.createResourceCDto();
      when(this.resourceHandler.hasResource(resourceCDto.getIdC())).thenReturn(true);
      when(this.resourceHandler.getResource(resourceCDto.getIdC()))
            .thenReturn(DtoHelper.cloneResourceCDto(resourceCDto));
      
      final Response response = this.resourceC.getResourceC(resourceCDto.getIdC());
      
      verify(this.resourceHandler).hasResource(resourceCDto.getIdC());
      verify(this.resourceHandler).getResource(resourceCDto.getIdC());
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.OK);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(ResourceCDto.class).isEqualTo(resourceCDto);
   }
   
   @Test
   void test_deleteResourceC_NotFound() {
      final long id = RandomUtils.nextLong();
      when(this.resourceHandler.hasResource(id)).thenReturn(false);
      
      final Response response = this.resourceC.deleteResourceC(id);
      
      verify(this.resourceHandler).hasResource(id);
      verify(this.resourceHandler, never()).removeResource(id);
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.NOT_FOUND);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("id", id));
   }
   
   @Test
   void test_deleteResourceC_Success() {
      final long id = RandomUtils.nextLong();
      when(this.resourceHandler.hasResource(id)).thenReturn(true);
      
      final Response response = this.resourceC.deleteResourceC(id);
      
      verify(this.resourceHandler).hasResource(id);
      verify(this.resourceHandler).removeResource(id);
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.OK);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("deleted", id));
   }
}
