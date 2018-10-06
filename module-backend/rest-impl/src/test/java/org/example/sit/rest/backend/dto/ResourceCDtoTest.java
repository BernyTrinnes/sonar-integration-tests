package org.example.sit.rest.backend.dto;

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
 * Tests for {@code ResourceCDto}.
 */
@SuppressWarnings({"javadoc", "boxing"})
public class ResourceCDtoTest {
   @Test
   public void test_Constructor_Success() {
      final long id = RandomUtils.nextLong();
      final ResourceCDto resourceDto = new ResourceCDto(id);
      
      assertResourceCDto(resourceDto, id, null, null, null, null, null, null, null);
   }
   
   private void assertResourceCDto(final ResourceCDto pResourceDto, final long pId,
         final String pParam1, final Integer pParam2, final String pParam3, final String pParam4,
         final Boolean pParam5, final Long pParam6, final List<String> pParam7) {
      assertNotNull(pResourceDto);
      assertThat(pResourceDto.getIdC()).isEqualTo(pId);
      if (null == pParam1) {
         assertNull(pResourceDto.getParamC1());
      } else {
         assertThat(pResourceDto.getParamC1()).isNotNull().isEqualTo(pParam1);
      }
      if (null == pParam2) {
         assertNull(pResourceDto.getParamC2());
      } else {
         assertThat(pResourceDto.getParamC2()).isNotNull().isEqualTo(pParam2);
      }
      if (null == pParam3) {
         assertNull(pResourceDto.getParamC3());
      } else {
         assertThat(pResourceDto.getParamC3()).isNotNull().isEqualTo(pParam3);
      }
      if (null == pParam4) {
         assertNull(pResourceDto.getParamC4());
      } else {
         assertThat(pResourceDto.getParamC4()).isNotNull().isEqualTo(pParam4);
      }
      if (null == pParam5) {
         assertNull(pResourceDto.getParamC5());
      } else {
         assertThat(pResourceDto.getParamC5()).isNotNull().isEqualTo(pParam5);
      }
      if (null == pParam6) {
         assertNull(pResourceDto.getParamC6());
      } else {
         assertThat(pResourceDto.getParamC6()).isNotNull().isEqualTo(pParam6);
      }
      if (null == pParam7) {
         assertNull(pResourceDto.getParamC7());
      } else {
         assertThat(pResourceDto.getParamC7()).isNotNull().hasSameSizeAs(pParam7)
               .containsExactlyElementsOf(pParam7);
      }
   }
   
   @Test
   public void test_paramC1() {
      final long id = RandomUtils.nextLong();
      final String param1 = RandomStringUtils.randomAlphanumeric(10);
      final ResourceCDto resourceDto = new ResourceCDto(id).setParamC1(param1);
      
      assertResourceCDto(resourceDto, id, param1, null, null, null, null, null, null);
   }
   
   @Test
   public void test_paramC2() {
      final long id = RandomUtils.nextLong();
      final Integer param2 = RandomUtils.nextInt();
      final ResourceCDto resourceDto = new ResourceCDto(id).setParamC2(param2);
      
      assertResourceCDto(resourceDto, id, null, param2, null, null, null, null, null);
   }
   
   @Test
   public void test_paramC3() {
      final long id = RandomUtils.nextLong();
      final String param3 = RandomStringUtils.randomAlphanumeric(11);
      final ResourceCDto resourceDto = new ResourceCDto(id).setParamC3(param3);
      
      assertResourceCDto(resourceDto, id, null, null, param3, null, null, null, null);
   }
   
   @Test
   public void test_paramC4() {
      final long id = RandomUtils.nextLong();
      final String param4 = RandomStringUtils.randomAlphanumeric(12);
      final ResourceCDto resourceDto = new ResourceCDto(id).setParamC4(param4);
      
      assertResourceCDto(resourceDto, id, null, null, null, param4, null, null, null);
   }
   
   @Test
   public void test_paramC5() {
      final long id = RandomUtils.nextLong();
      final Boolean param5 = RandomUtils.nextBoolean();
      final ResourceCDto resourceDto = new ResourceCDto(id).setParamC5(param5);
      
      assertResourceCDto(resourceDto, id, null, null, null, null, param5, null, null);
   }
   
   @Test
   public void test_paramC6() {
      final long id = RandomUtils.nextLong();
      final Long param6 = RandomUtils.nextLong();
      final ResourceCDto resourceDto = new ResourceCDto(id).setParamC6(param6);
      
      assertResourceCDto(resourceDto, id, null, null, null, null, null, param6, null);
   }
   
   @Test
   public void test_paramC7() {
      final long id = RandomUtils.nextLong();
      final List<String> param7 = DtoHelper.createResourceCDtoParam7List();
      final ResourceCDto resourceDto = new ResourceCDto(id).setParamC7(param7);
      
      assertResourceCDto(resourceDto, id, null, null, null, null, null, null, param7);
   }
   
   @Test
   public void test_equals() {
      final ResourceCDto resourceDto = new ResourceCDto(RandomUtils.nextLong());
      assertEquals(resourceDto, resourceDto);
      assertNotEquals(resourceDto, null);
      assertNotEquals(resourceDto, new Object());
      
      final ResourceCDto resourceDtoOther = new ResourceCDto(resourceDto.getIdC());
      assertEquals(resourceDto, resourceDtoOther);
      resourceDtoOther.setParamC1(RandomStringUtils.randomAlphanumeric(10));
      assertNotEquals(resourceDto, resourceDtoOther);
   }
   
