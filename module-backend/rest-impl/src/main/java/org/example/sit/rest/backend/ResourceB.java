package org.example.sit.rest.backend;

import java.util.AbstractMap;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.example.sit.common.resources.ResourceHandler;
import org.example.sit.rest.backend.dto.ResourceBDto;

/**
 * The REST resource which will be tested using integration tests in
 * {@code module-integration-test}.
 */
@Path("resource-b")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ResourceB implements BaseResource {
   private ResourceHandler<ResourceBDto> resources;
   
   /**
    * Initializes this resource.
    */
   public ResourceB() {
      this.resources = new ResourceHandler<>();
   }
   
   /**
    * Return the data of all resources.
    *
    * @return A response containing the data of all resources, or a response with status {@code 404}
    * if there are no resources available.
    */
   @GET
   public Response getAllResourceB() {
      if (!this.resources.hasResources()) {
         return Response.status(Response.Status.NOT_FOUND).build();
      }
      return Response.ok().entity(this.resources.getAllResources()).build();
   }
   
   /**
    * Delete the data of all resources.
    *
    * @return A response containing the result of the deletion.
    */
   @DELETE
   public Response deleteAllResourceB() {
      this.resources.removeAllResources();
      return Response.ok().entity(new AbstractMap.SimpleEntry<>("cleared", Boolean.TRUE)).build();
   }
   
   /**
    * Store the data of the given resource.
    *
    * @param pResourceB The data of the resource.
    * @return A response with the status of the process.
    */
   @POST
   public Response createResourceB(final ResourceBDto pResourceB) {
      if (null == pResourceB) {
         return Response.status(Response.Status.BAD_REQUEST).build();
      }
      if (this.resources.hasResource(pResourceB.getIdB())) {
         return Response.status(Response.Status.CONFLICT)
               .entity(getEntityForId(pResourceB.getIdB())).build();
      }
      
      this.resources.addResource(pResourceB.getIdB(), pResourceB);
      return Response.status(Response.Status.CREATED).entity(pResourceB).build();
   }
   
   private AbstractMap.SimpleEntry<String, Long> getEntityForId(final long pId) {
      return new AbstractMap.SimpleEntry<>("id", Long.valueOf(pId));
   }
   
   /**
    * Retrieve the data of the resource with the given ID.
    *
    * @param pId The ID of the resource.
    * @return A response containing the data of the requested resources, or a response with status
    * {@code 404} if there is no resource with the given ID.
    */
   @GET
   @Path("/{id}")
   public Response getResourceB(@PathParam("id") final long pId) {
      if (!this.resources.hasResource(pId)) {
         return Response.status(Response.Status.NOT_FOUND).entity(getEntityForId(pId)).build();
      }
      
      return Response.ok().entity(this.resources.getResource(pId)).build();
   }
   
   /**
    * Remove the resource with the given ID.
    *
    * @param pId The ID of the resource.
    * @return A response containing the result of the process.
    */
   @DELETE
   @Path("/{id}")
   public Response deleteResourceB(@PathParam("id") final long pId) {
      if (!this.resources.hasResource(pId)) {
         return Response.status(Response.Status.NOT_FOUND).entity(getEntityForId(pId)).build();
      }
      
      this.resources.removeResource(pId);
      return Response.ok()
            .entity(new AbstractMap.SimpleEntry<>("deleted", Long.valueOf(pId))).build();
   }
   
   /**
    * Update the data of the resource with the given ID.
    *
    * @param pId The ID of the resource.
    * @param pResourceBDto The data of the resource.
    * @return A response containing the result of the process.
    */
   @PUT
   @Path("/{id}")
   public Response updateResourceB(@PathParam("id") final long pId,
         final ResourceBDto pResourceBDto) {
      if (!this.resources.hasResource(pId)) {
         return Response.status(Response.Status.NOT_FOUND).entity(getEntityForId(pId)).build();
      }
      if (null == pResourceBDto) {
         return Response.status(Response.Status.BAD_REQUEST).entity(getEntityForId(pId)).build();
      }
      
      final ResourceBDto resourceBDto = this.resources.getResource(pId);
      if (null != pResourceBDto.getParamB1()) {
         resourceBDto.setParamB1(pResourceBDto.getParamB1());
      }
      if (null != pResourceBDto.getParamB2()) {
         resourceBDto.setParamB2(pResourceBDto.getParamB2());
      }
      if (null != pResourceBDto.getParamB3()) {
         resourceBDto.setParamB3(pResourceBDto.getParamB3());
      }
      if (null != pResourceBDto.getParamB4()) {
         resourceBDto.setParamB4(pResourceBDto.getParamB4());
      }
      if (null != pResourceBDto.getParamB5()) {
         resourceBDto.setParamB5(pResourceBDto.getParamB5());
      }
      this.resources.replaceResource(pId, resourceBDto);
      return Response.status(Response.Status.ACCEPTED).entity(resourceBDto).build();
   }
}
