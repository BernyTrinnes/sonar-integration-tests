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
 * Tests for {@code ResourceFDto}.
 */
@SuppressWarnings({"javadoc", "boxing"})
public class ResourceFDtoTest {
   @Test
   public void test_Constructor_Success() {
      final long id = RandomUtils.nextLong();
      final ResourceFDto resourceDto = new ResourceFDto(id);
      
      assertResourceFDto(resourceDto, id, null, null, null, null, null, null, null, null);
   }
   
   private void assertResourceFDto(final ResourceFDto pResourceDto, final long pId,
         final String pParam1, final Integer pParam2, final String pParam3, final String pParam4,
         final Boolean pParam5, final Long pParam6, final String pParam7,
         final List<String> pParam8) {
      assertNotNull(pResourceDto);
      assertThat(pResourceDto.getIdF()).isEqualTo(pId);
      if (null == pParam1) {
         assertNull(pResourceDto.getParamF1());
      } else {
         assertThat(pResourceDto.getParamF1()).isNotNull().isEqualTo(pParam1);
      }
      if (null == pParam2) {
         assertNull(pResourceDto.getParamF2());
      } else {
         assertThat(pResourceDto.getParamF2()).isNotNull().isEqualTo(pParam2);
      }
      if (null == pParam3) {
         assertNull(pResourceDto.getParamF3());
      } else {
         assertThat(pResourceDto.getParamF3()).isNotNull().isEqualTo(pParam3);
      }
      if (null == pParam4) {
         assertNull(pResourceDto.getParamF4());
      } else {
         assertThat(pResourceDto.getParamF4()).isNotNull().isEqualTo(pParam4);
      }
      if (null == pParam5) {
         assertNull(pResourceDto.getParamF5());
      } else {
         assertThat(pResourceDto.getParamF5()).isNotNull().isEqualTo(pParam5);
      }
      if (null == pParam6) {
         assertNull(pResourceDto.getParamF6());
      } else {
         assertThat(pResourceDto.getParamF6()).isNotNull().isEqualTo(pParam6);
      }
      if (null == pParam7) {
         assertNull(pResourceDto.getParamF7());
      } else {
         assertThat(pResourceDto.getParamF7()).isNotNull().isEqualTo(pParam7);
      }
      if (null == pParam8) {
         assertNull(pResourceDto.getParamF8());
      } else {
         assertThat(pResourceDto.getParamF8()).isNotNull().hasSameSizeAs(pParam8)
               .containsExactlyElementsOf(pParam8);
      }
   }
   
   @Test
   public void test_paramF1() {
      final long id = RandomUtils.nextLong();
      final String param1 = RandomStringUtils.randomAlphanumeric(10);
      final ResourceFDto resourceDto = new ResourceFDto(id).setParamF1(param1);
      
      assertResourceFDto(resourceDto, id, param1, null, null, null, null, null, null, null);
   }
   
   @Test
   public void test_paramF2() {
      final long id = RandomUtils.nextLong();
      final Integer param2 = RandomUtils.nextInt();
      final ResourceFDto resourceDto = new ResourceFDto(id).setParamF2(param2);
      
      assertResourceFDto(resourceDto, id, null, param2, null, null, null, null, null, null);
   }
   
   @Test
   public void test_paramF3() {
      final long id = RandomUtils.nextLong();
      final String param3 = RandomStringUtils.randomAlphanumeric(11);
      final ResourceFDto resourceDto = new ResourceFDto(id).setParamF3(param3);
      
      assertResourceFDto(resourceDto, id, null, null, param3, null, null, null, null, null);
   }
   
   @Test
   public void test_paramF4() {
      final long id = RandomUtils.nextLong();
      final String param4 = RandomStringUtils.randomAlphanumeric(12);
      final ResourceFDto resourceDto = new ResourceFDto(id).setParamF4(param4);
      
      assertResourceFDto(resourceDto, id, null, null, null, param4, null, null, null, null);
   }
   
