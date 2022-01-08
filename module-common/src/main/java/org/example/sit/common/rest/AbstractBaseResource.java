package org.example.sit.common.rest;

import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * The parent of all REST resources.
 */
public abstract class AbstractBaseResource {
   /**
    * Retrieve the availability of this resource.
    *
    * @return The response containing the availability information.
    */
   @GET
   @Path("availability")
   public Response getAvailability() {
      final Map<String, Object> responseEntity = new LinkedHashMap<>();
      responseEntity.put("timestamp", Long.valueOf(System.currentTimeMillis()));
      responseEntity.put("available", Boolean.TRUE);
      
      return Response.ok().entity(responseEntity).build();
   }
   
   /**
    * Return the entity for a <code>Response</code> containing only the ID of a resource.
    *
    * @param pId The ID of the resource.
    * @return The entity for the <code>Response</code>.
    */
   protected AbstractMap.SimpleEntry<String, Long> getEntityForId(final long pId) {
      return new AbstractMap.SimpleEntry<>("id", Long.valueOf(pId));
   }
}
