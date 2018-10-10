package org.example.sit.rest.frontend.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * Tests for {@code ResourceDDto}.
 */
@SuppressWarnings({"javadoc", "boxing"})
public class ResourceDDtoTest {
   @Test
   public void test_Constructor_Success() {
      final long id = RandomUtils.nextLong();
      final ResourceDDto resourceDto = new ResourceDDto(id);
      
      assertResourceDDto(resourceDto, id, null, null, null, null);
   }
   
   private void assertResourceDDto(final ResourceDDto pResourceDto, final long pId,
         final String pParam1, final Integer pParam2, final Boolean pParam3,
         final List<Long> pParam4) {
      assertNotNull(pResourceDto);
      assertThat(pResourceDto.getIdD()).isEqualTo(pId);
      if (null == pParam1) {
         assertNull(pResourceDto.getParamD1());
      } else {
         assertThat(pResourceDto.getParamD1()).isNotNull().isEqualTo(pParam1);
      }
      if (null == pParam2) {
         assertNull(pResourceDto.getParamD2());
      } else {
         assertThat(pResourceDto.getParamD2()).isNotNull().isEqualTo(pParam2);
      }
      if (null == pParam3) {
         assertNull(pResourceDto.getParamD3());
      } else {
         assertThat(pResourceDto.getParamD3()).isNotNull().isEqualTo(pParam3);
      }
      if (null == pParam4) {
         assertNull(pResourceDto.getParamD4());
      } else {
         assertThat(pResourceDto.getParamD4()).isNotNull().hasSameSizeAs(pParam4)
               .containsExactlyElementsOf(pParam4);
      }
   }
   
   @Test
   public void test_paramD1() {
      final long id = RandomUtils.nextLong();
      final String param1 = RandomStringUtils.randomAlphanumeric(10);
      final ResourceDDto resourceDto = new ResourceDDto(id).setParamD1(param1);
      
      assertResourceDDto(resourceDto, id, param1, null, null, null);
   }
   
   @Test
   public void test_paramD2() {
      final long id = RandomUtils.nextLong();
      final Integer param2 = RandomUtils.nextInt();
      final ResourceDDto resourceDto = new ResourceDDto(id).setParamD2(param2);
      
      assertResourceDDto(resourceDto, id, null, param2, null, null);
   }
   
   @Test
   public void test_paramD3() {
      final long id = RandomUtils.nextLong();
      final boolean param3 = RandomUtils.nextBoolean();
      final ResourceDDto resourceDto = new ResourceDDto(id).setParamD3(param3);
      
      assertResourceDDto(resourceDto, id, null, null, param3, null);
   }
   
   @Test
   public void test_paramD4() {
      final long id = RandomUtils.nextLong();
      final List<Long> param4 = DtoHelper.createResourceDDtoParam4List();
      final ResourceDDto resourceDto = new ResourceDDto(id).setParamD4(param4);
      
      assertResourceDDto(resourceDto, id, null, null, null, param4);
   }
   
   @Test
   public void test_equals() {
      final ResourceDDto resourceDto = new ResourceDDto(RandomUtils.nextLong());
      assertEquals(resourceDto, resourceDto);
      assertNotEquals(resourceDto, null);
      assertNotEquals(resourceDto, new Object());
      
      final ResourceDDto resourceDtoOther = new ResourceDDto(resourceDto.getIdD());
      assertEquals(resourceDto, resourceDtoOther);
      resourceDtoOther.setParamD1(RandomStringUtils.randomAlphanumeric(10));
      assertNotEquals(resourceDto, resourceDtoOther);
   }
   
   @Test
   public void test_equals_allFields() {
      final ResourceDDto resourceDto = DtoHelper.createResourceDDto();
      final ResourceDDto resourceDtoOther = DtoHelper.cloneResourceDDto(resourceDto);
      assertEquals(resourceDto, resourceDtoOther);
   }
   
   @Test
   public void test_equals_FieldsDiffer() {
      final ResourceDDto resourceDto = DtoHelper.createResourceDDto();
      final ResourceDDto resourceDtoOther = DtoHelper.cloneResourceDDto(resourceDto);
      assertEquals(resourceDto, resourceDtoOther);
      
      resourceDtoOther.setParamD1(RandomStringUtils.randomAlphanumeric(11));
      assertNotEquals(resourceDto, resourceDtoOther);
      
      resourceDtoOther.setParamD1(resourceDto.getParamD1());
      resourceDtoOther.setParamD2(RandomUtils.nextInt());
      assertNotEquals(resourceDto, resourceDtoOther);
      
      resourceDtoOther.setParamD2(resourceDto.getParamD2());
      resourceDtoOther.setParamD3(!resourceDto.getParamD3());
      assertNotEquals(resourceDto, resourceDtoOther);
      
      resourceDtoOther.setParamD3(resourceDto.getParamD3());
      resourceDtoOther.setParamD4(DtoHelper.createResourceDDtoParam4List());
      assertNotEquals(resourceDto, resourceDtoOther);
      
      final ResourceDDto resourceDtoOtherId = new ResourceDDto(resourceDto.getIdD() - 1)
            .setParamD1(resourceDto.getParamD1())
            .setParamD2(resourceDto.getParamD2())
            .setParamD3(resourceDto.getParamD3())
            .setParamD4(resourceDto.getParamD4());
      assertNotEquals(resourceDto, resourceDtoOtherId);
   }
   
   @Test
   public void test_hashCode() {
      final ResourceDDto resourceDto = DtoHelper.createResourceDDto();
      assertEquals(resourceDto.hashCode(), Objects.hash(resourceDto.getIdD(),
            resourceDto.getParamD1(), resourceDto.getParamD2(), resourceDto.getParamD3(),
            resourceDto.getParamD4()));
   }
   
   @Test
   public void test_toString() {
      final ResourceDDto resourceDto = DtoHelper.createResourceDDto();
      assertThat(resourceDto).hasToString("ResourceDDto{" + "idD=" + resourceDto.getIdD() +
            ", paramD1='" + resourceDto.getParamD1() + '\'' + ", paramD2='" +
            resourceDto.getParamD2() + '\'' + ", paramD3=" + resourceDto.getParamD3() +
            ", paramD4=" + resourceDto.getParamD4() + '}');
   }
   
   @Test
   public void test_JSON_serialization() {
      final ResourceDDto resourceDto = DtoHelper.createResourceDDto();
      final String expectedJson = getJsonRepresentation(resourceDto);
      
      DtoHelper.assertDtoToJson(resourceDto, expectedJson);
   }
   
   private String getJsonRepresentation(final ResourceDDto pResourceDDto) {
      return String
            .format("{\"id\":%d,\"param1\":\"%s\",\"param2\":%d,\"param3\":%b,\"param4\":[%s]}",
                  pResourceDDto.getIdD(), pResourceDDto.getParamD1(), pResourceDDto.getParamD2(),
                  pResourceDDto.getParamD3(), StringUtils.join(pResourceDDto.getParamD4(), ","));
   }
   
   @Test
   public void test_JSON_deserialization() {
      final ResourceDDto resourceDtoExpected = DtoHelper.createResourceDDto();
      final String json = getJsonRepresentation(resourceDtoExpected);
      
      try {
         final ObjectMapper mapper = new ObjectMapper();
         final ResourceDDto resourceDto = mapper.readValue(json, ResourceDDto.class);
         assertEquals(resourceDtoExpected, resourceDto);
      } catch (final IOException ignored) {
         fail("Deserialization should not throw an exception.");
      }
   }
}
