package org.example.sit.rest.backend.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@code ResourceBDto}.
 */
class ResourceBDtoTest {
   @Test
   void test_JSON_serialization() {
      final ResourceBDto resourceDto = DtoHelper.createResourceBDto();
      final String expectedJson = getJsonRepresentation(resourceDto);
      
      DtoHelper.assertDtoToJson(resourceDto, expectedJson);
   }
   
   private String getJsonRepresentation(final ResourceBDto pResourceBDto) {
      return String.format("{\"id\":%d,\"param1\":%d,\"param2\":\"%s\",\"param3\":\"%s\",\"param4\":%b," +
                  "\"param5\":[%s]}", pResourceBDto.getIdB(), pResourceBDto.getParamB1(), pResourceBDto.getParamB2(),
            pResourceBDto.getParamB3(), pResourceBDto.getParamB4(), StringUtils.join(pResourceBDto.getParamB5(), ","));
   }
   
   @Test
   void test_JSON_deserialization() {
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
