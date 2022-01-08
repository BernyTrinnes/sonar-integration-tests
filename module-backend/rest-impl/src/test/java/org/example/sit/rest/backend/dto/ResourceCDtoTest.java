package org.example.sit.rest.backend.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@code ResourceCDto}.
 */
class ResourceCDtoTest {
   @Test
   void test_JSON_serialization() {
      final ResourceCDto resourceDto = DtoHelper.createResourceCDto();
      final String expectedJson = getJsonRepresentation(resourceDto);
      
      DtoHelper.assertDtoToJson(resourceDto, expectedJson);
   }
   
   private String getJsonRepresentation(final ResourceCDto pResourceCDto) {
      return String.format("{\"id\":%d,\"param1\":\"%s\",\"param2\":%d,\"param3\":\"%s\",\"param4\":\"%s\"," +
                  "\"param5\":%b,\"param6\":%d,\"param7\":[\"%s\"]}", pResourceCDto.getIdC(),
            pResourceCDto.getParamC1(), pResourceCDto.getParamC2(), pResourceCDto.getParamC3(),
            pResourceCDto.getParamC4(), pResourceCDto.getParamC5(), pResourceCDto.getParamC6(),
            StringUtils.join(pResourceCDto.getParamC7(), "\",\""));
   }
   
   @Test
   void test_JSON_deserialization() {
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
