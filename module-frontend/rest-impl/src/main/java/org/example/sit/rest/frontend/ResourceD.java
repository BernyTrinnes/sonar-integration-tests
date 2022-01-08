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
import org.example.sit.rest.frontend.dto.ResourceDDto;

/**
 * The REST resource which will be tested using unit tests in {@code module-frontend/rest-impl}.
 */
@Path("resource-d")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ResourceD extends AbstractBaseResource {
   private final ResourceHandler<ResourceDDto> resourceHandler;
   
   /**
    * Initialize this resource. <br>
    * This initialization is needed by the test mocks.
    *
    * @param pResourceHandler The {@link ResourceHandler} for this resource.
    */
   @SuppressWarnings("unused")
   public ResourceD(final ResourceHandler<ResourceDDto> pResourceHandler) {
      this.resourceHandler = pResourceHandler;
   }
   
   /**
    * Return the data of all resources.
    *
    * @return A response containing the data of all resources, or a response with status {@code 404} if there are no
    * resources available.
    */
   @GET
   public Response getAllResourceD() {
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
   public Response deleteAllResourceD() {
      this.resourceHandler.removeAllResources();
      return Response.ok().entity(new AbstractMap.SimpleEntry<>("cleared", Boolean.TRUE)).build();
   }
   
   /**
    * Store the data of the given resource.
    *
    * @param pResourceD The data of the resource.
    * @return An HTTP status {@code 400} response if the given resource is invalid, or an HTTP status {@code 409}
    * response if the given resource is already stored, or an HTTP status {@code 201} response containing the data of
    * the resource itself if it could be stored successfully.
    */
   @POST
   public Response createResourceD(final ResourceDDto pResourceD) {
      if (null == pResourceD) {
         return Response.status(Response.Status.BAD_REQUEST).build();
      }
      if (this.resourceHandler.hasResource(pResourceD.getIdD())) {
         return Response.status(Response.Status.CONFLICT).entity(getEntityForId(pResourceD.getIdD())).build();
      }
      
      this.resourceHandler.addResource(pResourceD.getIdD(), pResourceD);
      return Response.status(Response.Status.CREATED).entity(pResourceD).build();
   }
   
   /**
    * Retrieve the data of the resource with the given ID.
    *
    * @param pIdD The ID of the resource.
    * @return A response containing the data of the requested resource, or a response with status {@code 404} if there
    * is no resource with the given ID.
    */
   @GET
   @Path("/{id}")
   public Response getResourceD(@PathParam("id") final long pIdD) {
      if (!this.resourceHandler.hasResource(pIdD)) {
         return Response.status(Response.Status.NOT_FOUND).entity(getEntityForId(pIdD)).build();
      }
      
      return Response.ok().entity(this.resourceHandler.getResource(pIdD)).build();
   }
   
   /**
    * Remove the resource with the given ID.
    *
    * @param pIdD The ID of the resource.
    * @return An HTTP status {@code 404} response if the resource with the given ID is not available, or a response
    * containing the ID of the removed resource if the removal was successful.
    */
   @DELETE
   @Path("/{id}")
   public Response deleteResourceD(@PathParam("id") final long pIdD) {
      if (!this.resourceHandler.hasResource(pIdD)) {
         return Response.status(Response.Status.NOT_FOUND).entity(getEntityForId(pIdD)).build();
      }
      
      this.resourceHandler.removeResource(pIdD);
      return Response.ok().entity(new AbstractMap.SimpleEntry<>("deleted", Long.valueOf(pIdD))).build();
   }
   
   /**
    * Update the data of the resource with the given ID.
    *
    * @param pIdD The ID of the resource.
    * @param pResourceDDtoNew The updated data of the resource.
    * @return An HTTP status {@code 400} response if the given resource is invalid, or an HTTP status {@code 404}
    * response if the resource with the given ID is not available, or an HTTP status {@code 202} response containing
    * the data of the updated resource if it could be updated successfully.
    */
   @PUT
   @Path("/{id}")
   public Response updateResourceD(@PathParam("id") final long pIdD, final ResourceDDto pResourceDDtoNew) {
      if (null == pResourceDDtoNew) {
         return Response.status(Response.Status.BAD_REQUEST).entity(getEntityForId(pIdD)).build();
      }
      if (!this.resourceHandler.hasResource(pIdD)) {
         return Response.status(Response.Status.NOT_FOUND).entity(getEntityForId(pIdD)).build();
      }
      
      final var resourceDDtoExisting = this.resourceHandler.getResource(pIdD);
      if (null != pResourceDDtoNew.getParamD1()) {
         resourceDDtoExisting.setParamD1(pResourceDDtoNew.getParamD1());
      }
      if (null != pResourceDDtoNew.getParamD2()) {
         resourceDDtoExisting.setParamD2(pResourceDDtoNew.getParamD2());
      }
      if (null != pResourceDDtoNew.getParamD3()) {
         resourceDDtoExisting.setParamD3(pResourceDDtoNew.getParamD3());
      }
      if (null != pResourceDDtoNew.getParamD4()) {
         resourceDDtoExisting.setParamD4(pResourceDDtoNew.getParamD4());
      }
      this.resourceHandler.replaceResource(pIdD, resourceDDtoExisting);
      return Response.status(Response.Status.ACCEPTED).entity(resourceDDtoExisting).build();
   }
}
