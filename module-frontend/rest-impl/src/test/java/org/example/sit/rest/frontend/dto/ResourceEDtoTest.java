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
 * Tests for {@code ResourceEDto}.
 */
@SuppressWarnings({"javadoc", "boxing"})
public class ResourceEDtoTest {
   @Test
   public void test_Constructor_Success() {
      final long id = RandomUtils.nextLong();
      final ResourceEDto resourceDto = new ResourceEDto(id);
      
      assertResourceEDto(resourceDto, id, null, null, null, null, null, null);
   }
   
   private void assertResourceEDto(final ResourceEDto pResourceDto, final long pId,
         final Integer pParam1, final String pParam2, final String pParam3, final Long pParam4,
         final Boolean pParam5, final List<Integer> pParam6) {
      assertNotNull(pResourceDto);
      assertThat(pResourceDto.getIdE()).isEqualTo(pId);
      if (null == pParam1) {
         assertNull(pResourceDto.getParamE1());
      } else {
         assertThat(pResourceDto.getParamE1()).isNotNull().isEqualTo(pParam1);
      }
      if (null == pParam2) {
         assertNull(pResourceDto.getParamE2());
      } else {
         assertThat(pResourceDto.getParamE2()).isNotNull().isEqualTo(pParam2);
      }
      if (null == pParam3) {
         assertNull(pResourceDto.getParamE3());
      } else {
         assertThat(pResourceDto.getParamE3()).isNotNull().isEqualTo(pParam3);
      }
      if (null == pParam4) {
         assertNull(pResourceDto.getParamE4());
      } else {
         assertThat(pResourceDto.getParamE4()).isNotNull().isEqualTo(pParam4);
      }
      if (null == pParam5) {
         assertNull(pResourceDto.getParamE5());
      } else {
         assertThat(pResourceDto.getParamE5()).isNotNull().isEqualTo(pParam5);
      }
      if (null == pParam6) {
         assertNull(pResourceDto.getParamE6());
      } else {
         assertThat(pResourceDto.getParamE6()).isNotNull().hasSameSizeAs(pParam6)
               .containsExactlyElementsOf(pParam6);
      }
   }
   
   @Test
   public void test_paramE1() {
      final long id = RandomUtils.nextLong();
      final Integer param1 = RandomUtils.nextInt();
      final ResourceEDto resourceDto = new ResourceEDto(id).setParamE1(param1);
      
      assertResourceEDto(resourceDto, id, param1, null, null, null, null, null);
   }
   
   @Test
   public void test_paramE2() {
      final long id = RandomUtils.nextLong();
      final String param2 = RandomStringUtils.randomAlphanumeric(10);
      final ResourceEDto resourceDto = new ResourceEDto(id).setParamE2(param2);
      
      assertResourceEDto(resourceDto, id, null, param2, null, null, null, null);
   }
   
   @Test
   public void test_paramE3() {
      final long id = RandomUtils.nextLong();
      final String param3 = RandomStringUtils.randomAlphanumeric(11);
      final ResourceEDto resourceDto = new ResourceEDto(id).setParamE3(param3);
      
      assertResourceEDto(resourceDto, id, null, null, param3, null, null, null);
   }
   
   @Test
   public void test_paramE4() {
      final long id = RandomUtils.nextLong();
      final Long param4 = RandomUtils.nextLong();
      final ResourceEDto resourceDto = new ResourceEDto(id).setParamE4(param4);
      
      assertResourceEDto(resourceDto, id, null, null, null, param4, null, null);
   }
   
   @Test
   public void test_paramE5() {
      final long id = RandomUtils.nextLong();
      final Boolean param5 = RandomUtils.nextBoolean();
      final ResourceEDto resourceDto = new ResourceEDto(id).setParamE5(param5);
      
      assertResourceEDto(resourceDto, id, null, null, null, null, param5, null);
   }
   
   @Test
   public void test_paramE6() {
      final long id = RandomUtils.nextLong();
      final List<Integer> param6 = DtoHelper.createResourceEDtoParam6List();
      final ResourceEDto resourceDto = new ResourceEDto(id).setParamE6(param6);
      
      assertResourceEDto(resourceDto, id, null, null, null, null, null, param6);
   }
   
   @Test
   public void test_equals() {
      final ResourceEDto resourceDto = new ResourceEDto(RandomUtils.nextLong());
      assertEquals(resourceDto, resourceDto);
      assertNotEquals(resourceDto, null);
      assertNotEquals(resourceDto, new Object());
      
      final ResourceEDto resourceDtoOther = new ResourceEDto(resourceDto.getIdE());
      assertEquals(resourceDto, resourceDtoOther);
      resourceDtoOther.setParamE1(RandomUtils.nextInt());
      assertNotEquals(resourceDto, resourceDtoOther);
   }
   
