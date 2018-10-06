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
 * Tests for {@code ResourceBDto}.
 */
@SuppressWarnings({"javadoc", "boxing"})
public class ResourceBDtoTest {
   @Test
   public void test_Constructor_Success() {
      final long id = RandomUtils.nextLong();
      final ResourceBDto resourceDto = new ResourceBDto(id);
      
      assertResourceBDto(resourceDto, id, null, null, null, null, null);
   }
   
   private void assertResourceBDto(final ResourceBDto pResourceDto, final long pId,
         final Integer pParam1, final String pParam2, final String pParam3, final Boolean pParam4,
         final List<Integer> pParam5) {
      assertNotNull(pResourceDto);
      assertThat(pResourceDto.getIdB()).isEqualTo(pId);
      if (null == pParam1) {
         assertNull(pResourceDto.getParamB1());
      } else {
         assertThat(pResourceDto.getParamB1()).isNotNull().isEqualTo(pParam1);
      }
      if (null == pParam2) {
         assertNull(pResourceDto.getParamB2());
      } else {
         assertThat(pResourceDto.getParamB2()).isNotNull().isEqualTo(pParam2);
      }
      if (null == pParam3) {
         assertNull(pResourceDto.getParamB3());
      } else {
         assertThat(pResourceDto.getParamB3()).isNotNull().isEqualTo(pParam3);
      }
      if (null == pParam4) {
         assertNull(pResourceDto.getParamB4());
      } else {
         assertThat(pResourceDto.getParamB4()).isNotNull().isEqualTo(pParam4);
      }
      if (null == pParam5) {
         assertNull(pResourceDto.getParamB5());
      } else {
         assertThat(pResourceDto.getParamB5()).isNotNull().hasSameSizeAs(pParam5)
               .containsExactlyElementsOf(pParam5);
      }
   }
   
   @Test
   public void test_paramB1() {
      final long id = RandomUtils.nextLong();
      final Integer param1 = RandomUtils.nextInt();
      final ResourceBDto resourceDto = new ResourceBDto(id).setParamB1(param1);
      
      assertResourceBDto(resourceDto, id, param1, null, null, null, null);
   }
   
   @Test
   public void test_paramB2() {
      final long id = RandomUtils.nextLong();
      final String param2 = RandomStringUtils.randomAlphanumeric(10);
      final ResourceBDto resourceDto = new ResourceBDto(id).setParamB2(param2);
      
      assertResourceBDto(resourceDto, id, null, param2, null, null, null);
   }
   
   @Test
   public void test_paramB3() {
      final long id = RandomUtils.nextLong();
      final String param3 = RandomStringUtils.randomAlphanumeric(11);
      final ResourceBDto resourceDto = new ResourceBDto(id).setParamB3(param3);
      
      assertResourceBDto(resourceDto, id, null, null, param3, null, null);
   }
   
   @Test
   public void test_paramB4() {
      final long id = RandomUtils.nextLong();
      final Boolean param4 = RandomUtils.nextBoolean();
      final ResourceBDto resourceDto = new ResourceBDto(id).setParamB4(param4);
      
      assertResourceBDto(resourceDto, id, null, null, null, param4, null);
   }
   
   @Test
   public void test_paramB5() {
      final long id = RandomUtils.nextLong();
      final List<Integer> param5 = DtoHelper.createResourceBDtoParam5List();
      final ResourceBDto resourceDto = new ResourceBDto(id).setParamB5(param5);
      
      assertResourceBDto(resourceDto, id, null, null, null, null, param5);
   }
   
   @Test
   public void test_equals() {
      final ResourceBDto resourceDto = new ResourceBDto(RandomUtils.nextLong());
      assertEquals(resourceDto, resourceDto);
      assertNotEquals(resourceDto, null);
      assertNotEquals(resourceDto, new Object());
      
      final ResourceBDto resourceDtoOther = new ResourceBDto(resourceDto.getIdB());
      assertEquals(resourceDto, resourceDtoOther);
      resourceDtoOther.setParamB1(RandomUtils.nextInt());
      assertNotEquals(resourceDto, resourceDtoOther);
   }
   
   @Test
   public void test_equals_allFields() {
      final ResourceBDto resourceDto = DtoHelper.createResourceBDto();
      final ResourceBDto resourceDtoOther = DtoHelper.cloneResourceBDto(resourceDto);
      assertEquals(resourceDto, resourceDtoOther);
   }
   
