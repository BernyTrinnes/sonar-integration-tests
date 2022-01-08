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
import org.example.sit.common.rest.AbstractBaseResource;
import org.example.sit.rest.frontend.dto.ResourceEDto;

/**
 * The REST resource which will be tested using integration tests in {@code module-integration-tests}.
 */
@Path("resource-e")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ResourceE extends AbstractBaseResource {
   private final ResourceHandler<ResourceEDto> resourceHandler;
   
   /**
    * Initialize this resource. <br>
    * This initialization is needed by Jersey.
    */
   public ResourceE() {
      this.resourceHandler = new ResourceHandler<>();
   }
   
   /**
    * Return the data of all resources.
    *
    * @return A response containing the data of all resources, or a response with status {@code 404} if there are no
    * resources available.
    */
   @GET
   public Response getAllResourceE() {
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
   public Response deleteAllResourceE() {
      this.resourceHandler.removeAllResources();
      return Response.ok().entity(new AbstractMap.SimpleEntry<>("cleared", Boolean.TRUE)).build();
   }
   
   /**
    * Store the data of the given resource.
    *
    * @param pResourceE The data of the resource.
    * @return An HTTP status {@code 400} response if the given resource is invalid, or an HTTP status {@code 409}
    * response if the given resource is already stored, or an HTTP status {@code 201} response containing the data of
    * the resource itself if it could be stored successfully.
    */
   @POST
   public Response createResourceE(final ResourceEDto pResourceE) {
      if (null == pResourceE) {
         return Response.status(Response.Status.BAD_REQUEST).build();
      }
      if (this.resourceHandler.hasResource(pResourceE.getIdE())) {
         return Response.status(Response.Status.CONFLICT).entity(getEntityForId(pResourceE.getIdE())).build();
      }
      
      this.resourceHandler.addResource(pResourceE.getIdE(), pResourceE);
      return Response.status(Response.Status.CREATED).entity(pResourceE).build();
   }
   
   /**
    * Retrieve the data of the resource with the given ID.
    *
    * @param pIdE The ID of the resource.
    * @return A response containing the data of the requested resource, or a response with status {@code 404} if there
    * is no resource with the given ID.
    */
   @GET
   @Path("/{id}")
   public Response getResourceE(@PathParam("id") final long pIdE) {
      if (!this.resourceHandler.hasResource(pIdE)) {
         return Response.status(Response.Status.NOT_FOUND).entity(getEntityForId(pIdE)).build();
      }
      
      return Response.ok().entity(this.resourceHandler.getResource(pIdE)).build();
   }
   
   /**
    * Remove the resource with the given ID.
    *
    * @param pIdE The ID of the resource.
    * @return An HTTP status {@code 404} response if the resource with the given ID is not available, or a response
    * containing the ID of the removed resource if the removal was successful.
    */
   @DELETE
   @Path("/{id}")
   public Response deleteResourceE(@PathParam("id") final long pIdE) {
      if (!this.resourceHandler.hasResource(pIdE)) {
         return Response.status(Response.Status.NOT_FOUND).entity(getEntityForId(pIdE)).build();
      }
      
      this.resourceHandler.removeResource(pIdE);
      return Response.ok().entity(new AbstractMap.SimpleEntry<>("deleted", Long.valueOf(pIdE))).build();
   }
   
   /**
    * Update the data of the resource with the given ID.
    *
    * @param pIdE The ID of the resource.
    * @param pResourceEDtoNew The updated data of the resource.
    * @return An HTTP status {@code 400} response if the given resource is invalid, or an HTTP status {@code 404}
    * response if the resource with the given ID is not available, or an HTTP status {@code 202} response containing
    * the data of the updated resource if it could be updated successfully.
    */
   @PUT
   @Path("/{id}")
   public Response updateResourceE(@PathParam("id") final long pIdE, final ResourceEDto pResourceEDtoNew) {
      if (null == pResourceEDtoNew) {
         return Response.status(Response.Status.BAD_REQUEST).entity(getEntityForId(pIdE)).build();
      }
      if (!this.resourceHandler.hasResource(pIdE)) {
         return Response.status(Response.Status.NOT_FOUND).entity(getEntityForId(pIdE)).build();
      }
      
      final var resourceEDtoExisting = this.resourceHandler.getResource(pIdE);
      if (null != pResourceEDtoNew.getParamE1()) {
         resourceEDtoExisting.setParamE1(pResourceEDtoNew.getParamE1());
      }
      if (null != pResourceEDtoNew.getParamE2()) {
         resourceEDtoExisting.setParamE2(pResourceEDtoNew.getParamE2());
      }
      if (null != pResourceEDtoNew.getParamE3()) {
         resourceEDtoExisting.setParamE3(pResourceEDtoNew.getParamE3());
      }
      if (null != pResourceEDtoNew.getParamE4()) {
         resourceEDtoExisting.setParamE4(pResourceEDtoNew.getParamE4());
      }
      if (null != pResourceEDtoNew.getParamE5()) {
         resourceEDtoExisting.setParamE5(pResourceEDtoNew.getParamE5());
      }
      if (null != pResourceEDtoNew.getParamE6()) {
         resourceEDtoExisting.setParamE6(pResourceEDtoNew.getParamE6());
      }
      this.resourceHandler.replaceResource(pIdE, resourceEDtoExisting);
      return Response.status(Response.Status.ACCEPTED).entity(resourceEDtoExisting).build();
   }
}
