package org.example.sit.rest.frontend;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.AbstractMap;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.example.sit.common.resources.ResourceHandler;
import org.example.sit.rest.frontend.dto.DtoHelper;
import org.example.sit.rest.frontend.dto.ResourceFDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Tests for {@code ResourceF}.
 */
@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings({"javadoc", "boxing"})
public class ResourceFTest {
   @Mock
   private ResourceHandler<ResourceFDto> resources;
   
   @InjectMocks
   private ResourceF resourceF;
   
   @Test
   public void test_createResourceF_BadRequest() {
      final Response response = this.resourceF.createResourceF(null);
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.BAD_REQUEST);
      assertThat(response.hasEntity()).isFalse();
      assertThat(response.getEntity()).isNull();
   }
   
   @Test
   public void test_createResourceF_Conflict() {
      final ResourceFDto resourceFDto = DtoHelper.createResourceFDto();
      when(this.resources.hasResource(resourceFDto.getIdF())).thenReturn(true);
      
      final Response response = this.resourceF.createResourceF(resourceFDto);
      
      verify(this.resources).hasResource(eq(resourceFDto.getIdF()));
      verify(this.resources, never()).addResource(anyLong(), any(ResourceFDto.class));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.CONFLICT);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("id", resourceFDto.getIdF()));
   }
   
   @Test
   public void test_createResourceF_Success() {
      final ResourceFDto resourceFDto = DtoHelper.createResourceFDto();
      when(this.resources.hasResource(resourceFDto.getIdF())).thenReturn(false);
      
      final Response response = this.resourceF.createResourceF(resourceFDto);
      
      verify(this.resources).hasResource(eq(resourceFDto.getIdF()));
      verify(this.resources).addResource(eq(resourceFDto.getIdF()), eq(resourceFDto));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.CREATED);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(ResourceFDto.class)
            .isEqualTo(resourceFDto);
   }
   
   @Test
   public void test_deleteResourceF_NotFound() {
      final long id = RandomUtils.nextLong();
      when(this.resources.hasResource(id)).thenReturn(false);
      
      final Response response = this.resourceF.deleteResourceF(id);
      
      verify(this.resources).hasResource(eq(id));
      verify(this.resources, never()).removeResource(eq(id));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.NOT_FOUND);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("id", id));
   }
   
   @Test
   public void test_deleteResourceF_Success() {
      final long id = RandomUtils.nextLong();
      when(this.resources.hasResource(id)).thenReturn(true);
      
      final Response response = this.resourceF.deleteResourceF(id);
      
      verify(this.resources).hasResource(eq(id));
      verify(this.resources).removeResource(eq(id));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.OK);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("deleted", id));
   }
   
   @Test
   public void test_updateResourceF_NotFound() {
      final long id = RandomUtils.nextLong();
      when(this.resources.hasResource(id)).thenReturn(false);
      
      final Response response = this.resourceF.updateResourceF(id, null);
      
      verify(this.resources).hasResource(eq(id));
      verify(this.resources, never()).getResource(eq(id));
      verify(this.resources, never()).replaceResource(eq(id), any(ResourceFDto.class));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.NOT_FOUND);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("id", id));
   }
   
   @Test
   public void test_updateResourceF_BadRequest() {
      final long id = RandomUtils.nextLong();
      when(this.resources.hasResource(id)).thenReturn(true);
      
      final Response response = this.resourceF.updateResourceF(id, null);
      
      verify(this.resources).hasResource(eq(id));
      verify(this.resources, never()).getResource(eq(id));
      verify(this.resources, never()).replaceResource(eq(id), any(ResourceFDto.class));
      
      assertThat(response).isNotNull();
      assertThat(response.getStatusInfo()).isNotNull().isEqualTo(Response.Status.BAD_REQUEST);
      assertThat(response.hasEntity()).isTrue();
      assertThat(response.getEntity()).isNotNull().isInstanceOf(AbstractMap.SimpleEntry.class)
            .isEqualTo(new AbstractMap.SimpleEntry<>("id", id));
   }
   
   @Test
   public void test_updateResourceF_Success() {
      final ResourceFDto resourceFDto = DtoHelper.createResourceFDto();
      when(this.resources.hasResource(resourceFDto.getIdF())).thenReturn(true);
      when(this.resources.getResource(resourceFDto.getIdF()))
            .thenReturn(DtoHelper.cloneResourceFDto(resourceFDto));
      
      final ResourceFDto resourceFDtoToUpdate = DtoHelper.createResourceFDto();
      final Response response = this.resourceF.updateResourceF(resourceFDto.getIdF(),
            resourceFDtoToUpdate);
      
      verify(this.resources).hasResource(eq(resourceFDto.getIdF()));
      verify(this.resources).getResource(eq(resourceFDto.getIdF()));
      verify(this.resources).replaceResource(eq(resourceFDto.getIdF()), any(ResourceFDto.class));
      
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
   public void test_updateResourceF_OnlyParam1_Success() {
      final ResourceFDto resourceFDto = DtoHelper.createResourceFDto();
      when(this.resources.hasResource(resourceFDto.getIdF())).thenReturn(true);
      when(this.resources.getResource(resourceFDto.getIdF()))
            .thenReturn(DtoHelper.cloneResourceFDto(resourceFDto));
      
      final ResourceFDto resourceFDtoToUpdate = new ResourceFDto(resourceFDto.getIdF())
            .setParamF1(RandomStringUtils.randomAlphanumeric(20));
      final Response response =
            this.resourceF.updateResourceF(resourceFDto.getIdF(), resourceFDtoToUpdate);
      
      verify(this.resources).hasResource(eq(resourceFDto.getIdF()));
      verify(this.resources).getResource(eq(resourceFDto.getIdF()));
      verify(this.resources).replaceResource(eq(resourceFDto.getIdF()), any(ResourceFDto.class));
      
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
   public void test_updateResourceF_OnlyParam2_Success() {
      final ResourceFDto resourceFDto = DtoHelper.createResourceFDto();
      when(this.resources.hasResource(resourceFDto.getIdF())).thenReturn(true);
      when(this.resources.getResource(resourceFDto.getIdF()))
            .thenReturn(DtoHelper.cloneResourceFDto(resourceFDto));
      
      final ResourceFDto resourceFDtoToUpdate = new ResourceFDto(resourceFDto.getIdF())
            .setParamF2(RandomUtils.nextInt());
      final Response response = this.resourceF.updateResourceF(resourceFDto.getIdF(),
            resourceFDtoToUpdate);
      
      verify(this.resources).hasResource(eq(resourceFDto.getIdF()));
      verify(this.resources).getResource(eq(resourceFDto.getIdF()));
      verify(this.resources).replaceResource(eq(resourceFDto.getIdF()), any(ResourceFDto.class));
      
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
   public void test_updateResourceF_OnlyParam3_Success() {
      final ResourceFDto resourceFDto = DtoHelper.createResourceFDto();
      when(this.resources.hasResource(resourceFDto.getIdF())).thenReturn(true);
      when(this.resources.getResource(resourceFDto.getIdF()))
            .thenReturn(DtoHelper.cloneResourceFDto(resourceFDto));
      
      final ResourceFDto resourceFDtoToUpdate = new ResourceFDto(resourceFDto.getIdF())
            .setParamF3(RandomStringUtils.randomAlphanumeric(21));
      final Response response = this.resourceF.updateResourceF(resourceFDto.getIdF(),
            resourceFDtoToUpdate);
      
      verify(this.resources).hasResource(eq(resourceFDto.getIdF()));
      verify(this.resources).getResource(eq(resourceFDto.getIdF()));
      verify(this.resources).replaceResource(eq(resourceFDto.getIdF()), any(ResourceFDto.class));
      
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
   public void test_updateResourceF_OnlyParam4_Success() {
      final ResourceFDto resourceFDto = DtoHelper.createResourceFDto();
      when(this.resources.hasResource(resourceFDto.getIdF())).thenReturn(true);
      when(this.resources.getResource(resourceFDto.getIdF()))
            .thenReturn(DtoHelper.cloneResourceFDto(resourceFDto));
      
      final ResourceFDto resourceFDtoToUpdate = new ResourceFDto(resourceFDto.getIdF())
            .setParamF4(RandomStringUtils.randomAlphanumeric(22));
      final Response response = this.resourceF.updateResourceF(resourceFDto.getIdF(),
            resourceFDtoToUpdate);
      
      verify(this.resources).hasResource(eq(resourceFDto.getIdF()));
      verify(this.resources).getResource(eq(resourceFDto.getIdF()));
      verify(this.resources).replaceResource(eq(resourceFDto.getIdF()), any(ResourceFDto.class));
      
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
   public void test_updateResourceF_OnlyParam5_Success() {
      final ResourceFDto resourceFDto = DtoHelper.createResourceFDto();
      when(this.resources.hasResource(resourceFDto.getIdF())).thenReturn(true);
      when(this.resources.getResource(resourceFDto.getIdF()))
            .thenReturn(DtoHelper.cloneResourceFDto(resourceFDto));
      
      final ResourceFDto resourceFDtoToUpdate = new ResourceFDto(resourceFDto.getIdF())
            .setParamF5(!resourceFDto.getParamF5());
      final Response response = this.resourceF.updateResourceF(resourceFDto.getIdF(),
            resourceFDtoToUpdate);
      
      verify(this.resources).hasResource(eq(resourceFDto.getIdF()));
      verify(this.resources).getResource(eq(resourceFDto.getIdF()));
      verify(this.resources).replaceResource(eq(resourceFDto.getIdF()), any(ResourceFDto.class));
      
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
   public void test_updateResourceF_OnlyParam6_Success() {
      final ResourceFDto resourceFDto = DtoHelper.createResourceFDto();
      when(this.resources.hasResource(resourceFDto.getIdF())).thenReturn(true);
      when(this.resources.getResource(resourceFDto.getIdF()))
            .thenReturn(DtoHelper.cloneResourceFDto(resourceFDto));
      
      final ResourceFDto resourceFDtoToUpdate = new ResourceFDto(resourceFDto.getIdF())
            .setParamF6(RandomUtils.nextLong());
      final Response response = this.resourceF.updateResourceF(resourceFDto.getIdF(),
            resourceFDtoToUpdate);
      
      verify(this.resources).hasResource(eq(resourceFDto.getIdF()));
      verify(this.resources).getResource(eq(resourceFDto.getIdF()));
      verify(this.resources).replaceResource(eq(resourceFDto.getIdF()), any(ResourceFDto.class));
      
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
   public void test_updateResourceF_OnlyParam7_Success() {
      final ResourceFDto resourceFDto = DtoHelper.createResourceFDto();
      when(this.resources.hasResource(resourceFDto.getIdF())).thenReturn(true);
      when(this.resources.getResource(resourceFDto.getIdF()))
            .thenReturn(DtoHelper.cloneResourceFDto(resourceFDto));
      
      final ResourceFDto resourceFDtoToUpdate = new ResourceFDto(resourceFDto.getIdF())
            .setParamF7(RandomStringUtils.randomAlphanumeric(23));
      final Response response = this.resourceF.updateResourceF(resourceFDto.getIdF(),
            resourceFDtoToUpdate);
      
      verify(this.resources).hasResource(eq(resourceFDto.getIdF()));
      verify(this.resources).getResource(eq(resourceFDto.getIdF()));
      verify(this.resources).replaceResource(eq(resourceFDto.getIdF()), any(ResourceFDto.class));
      
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
   public void test_updateResourceF_OnlyParam8_Success() {
      final ResourceFDto resourceFDto = DtoHelper.createResourceFDto();
      when(this.resources.hasResource(resourceFDto.getIdF())).thenReturn(true);
      when(this.resources.getResource(resourceFDto.getIdF()))
            .thenReturn(DtoHelper.cloneResourceFDto(resourceFDto));
      
      final ResourceFDto resourceFDtoToUpdate = new ResourceFDto(resourceFDto.getIdF())
            .setParamF8(DtoHelper.createResourceFDtoParam8List());
      final Response response = this.resourceF.updateResourceF(resourceFDtoToUpdate.getIdF(),
            resourceFDtoToUpdate);
      
      verify(this.resources).hasResource(eq(resourceFDto.getIdF()));
      verify(this.resources).getResource(eq(resourceFDto.getIdF()));
      verify(this.resources).replaceResource(eq(resourceFDto.getIdF()), any(ResourceFDto.class));
      
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
