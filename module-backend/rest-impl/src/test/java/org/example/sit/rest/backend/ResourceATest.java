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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Tests for {@code ResourceA}.
 */
@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings({"javadoc", "boxing"})
public class ResourceATest {
   @Mock
   private ResourceHandler<ResourceADto> resources;
   
   @InjectMocks
   private ResourceA resourceA;
   
   @Test
   @SuppressWarnings("unchecked")
   public void test_getAvailability_Success() {
      final Response response = this.resourceA.getAvailability();
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.OK);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(Map.class);
      final Map<Object, Object> entity = (Map<Object, Object>) response.getEntity();
      assertThat(entity).hasSize(2).containsOnlyKeys("available", "timestamp")
            .containsEntry("available", Boolean.TRUE);
   }
   
   @Test
   public void test_getAllResourceA_NotFound() {
      when(this.resources.hasResources()).thenReturn(false);
      
      final Response response = this.resourceA.getAllResourceA();
      
      verify(this.resources).hasResources();
      verify(this.resources, never()).getAllResources();
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.NOT_FOUND);
      assertThat(response.hasEntity()).isFalse();
      assertThat(response.getEntity()).isNull();
   }
   
   @Test
   public void test_getAllResourceA_Success() {
      when(this.resources.hasResources()).thenReturn(true);
      final ResourceADto resourceADto1 = DtoHelper.createResourceADto();
      final ResourceADto resourceADto2 = DtoHelper.createResourceADto();
      final ResourceADto resourceADto3 = DtoHelper.createResourceADto();
      when(this.resources.getAllResources()).thenReturn(Arrays.asList(resourceADto1, resourceADto2,
            resourceADto3));
      
      final Response response = this.resourceA.getAllResourceA();
   
      verify(this.resources).hasResources();
      verify(this.resources).getAllResources();
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.OK);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(List.class).asList()
            .containsExactly(resourceADto1, resourceADto2, resourceADto3);
   }
   
   @Test
   public void test_createResourceA_BadRequest() {
      final Response response = this.resourceA.createResourceA(null);
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.BAD_REQUEST);
      assertThat(response.hasEntity()).isFalse();
      assertThat(response.getEntity()).isNull();
   }
   
   @Test
   public void test_createResourceA_Conflict() {
      final ResourceADto resourceADto = DtoHelper.createResourceADto();
      when(this.resources.hasResource(resourceADto.getIdA())).thenReturn(true);
   
      final Response response = this.resourceA.createResourceA(resourceADto);
   
      verify(this.resources).hasResource(eq(resourceADto.getIdA()));
      verify(this.resources, never()).addResource(anyLong(), any(ResourceADto.class));
   
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.CONFLICT);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("id", resourceADto.getIdA()));
   }
   
   @Test
   public void test_createResourceA_Success() {
      final ResourceADto resourceADto = DtoHelper.createResourceADto();
      when(this.resources.hasResource(resourceADto.getIdA())).thenReturn(false);
      
      final Response response = this.resourceA.createResourceA(resourceADto);
      
      verify(this.resources).hasResource(eq(resourceADto.getIdA()));
      verify(this.resources).addResource(eq(resourceADto.getIdA()), eq(resourceADto));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.CREATED);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(ResourceADto.class)
            .isEqualTo(resourceADto);
   }
   
   @Test
   public void test_getResourceA_NotFound() {
      final long id = RandomUtils.nextLong();
      when(this.resources.hasResource(id)).thenReturn(false);
   
      final Response response = this.resourceA.getResourceA(id);
   
      verify(this.resources).hasResource(eq(id));
      verify(this.resources, never()).getResource(id);
   
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.NOT_FOUND);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("id", id));
   }
   
   @Test
   public void test_getResourceA_Success() {
      final ResourceADto resourceADto = DtoHelper.createResourceADto();
      when(this.resources.hasResource(resourceADto.getIdA())).thenReturn(true);
      when(this.resources.getResource(resourceADto.getIdA()))
            .thenReturn(DtoHelper.cloneResourceADto(resourceADto));
      
      final Response response = this.resourceA.getResourceA(resourceADto.getIdA());
      
      verify(this.resources).hasResource(eq(resourceADto.getIdA()));
      verify(this.resources).getResource(eq(resourceADto.getIdA()));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.OK);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(ResourceADto.class)
            .isEqualTo(resourceADto);
   }
   
   @Test
   public void test_deleteResourceA_NotFound() {
      final long id = RandomUtils.nextLong();
      when(this.resources.hasResource(id)).thenReturn(false);
      
      final Response response = this.resourceA.deleteResourceA(id);
      
      verify(this.resources).hasResource(eq(id));
      verify(this.resources, never()).removeResource(eq(id));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.NOT_FOUND);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("id", id));
   }
   
   @Test
   public void test_deleteResourceA_Success() {
      final long id = RandomUtils.nextLong();
      when(this.resources.hasResource(id)).thenReturn(true);
      
      final Response response = this.resourceA.deleteResourceA(id);
      
      verify(this.resources).hasResource(eq(id));
      verify(this.resources).removeResource(eq(id));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.OK);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("deleted", id));
   }
   
   @Test
   public void test_updateResourceA_NotFound() {
      final long id = RandomUtils.nextLong();
      when(this.resources.hasResource(id)).thenReturn(false);
      
      final Response response = this.resourceA.updateResourceA(id, null);
      
      verify(this.resources).hasResource(eq(id));
      verify(this.resources, never()).getResource(eq(id));
      verify(this.resources, never()).replaceResource(eq(id), any(ResourceADto.class));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.NOT_FOUND);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("id", id));
   }
   
   @Test
   public void test_updateResourceA_BadRequest() {
      final long id = RandomUtils.nextLong();
      when(this.resources.hasResource(id)).thenReturn(true);
      
      final Response response = this.resourceA.updateResourceA(id, null);
      
      verify(this.resources).hasResource(eq(id));
      verify(this.resources, never()).getResource(eq(id));
      verify(this.resources, never()).replaceResource(eq(id), any(ResourceADto.class));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.BAD_REQUEST);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("id", id));
   }
   
   @Test
   public void test_updateResourceA_Success() {
      final ResourceADto resourceADto = DtoHelper.createResourceADto();
      when(this.resources.hasResource(resourceADto.getIdA())).thenReturn(true);
      when(this.resources.getResource(resourceADto.getIdA()))
            .thenReturn(DtoHelper.cloneResourceADto(resourceADto));
      
      final ResourceADto resourceADtoToUpdate = DtoHelper.createResourceADto();
      final Response response = this.resourceA.updateResourceA(resourceADto.getIdA(),
            resourceADtoToUpdate);
      
      verify(this.resources).hasResource(eq(resourceADto.getIdA()));
      verify(this.resources).getResource(eq(resourceADto.getIdA()));
      verify(this.resources).replaceResource(eq(resourceADto.getIdA()), any(ResourceADto.class));
      
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
   public void test_updateResourceA_OnlyParam1_Success() {
      final ResourceADto resourceADto = DtoHelper.createResourceADto();
      when(this.resources.hasResource(resourceADto.getIdA())).thenReturn(true);
      when(this.resources.getResource(resourceADto.getIdA()))
            .thenReturn(DtoHelper.cloneResourceADto(resourceADto));
      
      final ResourceADto resourceADtoToUpdate = new ResourceADto(resourceADto.getIdA())
            .setParamA1(RandomStringUtils.randomAlphanumeric(12));
      final Response response = this.resourceA.updateResourceA(resourceADto.getIdA(),
            resourceADtoToUpdate);
      
      verify(this.resources).hasResource(eq(resourceADto.getIdA()));
      verify(this.resources).getResource(eq(resourceADto.getIdA()));
      verify(this.resources).replaceResource(eq(resourceADto.getIdA()), any(ResourceADto.class));
      
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
   public void test_updateResourceA_OnlyParam2_Success() {
      final ResourceADto resourceADto = DtoHelper.createResourceADto();
      when(this.resources.hasResource(resourceADto.getIdA())).thenReturn(true);
      when(this.resources.getResource(resourceADto.getIdA()))
            .thenReturn(DtoHelper.cloneResourceADto(resourceADto));
      
      final ResourceADto resourceADtoToUpdate = new ResourceADto(resourceADto.getIdA())
            .setParamA2(!resourceADto.getParamA2());
      final Response response = this.resourceA.updateResourceA(resourceADto.getIdA(),
            resourceADtoToUpdate);
      
      verify(this.resources).hasResource(eq(resourceADto.getIdA()));
      verify(this.resources).getResource(eq(resourceADto.getIdA()));
      verify(this.resources).replaceResource(eq(resourceADto.getIdA()), any(ResourceADto.class));
      
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
   public void test_updateResourceA_OnlyParam3_Success() {
      final ResourceADto resourceADto = DtoHelper.createResourceADto();
      when(this.resources.hasResource(resourceADto.getIdA())).thenReturn(true);
      when(this.resources.getResource(resourceADto.getIdA()))
            .thenReturn(DtoHelper.cloneResourceADto(resourceADto));
      
      final ResourceADto resourceADtoToUpdate = new ResourceADto(resourceADto.getIdA())
            .setParamA3(DtoHelper.createResourceADtoParam3List());
      final Response response = this.resourceA.updateResourceA(resourceADto.getIdA(),
            resourceADtoToUpdate);
      
      verify(this.resources).hasResource(eq(resourceADto.getIdA()));
      verify(this.resources).getResource(eq(resourceADto.getIdA()));
      verify(this.resources).replaceResource(eq(resourceADto.getIdA()), any(ResourceADto.class));
      
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