   @Test
   public void test_paramF5() {
      final long id = RandomUtils.nextLong();
      final Boolean param5 = RandomUtils.nextBoolean();
      final ResourceFDto resourceDto = new ResourceFDto(id).setParamF5(param5);
      
      assertResourceFDto(resourceDto, id, null, null, null, null, param5, null, null, null);
   }
   
   @Test
   public void test_paramF6() {
      final long id = RandomUtils.nextLong();
      final Long param6 = RandomUtils.nextLong();
      final ResourceFDto resourceDto = new ResourceFDto(id).setParamF6(param6);
      
      assertResourceFDto(resourceDto, id, null, null, null, null, null, param6, null, null);
   }
   
   @Test
   public void test_paramF7() {
      final long id = RandomUtils.nextLong();
      final String param7 = RandomStringUtils.randomAlphanumeric(13);
      final ResourceFDto resourceDto = new ResourceFDto(id).setParamF7(param7);
      
      assertResourceFDto(resourceDto, id, null, null, null, null, null, null, param7, null);
   }
   
   @Test
   public void test_paramF8() {
      final long id = RandomUtils.nextLong();
      final List<String> param8 = DtoHelper.createResourceFDtoParam8List();
      final ResourceFDto resourceDto = new ResourceFDto(id).setParamF8(param8);
      
      assertResourceFDto(resourceDto, id, null, null, null, null, null, null, null, param8);
   }
   
   @Test
   public void test_equals() {
      final ResourceFDto resourceDto = new ResourceFDto(RandomUtils.nextLong());
      assertEquals(resourceDto, resourceDto);
      assertNotEquals(resourceDto, null);
      assertNotEquals(resourceDto, new Object());
      
      final ResourceFDto resourceDtoOther = new ResourceFDto(resourceDto.getIdF());
      assertEquals(resourceDto, resourceDtoOther);
      resourceDtoOther.setParamF1(RandomStringUtils.randomAlphanumeric(10));
      assertNotEquals(resourceDto, resourceDtoOther);
   }
   
   @Test
   public void test_equals_allFields() {
      final ResourceFDto resourceDto = DtoHelper.createResourceFDto();
      final ResourceFDto resourceDtoOther = DtoHelper.cloneResourceFDto(resourceDto);
      assertEquals(resourceDto, resourceDtoOther);
   }
   
   @Test
   public void test_equals_FieldsDiffer() {
      final ResourceFDto resourceDto = DtoHelper.createResourceFDto();
      final ResourceFDto resourceDtoOther = DtoHelper.cloneResourceFDto(resourceDto);
      assertEquals(resourceDto, resourceDtoOther);
      
      resourceDtoOther.setParamF1(RandomStringUtils.randomAlphanumeric(15));
      assertNotEquals(resourceDto, resourceDtoOther);
      
      resourceDtoOther.setParamF1(resourceDto.getParamF1());
      resourceDtoOther.setParamF2(RandomUtils.nextInt());
      assertNotEquals(resourceDto, resourceDtoOther);
      
      resourceDtoOther.setParamF2(resourceDto.getParamF2());
      resourceDtoOther.setParamF3(RandomStringUtils.randomAlphanumeric(16));
      assertNotEquals(resourceDto, resourceDtoOther);
      
      resourceDtoOther.setParamF3(resourceDto.getParamF3());
      resourceDtoOther.setParamF4(RandomStringUtils.randomAlphanumeric(17));
      assertNotEquals(resourceDto, resourceDtoOther);
      
      resourceDtoOther.setParamF4(resourceDto.getParamF4());
      resourceDtoOther.setParamF5(!resourceDto.getParamF5());
      assertNotEquals(resourceDto, resourceDtoOther);
      
      resourceDtoOther.setParamF5(resourceDto.getParamF5());
      resourceDtoOther.setParamF6(RandomUtils.nextLong());
      assertNotEquals(resourceDto, resourceDtoOther);
      
      resourceDtoOther.setParamF6(resourceDto.getParamF6());
      resourceDtoOther.setParamF7(RandomStringUtils.randomAlphanumeric(18));
      assertNotEquals(resourceDto, resourceDtoOther);
      
      resourceDtoOther.setParamF7(resourceDto.getParamF7());
      resourceDtoOther.setParamF8(DtoHelper.createResourceFDtoParam8List());
      assertNotEquals(resourceDto, resourceDtoOther);
      
      final ResourceFDto resourceDtoOtherId = new ResourceFDto(resourceDto.getIdF() - 1)
            .setParamF1(resourceDto.getParamF1())
            .setParamF2(resourceDto.getParamF2())
            .setParamF3(resourceDto.getParamF3())
            .setParamF4(resourceDto.getParamF4())
            .setParamF5(resourceDto.getParamF5())
            .setParamF6(resourceDto.getParamF6())
            .setParamF7(resourceDto.getParamF7())
            .setParamF8(resourceDto.getParamF8());
      assertNotEquals(resourceDto, resourceDtoOtherId);
   }
   
