package org.example.sit.rest.backend;

import java.util.AbstractMap;
import javax.ws.rs.BeanParam;
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
import org.example.sit.rest.backend.dto.ResourceADto;

/**
 * The REST resource which will be tested using unit tests in {@code module-backend/rest-impl}.
 */
@Path("resource-a")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ResourceA implements BaseResource {
   private ResourceHandler<ResourceADto> resources;
   
   /**
    * Initializes this resource.
    */
   public ResourceA() {
      this.resources = new ResourceHandler<>();
   }
   
   /**
    * Return the data of all resources.
    *
    * @return A response containing the data of all resources, or a response with status {@code 404}
    * if there are no resources available.
    */
   @GET
   public Response getAllResourceA() {
      if (!this.resources.hasResources()) {
         return Response.status(Response.Status.NOT_FOUND).build();
      }
      return Response.ok().entity(this.resources.getAllResources()).build();
   }
   
   /**
    * Store the data of the given resource.
    *
    * @param pResourceA The data of the resource.
    * @return A response with the status of the process.
    */
   @POST
   public Response createResourceA(@BeanParam final ResourceADto pResourceA) {
      if (null == pResourceA) {
         return Response.status(Response.Status.BAD_REQUEST).build();
      }
      if (this.resources.hasResource(pResourceA.getIdA())) {
         return Response.status(Response.Status.CONFLICT)
               .entity(getEntityForId(pResourceA.getIdA())).build();
      }
      
      this.resources.addResource(pResourceA.getIdA(), pResourceA);
      return Response.status(Response.Status.CREATED).entity(pResourceA).build();
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
   public Response getResourceA(@PathParam("id") final long pId) {
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
   public Response deleteResourceA(@PathParam("id") final long pId) {
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
    * @param pResourceADto The data of the resource.
    * @return A response containing the result of the process.
    */
   @PUT
   @Path("/{id}")
   public Response updateResourceA(@PathParam("id") final long pId,
         @BeanParam final ResourceADto pResourceADto) {
      if (!this.resources.hasResource(pId)) {
         return Response.status(Response.Status.NOT_FOUND).entity(getEntityForId(pId)).build();
      }
      if (null == pResourceADto) {
         return Response.status(Response.Status.BAD_REQUEST).entity(getEntityForId(pId)).build();
      }
      
      final ResourceADto resourceADto = this.resources.getResource(pId);
      if (null != pResourceADto.getParamA1()) {
         resourceADto.setParamA1(pResourceADto.getParamA1());
      }
      if (null != pResourceADto.getParamA2()) {
         resourceADto.setParamA2(pResourceADto.getParamA2());
      }
      if (null != pResourceADto.getParamA3()) {
         resourceADto.setParamA3(pResourceADto.getParamA3());
      }
      this.resources.replaceResource(pId, resourceADto);
      return Response.status(Response.Status.ACCEPTED).entity(resourceADto).build();
   }
}
