package org.example.sit.rest.backend;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * The parent of all REST resources.
 */
public interface BaseResource {
   /**
    * Retrieve the availability of this resource.
    *
    * @return The response containing the availability information.
    */
   @GET
   @Path("availability")
   default Response getAvailability() {
      final Map<String, Object> responseEntity = new LinkedHashMap<>();
      responseEntity.put("timestamp", Long.valueOf(System.currentTimeMillis()));
      responseEntity.put("available", Boolean.TRUE);
      
      return Response.ok().entity(responseEntity).build();
   }
}