   @Test
   public void test_equals_allFields() {
      final ResourceCDto resourceDto = DtoHelper.createResourceCDto();
      final ResourceCDto resourceDtoOther = DtoHelper.cloneResourceCDto(resourceDto);
      assertEquals(resourceDto, resourceDtoOther);
   }
   
   @Test
   public void test_equals_FieldsDiffer() {
      final ResourceCDto resourceDto = DtoHelper.createResourceCDto();
      final ResourceCDto resourceDtoOther = DtoHelper.cloneResourceCDto(resourceDto);
      assertEquals(resourceDto, resourceDtoOther);
      
      resourceDtoOther.setParamC1(RandomStringUtils.randomAlphanumeric(15));
      assertNotEquals(resourceDto, resourceDtoOther);
   
      resourceDtoOther.setParamC1(resourceDto.getParamC1());
      resourceDtoOther.setParamC2(RandomUtils.nextInt());
      assertNotEquals(resourceDto, resourceDtoOther);
   
      resourceDtoOther.setParamC2(resourceDto.getParamC2());
      resourceDtoOther.setParamC3(RandomStringUtils.randomAlphanumeric(16));
      assertNotEquals(resourceDto, resourceDtoOther);
   
      resourceDtoOther.setParamC3(resourceDto.getParamC3());
      resourceDtoOther.setParamC4(RandomStringUtils.randomAlphanumeric(17));
      assertNotEquals(resourceDto, resourceDtoOther);
   
      resourceDtoOther.setParamC4(resourceDto.getParamC4());
      resourceDtoOther.setParamC5(!resourceDto.getParamC5());
      assertNotEquals(resourceDto, resourceDtoOther);
   
      resourceDtoOther.setParamC5(resourceDto.getParamC5());
      resourceDtoOther.setParamC6(RandomUtils.nextLong());
      assertNotEquals(resourceDto, resourceDtoOther);
   
      resourceDtoOther.setParamC6(resourceDto.getParamC6());
      resourceDtoOther.setParamC7(DtoHelper.createResourceCDtoParam7List());
      assertNotEquals(resourceDto, resourceDtoOther);
      
      final ResourceCDto resourceDtoOtherId = new ResourceCDto(resourceDto.getIdC() - 1)
            .setParamC1(resourceDto.getParamC1())
            .setParamC2(resourceDto.getParamC2())
            .setParamC3(resourceDto.getParamC3())
            .setParamC4(resourceDto.getParamC4())
            .setParamC5(resourceDto.getParamC5())
            .setParamC6(resourceDto.getParamC6())
            .setParamC7(resourceDto.getParamC7());
      assertNotEquals(resourceDto, resourceDtoOtherId);
   }
   
   @Test
   public void test_hashCode() {
      final ResourceCDto resourceDto = DtoHelper.createResourceCDto();
      assertEquals(resourceDto.hashCode(), Objects.hash(resourceDto.getIdC(),
            resourceDto.getParamC1(), resourceDto.getParamC2(), resourceDto.getParamC3(),
            resourceDto.getParamC4(), resourceDto.getParamC5(), resourceDto.getParamC6(),
            resourceDto.getParamC7()));
   }
   
   @Test
   public void test_toString() {
      final ResourceCDto resourceDto = DtoHelper.createResourceCDto();
      assertThat(resourceDto).hasToString("ResourceCDto{" + "idC=" + resourceDto.getIdC() +
            ", paramC1='" + resourceDto.getParamC1() + '\'' + ", paramC2=" +
            resourceDto.getParamC2() + ", paramC3='" + resourceDto.getParamC3() + '\'' +
            ", paramC4='" + resourceDto.getParamC4() + '\'' + ", paramC5=" +
            resourceDto.getParamC5() + ", paramC6=" + resourceDto.getParamC6() + ", paramC7=" +
            resourceDto.getParamC7() + '}');
   }
   
   @Test
   public void test_JSON_serialization() {
      final ResourceCDto resourceDto = DtoHelper.createResourceCDto();
      final String expectedJson = getJsonRepresentation(resourceDto);
      
      DtoHelper.assertDtoToJson(resourceDto, expectedJson);
   }
   
   private String getJsonRepresentation(final ResourceCDto pResourceCDto) {
      return String.format(
            "{\"id\":%d,\"param1\":\"%s\",\"param2\":%d,\"param3\":\"%s\",\"param4\":\"%s\"," +
                  "\"param5\":%b,\"param6\":%d,\"param7\":[\"%s\"]}",
            pResourceCDto.getIdC(), pResourceCDto.getParamC1(), pResourceCDto.getParamC2(),
            pResourceCDto.getParamC3(), pResourceCDto.getParamC4(), pResourceCDto.getParamC5(),
            pResourceCDto.getParamC6(), StringUtils.join(pResourceCDto.getParamC7(), "\",\""));
   }
   
   @Test
   public void test_JSON_deserialization() {
      final ResourceCDto resourceDtoExpected = DtoHelper.createResourceCDto();
      final String json = getJsonRepresentation(resourceDtoExpected);
      
      try {
         final ObjectMapper mapper = new ObjectMapper();
         final ResourceCDto resourceDto = mapper.readValue(json, ResourceCDto.class);
         assertEquals(resourceDtoExpected, resourceDto);
      } catch (final IOException ignored) {
         fail("Deserialization should not throw an exception.");
      }
   }
}
