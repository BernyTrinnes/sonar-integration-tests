package org.example.sit.rest.frontend.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@code ResourceEDto}.
 */
class ResourceEDtoTest {
   @Test
   void test_JSON_serialization() {
      final ResourceEDto resourceDto = DtoHelper.createResourceEDto();
      final String expectedJson = getJsonRepresentation(resourceDto);
      
      DtoHelper.assertDtoToJson(resourceDto, expectedJson);
   }
   
   private String getJsonRepresentation(final ResourceEDto pResourceEDto) {
      return String.format("{\"id\":%d,\"param1\":%d,\"param2\":\"%s\",\"param3\":\"%s\",\"param4\":%d," +
                  "\"param5\":%b,\"param6\":[%s]}", pResourceEDto.getIdE(), pResourceEDto.getParamE1(),
            pResourceEDto.getParamE2(), pResourceEDto.getParamE3(), pResourceEDto.getParamE4(),
            pResourceEDto.getParamE5(), StringUtils.join(pResourceEDto.getParamE6(), ","));
   }
   
   @Test
   void test_JSON_deserialization() {
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