   @Test
   public void test_equals_FieldsDiffer() {
      final ResourceBDto resourceDto = DtoHelper.createResourceBDto();
      final ResourceBDto resourceDtoOther = DtoHelper.cloneResourceBDto(resourceDto);
      assertEquals(resourceDto, resourceDtoOther);
      
      resourceDtoOther.setParamB1(RandomUtils.nextInt());
      assertNotEquals(resourceDto, resourceDtoOther);
   
      resourceDtoOther.setParamB1(resourceDto.getParamB1());
      resourceDtoOther.setParamB2(RandomStringUtils.randomAlphanumeric(12));
      assertNotEquals(resourceDto, resourceDtoOther);
   
      resourceDtoOther.setParamB2(resourceDto.getParamB2());
      resourceDtoOther.setParamB3(RandomStringUtils.randomAlphanumeric(13));
      assertNotEquals(resourceDto, resourceDtoOther);
      
      resourceDtoOther.setParamB3(resourceDto.getParamB3());
      resourceDtoOther.setParamB4(!resourceDto.getParamB4());
      assertNotEquals(resourceDto, resourceDtoOther);
      
      resourceDtoOther.setParamB4(resourceDto.getParamB4());
      resourceDtoOther.setParamB5(DtoHelper.createResourceBDtoParam5List());
      assertNotEquals(resourceDto, resourceDtoOther);
      
      final ResourceBDto resourceDtoOtherId = new ResourceBDto(resourceDto.getIdB() - 1)
            .setParamB1(resourceDto.getParamB1())
            .setParamB2(resourceDto.getParamB2())
            .setParamB3(resourceDto.getParamB3())
            .setParamB4(resourceDto.getParamB4())
            .setParamB5(resourceDto.getParamB5());
      assertNotEquals(resourceDto, resourceDtoOtherId);
   }
   
   @Test
   public void test_hashCode() {
      final ResourceBDto resourceDto = DtoHelper.createResourceBDto();
      assertEquals(resourceDto.hashCode(), Objects.hash(resourceDto.getIdB(),
            resourceDto.getParamB1(), resourceDto.getParamB2(), resourceDto.getParamB3(),
            resourceDto.getParamB4(), resourceDto.getParamB5()));
   }
   
   @Test
   public void test_toString() {
      final ResourceBDto resourceDto = DtoHelper.createResourceBDto();
      assertThat(resourceDto).hasToString("ResourceBDto{" + "idB=" + resourceDto.getIdB() +
            ", paramB1=" + resourceDto.getParamB1() + ", paramB2='" + resourceDto.getParamB2() +
            '\'' + ", paramB3='" + resourceDto.getParamB3() + '\'' + ", paramB4=" +
            resourceDto.getParamB4() + ", paramB5=" + resourceDto.getParamB5() + '}');
   }
   
   @Test
   public void test_JSON_serialization() {
      final ResourceBDto resourceDto = DtoHelper.createResourceBDto();
      final String expectedJson = getJsonRepresentation(resourceDto);
      
      DtoHelper.assertDtoToJson(resourceDto, expectedJson);
   }
   
   private String getJsonRepresentation(final ResourceBDto pResourceBDto) {
      return String.format(
            "{\"id\":%d,\"param1\":%d,\"param2\":\"%s\",\"param3\":\"%s\",\"param4\":%b," +
                  "\"param5\":[%s]}",
            pResourceBDto.getIdB(), pResourceBDto.getParamB1(),
            pResourceBDto.getParamB2(), pResourceBDto.getParamB3(), pResourceBDto.getParamB4(),
            StringUtils.join(pResourceBDto.getParamB5(), ","));
   }
   
   @Test
   public void test_JSON_deserialization() {
      final ResourceBDto resourceDtoExpected = DtoHelper.createResourceBDto();
      final String json = getJsonRepresentation(resourceDtoExpected);
      
      try {
         final ObjectMapper mapper = new ObjectMapper();
         final ResourceBDto resourceDto = mapper.readValue(json, ResourceBDto.class);
         assertEquals(resourceDtoExpected, resourceDto);
      } catch (final IOException ignored) {
         fail("Deserialization should not throw an exception.");
      }
   }
}