   @Test
   public void test_equals_allFields() {
      final ResourceEDto resourceDto = DtoHelper.createResourceEDto();
      final ResourceEDto resourceDtoOther = DtoHelper.cloneResourceEDto(resourceDto);
      assertEquals(resourceDto, resourceDtoOther);
   }
   
   @Test
   public void test_equals_FieldsDiffer() {
      final ResourceEDto resourceDto = DtoHelper.createResourceEDto();
      final ResourceEDto resourceDtoOther = DtoHelper.cloneResourceEDto(resourceDto);
      assertEquals(resourceDto, resourceDtoOther);
      
      resourceDtoOther.setParamE1(RandomUtils.nextInt());
      assertNotEquals(resourceDto, resourceDtoOther);
   
      resourceDtoOther.setParamE1(resourceDto.getParamE1());
      resourceDtoOther.setParamE2(RandomStringUtils.randomAlphanumeric(12));
      assertNotEquals(resourceDto, resourceDtoOther);
   
      resourceDtoOther.setParamE2(resourceDto.getParamE2());
      resourceDtoOther.setParamE3(RandomStringUtils.randomAlphanumeric(13));
      assertNotEquals(resourceDto, resourceDtoOther);
      
      resourceDtoOther.setParamE3(resourceDto.getParamE3());
      resourceDtoOther.setParamE4(RandomUtils.nextLong());
      assertNotEquals(resourceDto, resourceDtoOther);
      
      resourceDtoOther.setParamE4(resourceDto.getParamE4());
      resourceDtoOther.setParamE5(!resourceDto.getParamE5());
      assertNotEquals(resourceDto, resourceDtoOther);
      
      resourceDtoOther.setParamE5(resourceDto.getParamE5());
      resourceDtoOther.setParamE6(DtoHelper.createResourceEDtoParam6List());
      assertNotEquals(resourceDto, resourceDtoOther);
      
      final ResourceEDto resourceDtoOtherId = new ResourceEDto(resourceDto.getIdE() - 1)
            .setParamE1(resourceDto.getParamE1())
            .setParamE2(resourceDto.getParamE2())
            .setParamE3(resourceDto.getParamE3())
            .setParamE4(resourceDto.getParamE4())
            .setParamE5(resourceDto.getParamE5())
            .setParamE6(resourceDto.getParamE6());
      assertNotEquals(resourceDto, resourceDtoOtherId);
   }
   
   @Test
   public void test_hashCode() {
      final ResourceEDto resourceDto = DtoHelper.createResourceEDto();
      assertEquals(resourceDto.hashCode(), Objects.hash(resourceDto.getIdE(),
            resourceDto.getParamE1(), resourceDto.getParamE2(), resourceDto.getParamE3(),
            resourceDto.getParamE4(), resourceDto.getParamE5(), resourceDto.getParamE6()));
   }
   
   @Test
   public void test_toString() {
      final ResourceEDto resourceDto = DtoHelper.createResourceEDto();
      assertThat(resourceDto).hasToString("ResourceEDto{" + "idE=" + resourceDto.getIdE() +
            ", paramE1=" + resourceDto.getParamE1() + ", paramE2='" + resourceDto.getParamE2() +
            '\'' + ", paramE3='" + resourceDto.getParamE3() + '\'' + ", paramE4=" +
            resourceDto.getParamE4() + ", paramE5=" + resourceDto.getParamE5() + ", paramE6=" +
            resourceDto.getParamE6() + '}');
   }
   
   @Test
   public void test_JSON_serialization() {
      final ResourceEDto resourceDto = DtoHelper.createResourceEDto();
      final String expectedJson = getJsonRepresentation(resourceDto);
      
      DtoHelper.assertDtoToJson(resourceDto, expectedJson);
   }
   
   private String getJsonRepresentation(final ResourceEDto pResourceEDto) {
      return String.format(
            "{\"id\":%d,\"param1\":%d,\"param2\":\"%s\",\"param3\":\"%s\",\"param4\":%d," +
                  "\"param5\":%b,\"param6\":[%s]}",
            pResourceEDto.getIdE(), pResourceEDto.getParamE1(), pResourceEDto.getParamE2(),
            pResourceEDto.getParamE3(), pResourceEDto.getParamE4(), pResourceEDto.getParamE5(),
            StringUtils.join(pResourceEDto.getParamE6(), ","));
   }
   
   @Test
   public void test_JSON_deserialization() {
      final ResourceEDto resourceDtoExpected = DtoHelper.createResourceEDto();
      final String json = getJsonRepresentation(resourceDtoExpected);
      
      try {
         final ObjectMapper mapper = new ObjectMapper();
         final ResourceEDto resourceDto = mapper.readValue(json, ResourceEDto.class);
         assertEquals(resourceDtoExpected, resourceDto);
      } catch (final IOException ignored) {
         fail("Deserialization should not throw an exception.");
      }
   }
}