   @Test
   public void test_hashCode() {
      final ResourceFDto resourceDto = DtoHelper.createResourceFDto();
      assertEquals(resourceDto.hashCode(), Objects.hash(resourceDto.getIdF(),
            resourceDto.getParamF1(), resourceDto.getParamF2(), resourceDto.getParamF3(),
            resourceDto.getParamF4(), resourceDto.getParamF5(), resourceDto.getParamF6(),
            resourceDto.getParamF7(), resourceDto.getParamF8()));
   }
   
   @Test
   public void test_toString() {
      final ResourceFDto resourceDto = DtoHelper.createResourceFDto();
      assertThat(resourceDto).hasToString(
            "ResourceFDto{" + "idF=" + resourceDto.getIdF() + ", paramF1='" +
                  resourceDto.getParamF1() + '\'' + ", paramF2=" + resourceDto.getParamF2() +
                  ", paramF3='" + resourceDto.getParamF3() + '\'' + ", paramF4='" +
                  resourceDto.getParamF4() + '\'' + ", paramF5=" + resourceDto.getParamF5() +
                  ", paramF6=" + resourceDto.getParamF6() + ", paramF7='" +
                  resourceDto.getParamF7() + '\'' + ", paramF8=" + resourceDto.getParamF8() + '}');
   }
   
   @Test
   public void test_JSON_serialization() {
      final ResourceFDto resourceDto = DtoHelper.createResourceFDto();
      final String expectedJson = getJsonRepresentation(resourceDto);
      
      DtoHelper.assertDtoToJson(resourceDto, expectedJson);
   }
   
   private String getJsonRepresentation(final ResourceFDto pResourceFDto) {
      return String.format(
            "{\"id\":%d,\"param1\":\"%s\",\"param2\":%d,\"param3\":\"%s\",\"param4\":\"%s\"," +
                  "\"param5\":%b,\"param6\":%d,\"param7\":\"%s\",\"param8\":[\"%s\"]}",
            pResourceFDto.getIdF(), pResourceFDto.getParamF1(), pResourceFDto.getParamF2(),
            pResourceFDto.getParamF3(), pResourceFDto.getParamF4(), pResourceFDto.getParamF5(),
            pResourceFDto.getParamF6(), pResourceFDto.getParamF7(),
            StringUtils.join(pResourceFDto.getParamF8(), "\",\""));
   }
   
   @Test
   public void test_JSON_deserialization() {
      final ResourceFDto resourceDtoExpected = DtoHelper.createResourceFDto();
      final String json = getJsonRepresentation(resourceDtoExpected);
      
      try {
         final ObjectMapper mapper = new ObjectMapper();
         final ResourceFDto resourceDto = mapper.readValue(json, ResourceFDto.class);
         assertEquals(resourceDtoExpected, resourceDto);
      } catch (final IOException ignored) {
         fail("Deserialization should not throw an exception.");
      }
   }
}
