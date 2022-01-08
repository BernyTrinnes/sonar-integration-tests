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
import org.example.sit.common.rest.AbstractBaseResource;
import org.example.sit.rest.backend.dto.ResourceADto;

/**
 * The REST resource which will be tested using unit tests in {@code module-backend/rest-impl}.
 */
@Path("resource-a")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ResourceA extends AbstractBaseResource {
   private final ResourceHandler<ResourceADto> resourceHandler;
   
   /**
    * Initialize this resource. <br>
    * This initialization is needed by the test mocks.
    *
    * @param pResourceHandler The {@link ResourceHandler} for this resource.
    */
   @SuppressWarnings("unused")
   public ResourceA(final ResourceHandler<ResourceADto> pResourceHandler) {
      this.resourceHandler = pResourceHandler;
   }
   
   /**
    * Return the data of all resources.
    *
    * @return A response containing the data of all resources, or a response with status {@code 404} if there are no
    * resources available.
    */
   @GET
   public Response getAllResourceA() {
      if (!this.resourceHandler.hasResources()) {
         return Response.status(Response.Status.NOT_FOUND).build();
      }
      return Response.ok().entity(this.resourceHandler.getAllResources()).build();
   }
   
   /**
    * Remove the data of all resources.
    *
    * @return A response containing the result of the deletion, the result will always be successful.
    */
   @DELETE
   public Response deleteAllResourceA() {
      this.resourceHandler.removeAllResources();
      return Response.ok().entity(new AbstractMap.SimpleEntry<>("cleared", Boolean.TRUE)).build();
   }
   
   /**
    * Store the data of the given resource.
    *
    * @param pResourceA The data of the resource.
    * @return An HTTP status {@code 400} response if the given resource is invalid, or an HTTP status {@code 409}
    * response if the given resource is already stored, or an HTTP status {@code 201} response containing the data of
    * the resource itself if it could be stored successfully.
    */
   @POST
   public Response createResourceA(final ResourceADto pResourceA) {
      if (null == pResourceA) {
         return Response.status(Response.Status.BAD_REQUEST).build();
      }
      if (this.resourceHandler.hasResource(pResourceA.getIdA())) {
         return Response.status(Response.Status.CONFLICT).entity(getEntityForId(pResourceA.getIdA())).build();
      }
      
      this.resourceHandler.addResource(pResourceA.getIdA(), pResourceA);
      return Response.status(Response.Status.CREATED).entity(pResourceA).build();
   }
   
   /**
    * Retrieve the data of the resource with the given ID.
    *
    * @param pIdA The ID of the resource.
    * @return A response containing the data of the requested resource, or a response with status {@code 404} if there
    * is no resource with the given ID.
    */
   @GET
   @Path("/{id}")
   public Response getResourceA(@PathParam("id") final long pIdA) {
      if (!this.resourceHandler.hasResource(pIdA)) {
         return Response.status(Response.Status.NOT_FOUND).entity(getEntityForId(pIdA)).build();
      }
      
      return Response.ok().entity(this.resourceHandler.getResource(pIdA)).build();
   }
   
   /**
    * Remove the resource with the given ID.
    *
    * @param pIdA The ID of the resource.
    * @return An HTTP status {@code 404} response if the resource with the given ID is not available, or a response
    * containing the ID of the removed resource if the removal was successful.
    */
   @DELETE
   @Path("/{id}")
   public Response deleteResourceA(@PathParam("id") final long pIdA) {
      if (!this.resourceHandler.hasResource(pIdA)) {
         return Response.status(Response.Status.NOT_FOUND).entity(getEntityForId(pIdA)).build();
      }
      
      this.resourceHandler.removeResource(pIdA);
      return Response.ok().entity(new AbstractMap.SimpleEntry<>("deleted", Long.valueOf(pIdA))).build();
   }
   
   /**
    * Update the data of the resource with the given ID.
    *
    * @param pIdA The ID of the resource.
    * @param pResourceADtoNew The updated data of the resource.
    * @return An HTTP status {@code 400} response if the given resource is invalid, or an HTTP status {@code 404}
    * response if the resource with the given ID is not available, or an HTTP status {@code 202} response containing
    * the data of the updated resource if it could be updated successfully.
    */
   @PUT
   @Path("/{id}")
   public Response updateResourceA(@PathParam("id") final long pIdA, final ResourceADto pResourceADtoNew) {
      if (null == pResourceADtoNew) {
         return Response.status(Response.Status.BAD_REQUEST).entity(getEntityForId(pIdA)).build();
      }
      if (!this.resourceHandler.hasResource(pIdA)) {
         return Response.status(Response.Status.NOT_FOUND).entity(getEntityForId(pIdA)).build();
      }
      
      final var resourceADtoExisting = this.resourceHandler.getResource(pIdA);
      if (null != pResourceADtoNew.getParamA1()) {
         resourceADtoExisting.setParamA1(pResourceADtoNew.getParamA1());
      }
      if (null != pResourceADtoNew.getParamA2()) {
         resourceADtoExisting.setParamA2(pResourceADtoNew.getParamA2());
      }
      if (null != pResourceADtoNew.getParamA3()) {
         resourceADtoExisting.setParamA3(pResourceADtoNew.getParamA3());
      }
      this.resourceHandler.replaceResource(pIdA, resourceADtoExisting);
      return Response.status(Response.Status.ACCEPTED).entity(resourceADtoExisting).build();
   }
}
