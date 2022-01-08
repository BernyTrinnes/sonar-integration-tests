package org.example.sit.rest.frontend.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@code ResourceFDto}.
 */
class ResourceFDtoTest {
   @Test
   void test_JSON_serialization() {
      final ResourceFDto resourceDto = DtoHelper.createResourceFDto();
      final String expectedJson = getJsonRepresentation(resourceDto);
      
      DtoHelper.assertDtoToJson(resourceDto, expectedJson);
   }
   
   private String getJsonRepresentation(final ResourceFDto pResourceFDto) {
      return String.format("{\"id\":%d,\"param1\":\"%s\",\"param2\":%d,\"param3\":\"%s\",\"param4\":\"%s\"," +
                  "\"param5\":%b,\"param6\":%d,\"param7\":\"%s\",\"param8\":[\"%s\"]}", pResourceFDto.getIdF(),
            pResourceFDto.getParamF1(), pResourceFDto.getParamF2(), pResourceFDto.getParamF3(),
            pResourceFDto.getParamF4(), pResourceFDto.getParamF5(), pResourceFDto.getParamF6(),
            pResourceFDto.getParamF7(), StringUtils.join(pResourceFDto.getParamF8(), "\",\""));
   }
   
   @Test
   void test_JSON_deserialization() {
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
