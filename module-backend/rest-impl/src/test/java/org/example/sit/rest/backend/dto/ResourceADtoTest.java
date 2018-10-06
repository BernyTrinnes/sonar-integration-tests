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
 * Tests for {@code ResourceADto}.
 */
@SuppressWarnings({"javadoc", "boxing"})
public class ResourceADtoTest {
   @Test
   public void test_Constructor_Success() {
      final long id = RandomUtils.nextLong();
      final ResourceADto resourceDto = new ResourceADto(id);
      
      assertResourceADto(resourceDto, id, null, null, null);
   }
   
   private void assertResourceADto(final ResourceADto pResourceDto, final long pId,
         final String pParam1, final Boolean pParam2, final List<Long> pParam3) {
      assertNotNull(pResourceDto);
      assertThat(pResourceDto.getIdA()).isEqualTo(pId);
      if (null == pParam1) {
         assertNull(pResourceDto.getParamA1());
      } else {
         assertThat(pResourceDto.getParamA1()).isNotNull().isEqualTo(pParam1);
      }
      if (null == pParam2) {
         assertNull(pResourceDto.getParamA2());
      } else {
         assertThat(pResourceDto.getParamA2()).isNotNull().isEqualTo(pParam2);
      }
      if (null == pParam3) {
         assertNull(pResourceDto.getParamA3());
      } else {
         assertThat(pResourceDto.getParamA3()).isNotNull().hasSameSizeAs(pParam3)
               .containsExactlyElementsOf(pParam3);
      }
   }
   
   @Test
   public void test_paramA1() {
      final long id = RandomUtils.nextLong();
      final String param1 = RandomStringUtils.randomAlphanumeric(10);
      final ResourceADto resourceDto = new ResourceADto(id).setParamA1(param1);
      
      assertResourceADto(resourceDto, id, param1, null, null);
   }
   
   @Test
   public void test_paramA2() {
      final long id = RandomUtils.nextLong();
      final boolean param2 = RandomUtils.nextBoolean();
      final ResourceADto resourceDto = new ResourceADto(id).setParamA2(param2);
      
      assertResourceADto(resourceDto, id, null, param2, null);
   }
   
   @Test
   public void test_paramA3() {
      final long id = RandomUtils.nextLong();
      final List<Long> param3 = DtoHelper.createResourceADtoParam3List();
      final ResourceADto resourceDto = new ResourceADto(id).setParamA3(param3);
      
      assertResourceADto(resourceDto, id, null, null, param3);
   }
   
   @Test
   public void test_equals() {
      final ResourceADto resourceDto = new ResourceADto(RandomUtils.nextLong());
      assertEquals(resourceDto, resourceDto);
      assertNotEquals(resourceDto, null);
      assertNotEquals(resourceDto, new Object());
      
      final ResourceADto resourceDtoOther = new ResourceADto(resourceDto.getIdA());
      assertEquals(resourceDto, resourceDtoOther);
      resourceDtoOther.setParamA1(RandomStringUtils.randomAlphanumeric(10));
      assertNotEquals(resourceDto, resourceDtoOther);
   }
   
   @Test
   public void test_equals_allFields() {
      final ResourceADto resourceDto = DtoHelper.createResourceADto();
      final ResourceADto resourceDtoOther = DtoHelper.cloneResourceADto(resourceDto);
      assertEquals(resourceDto, resourceDtoOther);
   }
   
   @Test
   public void test_equals_FieldsDiffer() {
      final ResourceADto resourceDto = DtoHelper.createResourceADto();
      final ResourceADto resourceDtoOther = DtoHelper.cloneResourceADto(resourceDto);
      assertEquals(resourceDto, resourceDtoOther);
      
      resourceDtoOther.setParamA1(RandomStringUtils.randomAlphanumeric(11));
      assertNotEquals(resourceDto, resourceDtoOther);
      
      resourceDtoOther.setParamA1(resourceDto.getParamA1());
      resourceDtoOther.setParamA2(!resourceDto.getParamA2());
      assertNotEquals(resourceDto, resourceDtoOther);
      
      resourceDtoOther.setParamA2(resourceDto.getParamA2());
      resourceDtoOther.setParamA3(DtoHelper.createResourceADtoParam3List());
      assertNotEquals(resourceDto, resourceDtoOther);
      
      final ResourceADto resourceDtoOtherId = new ResourceADto(resourceDto.getIdA() - 1)
            .setParamA1(resourceDto.getParamA1())
            .setParamA2(resourceDto.getParamA2())
            .setParamA3(resourceDto.getParamA3());
      assertNotEquals(resourceDto, resourceDtoOtherId);
   }
   
   @Test
   public void test_hashCode() {
      final ResourceADto resourceDto = DtoHelper.createResourceADto();
      assertEquals(resourceDto.hashCode(), Objects.hash(resourceDto.getIdA(),
            resourceDto.getParamA1(), resourceDto.getParamA2(), resourceDto.getParamA3()));
   }
   
   @Test
   public void test_toString() {
      final ResourceADto resourceDto = DtoHelper.createResourceADto();
      assertThat(resourceDto).hasToString("ResourceADto{" + "idA=" +
            resourceDto.getIdA() + ", paramA1='" + resourceDto.getParamA1() + '\'' + ", paramA2=" +
            resourceDto.getParamA2() + ", paramA3=" + resourceDto.getParamA3() + '}');
   }
   
   @Test
   public void test_JSON_serialization() {
      final ResourceADto resourceDto = DtoHelper.createResourceADto();
      final String expectedJson = getJsonRepresentation(resourceDto);
      
      DtoHelper.assertDtoToJson(resourceDto, expectedJson);
   }
   
   private String getJsonRepresentation(final ResourceADto pResourceADto) {
      return String.format("{\"id\":%d,\"param1\":\"%s\",\"param2\":%b,\"param3\":[%s]}",
            pResourceADto.getIdA(), pResourceADto.getParamA1(),
            pResourceADto.getParamA2(), StringUtils.join(pResourceADto.getParamA3(), ","));
   }
   
   @Test
   public void test_JSON_deserialization() {
      final ResourceADto resourceDtoExpected = DtoHelper.createResourceADto();
      final String json = getJsonRepresentation(resourceDtoExpected);
      
      try {
         final ObjectMapper mapper = new ObjectMapper();
         final ResourceADto resourceDto = mapper.readValue(json, ResourceADto.class);
         assertEquals(resourceDtoExpected, resourceDto);
      } catch (final IOException ignored) {
         fail("Deserialization should not throw an exception.");
      }
   }
}
