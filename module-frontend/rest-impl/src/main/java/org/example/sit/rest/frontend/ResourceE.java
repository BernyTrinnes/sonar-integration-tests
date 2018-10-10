package org.example.sit.rest.frontend;

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
import org.example.sit.rest.frontend.dto.ResourceEDto;

/**
 * The REST resource which will be tested using integration tests in
 * {@code module-integration-test}.
 */
@Path("resource-e")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ResourceE {
   private ResourceHandler<ResourceEDto> resources;
   
   /**
    * Initializes this resource.
    */
   public ResourceE() {
      this.resources = new ResourceHandler<>();
   }
   
   /**
    * Return the data of all resources.
    *
    * @return A response containing the data of all resources, or a response with status {@code 404}
    * if there are no resources available.
    */
   @GET
   public Response getAllResourceE() {
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
   public Response deleteAllResourceE() {
      this.resources.removeAllResources();
      return Response.ok().entity(new AbstractMap.SimpleEntry<>("cleared", Boolean.TRUE)).build();
   }
   
   /**
    * Store the data of the given resource.
    *
    * @param pResourceE The data of the resource.
    * @return A response with the status of the process.
    */
   @POST
   public Response createResourceE(final ResourceEDto pResourceE) {
      if (null == pResourceE) {
         return Response.status(Response.Status.BAD_REQUEST).build();
      }
      if (this.resources.hasResource(pResourceE.getIdE())) {
         return Response.status(Response.Status.CONFLICT)
               .entity(getEntityForId(pResourceE.getIdE())).build();
      }
      
      this.resources.addResource(pResourceE.getIdE(), pResourceE);
      return Response.status(Response.Status.CREATED).entity(pResourceE).build();
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
   public Response getResourceE(@PathParam("id") final long pId) {
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
   public Response deleteResourceE(@PathParam("id") final long pId) {
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
    * @param pResourceEDto The data of the resource.
    * @return A response containing the result of the process.
    */
   @PUT
   @Path("/{id}")
   public Response updateResourceE(@PathParam("id") final long pId,
         final ResourceEDto pResourceEDto) {
      if (!this.resources.hasResource(pId)) {
         return Response.status(Response.Status.NOT_FOUND).entity(getEntityForId(pId)).build();
      }
      if (null == pResourceEDto) {
         return Response.status(Response.Status.BAD_REQUEST).entity(getEntityForId(pId)).build();
      }
      
      final ResourceEDto resourceEDto = this.resources.getResource(pId);
      if (null != pResourceEDto.getParamE1()) {
         resourceEDto.setParamE1(pResourceEDto.getParamE1());
      }
      if (null != pResourceEDto.getParamE2()) {
         resourceEDto.setParamE2(pResourceEDto.getParamE2());
      }
      if (null != pResourceEDto.getParamE3()) {
         resourceEDto.setParamE3(pResourceEDto.getParamE3());
      }
      if (null != pResourceEDto.getParamE4()) {
         resourceEDto.setParamE4(pResourceEDto.getParamE4());
      }
      if (null != pResourceEDto.getParamE5()) {
         resourceEDto.setParamE5(pResourceEDto.getParamE5());
      }
      if (null != pResourceEDto.getParamE6()) {
         resourceEDto.setParamE6(pResourceEDto.getParamE6());
      }
      this.resources.replaceResource(pId, resourceEDto);
      return Response.status(Response.Status.ACCEPTED).entity(resourceEDto).build();
   }
}
