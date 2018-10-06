package org.example.sit.rest.backend;

import static org.assertj.core.api.Assertions.assertThat;
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

import org.apache.commons.lang3.RandomUtils;
import org.example.sit.common.resources.ResourceHandler;
import org.example.sit.rest.backend.dto.DtoHelper;
import org.example.sit.rest.backend.dto.ResourceCDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Tests for {@code ResourceC}.
 */
@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings({"javadoc", "boxing"})
public class ResourceCTest {
   @Mock
   private ResourceHandler<ResourceCDto> resources;
   
   @InjectMocks
   private ResourceC resourceC;
   
   @Test
   @SuppressWarnings("unchecked")
   public void test_getAvailability_Success() {
      final Response response = this.resourceC.getAvailability();
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.OK);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(Map.class);
      final Map<Object, Object> entity = (Map<Object, Object>) response.getEntity();
      assertThat(entity).hasSize(2).containsOnlyKeys("available", "timestamp")
            .containsEntry("available", Boolean.TRUE);
   }
   
   @Test
   public void test_getAllResourceC_NotFound() {
      when(this.resources.hasResources()).thenReturn(false);
      
      final Response response = this.resourceC.getAllResourceC();
      
      verify(this.resources).hasResources();
      verify(this.resources, never()).getAllResources();
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.NOT_FOUND);
      assertThat(response.hasEntity()).isFalse();
      assertThat(response.getEntity()).isNull();
   }
   
   @Test
   public void test_getAllResourceC_Success() {
      when(this.resources.hasResources()).thenReturn(true);
      final ResourceCDto resourceCDto1 = DtoHelper.createResourceCDto();
      final ResourceCDto resourceCDto2 = DtoHelper.createResourceCDto();
      final ResourceCDto resourceCDto3 = DtoHelper.createResourceCDto();
      when(this.resources.getAllResources()).thenReturn(Arrays.asList(resourceCDto1, resourceCDto2,
            resourceCDto3));
      
      final Response response = this.resourceC.getAllResourceC();
      
      verify(this.resources).hasResources();
      verify(this.resources).getAllResources();
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.OK);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(List.class).asList()
            .containsExactly(resourceCDto1, resourceCDto2, resourceCDto3);
   }
   
   @Test
   public void test_getResourceC_NotFound() {
      final long id = RandomUtils.nextLong();
      when(this.resources.hasResource(id)).thenReturn(false);
      
      final Response response = this.resourceC.getResourceC(id);
      
      verify(this.resources).hasResource(eq(id));
      verify(this.resources, never()).getResource(anyLong());
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.NOT_FOUND);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("id", id));
   }
   
   @Test
   public void test_getResourceC_Success() {
      final ResourceCDto resourceCDto = DtoHelper.createResourceCDto();
      when(this.resources.hasResource(resourceCDto.getIdC())).thenReturn(true);
      when(this.resources.getResource(resourceCDto.getIdC()))
            .thenReturn(DtoHelper.cloneResourceCDto(resourceCDto));
      
      final Response response = this.resourceC.getResourceC(resourceCDto.getIdC());
      
      verify(this.resources).hasResource(eq(resourceCDto.getIdC()));
      verify(this.resources).getResource(eq(resourceCDto.getIdC()));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.OK);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(ResourceCDto.class)
            .isEqualTo(resourceCDto);
   }
   
   @Test
   public void test_deleteResourceC_NotFound() {
      final long id = RandomUtils.nextLong();
      when(this.resources.hasResource(id)).thenReturn(false);
      
      final Response response = this.resourceC.deleteResourceC(id);
      
      verify(this.resources).hasResource(eq(id));
      verify(this.resources, never()).removeResource(eq(id));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.NOT_FOUND);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("id", id));
   }
   
   @Test
   public void test_deleteResourceC_Success() {
      final long id = RandomUtils.nextLong();
      when(this.resources.hasResource(id)).thenReturn(true);
      
      final Response response = this.resourceC.deleteResourceC(id);
      
      verify(this.resources).hasResource(eq(id));
      verify(this.resources).removeResource(eq(id));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.OK);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("deleted", id));
   }
}
