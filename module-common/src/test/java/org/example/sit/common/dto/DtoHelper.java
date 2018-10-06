package org.example.sit.common.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Helper methods.
 */
@SuppressWarnings({"javadoc", "boxing"})
public abstract class DtoHelper {
   /**
    * Assert that the JSON transformed DTO is equal to the given JSON representation.
    *
    * @param pDto The DTO to transform to JSON.
    * @param pExpectedJson The expected JSON representation.
    */
   public static void assertDtoToJson(final Object pDto, final String pExpectedJson) {
      try {
         final ObjectMapper mapper = new ObjectMapper();
         final String json = mapper.writeValueAsString(pDto);
         assertEquals(pExpectedJson, json);
      } catch (final JsonProcessingException ignored) {
         fail("JSON serialization should not throw an exception.");
      }
   }
}
