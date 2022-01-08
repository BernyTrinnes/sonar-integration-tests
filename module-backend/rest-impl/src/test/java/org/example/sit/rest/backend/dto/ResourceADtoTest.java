package org.example.sit.rest.backend.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@code ResourceADto}.
 */
class ResourceADtoTest {
   @Test
   void test_JSON_serialization() {
      final ResourceADto resourceDto = DtoHelper.createResourceADto();
      final String expectedJson = getJsonRepresentation(resourceDto);
      
      DtoHelper.assertDtoToJson(resourceDto, expectedJson);
   }
   
   private String getJsonRepresentation(final ResourceADto pResourceADto) {
      return String.format("{\"id\":%d,\"param1\":\"%s\",\"param2\":%b,\"param3\":[%s]}",
            pResourceADto.getIdA(), pResourceADto.getParamA1(), pResourceADto.getParamA2(),
            StringUtils.join(pResourceADto.getParamA3(), ","));
   }
   
   @Test
   void test_JSON_deserialization() {
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
