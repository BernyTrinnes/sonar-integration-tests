package org.example.sit.rest.frontend.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@code ResourceDDto}.
 */
class ResourceDDtoTest {
   @Test
   void test_JSON_serialization() {
      final ResourceDDto resourceDto = DtoHelper.createResourceDDto();
      final String expectedJson = getJsonRepresentation(resourceDto);
      
      DtoHelper.assertDtoToJson(resourceDto, expectedJson);
   }
   
   private String getJsonRepresentation(final ResourceDDto pResourceDDto) {
      return String.format("{\"id\":%d,\"param1\":\"%s\",\"param2\":%d,\"param3\":%b,\"param4\":[%s]}",
            pResourceDDto.getIdD(), pResourceDDto.getParamD1(), pResourceDDto.getParamD2(), pResourceDDto.getParamD3(),
            StringUtils.join(pResourceDDto.getParamD4(), ","));
   }
   
   @Test
   void test_JSON_deserialization() {
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
