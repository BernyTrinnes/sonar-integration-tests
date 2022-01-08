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
import org.example.sit.rest.backend.dto.ResourceBDto;

/**
 * The REST resource which will be tested using integration tests in {@code module-integration-tests}.
 */
@Path("resource-b")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ResourceB extends AbstractBaseResource {
   private final ResourceHandler<ResourceBDto> resourceHandler;
   
   /**
    * Initialize this resource. <br>
    * This initialization is needed by Jersey.
    */
   public ResourceB() {
      this.resourceHandler = new ResourceHandler<>();
   }
   
   /**
    * Return the data of all resources.
    *
    * @return A response containing the data of all resources, or a response with status {@code 404} if there are no
    * resources available.
    */
   @GET
   public Response getAllResourceB() {
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
   public Response deleteAllResourceB() {
      this.resourceHandler.removeAllResources();
      return Response.ok().entity(new AbstractMap.SimpleEntry<>("cleared", Boolean.TRUE)).build();
   }
   
   /**
    * Store the data of the given resource.
    *
    * @param pResourceB The data of the resource.
    * @return An HTTP status {@code 400} response if the given resource is invalid, or an HTTP status {@code 409}
    * response if the given resource is already stored, or an HTTP status {@code 201} response containing the data of
    * the resource itself if it could be stored successfully.
    */
   @POST
   public Response createResourceB(final ResourceBDto pResourceB) {
      if (null == pResourceB) {
         return Response.status(Response.Status.BAD_REQUEST).build();
      }
      if (this.resourceHandler.hasResource(pResourceB.getIdB())) {
         return Response.status(Response.Status.CONFLICT).entity(getEntityForId(pResourceB.getIdB())).build();
      }
      
      this.resourceHandler.addResource(pResourceB.getIdB(), pResourceB);
      return Response.status(Response.Status.CREATED).entity(pResourceB).build();
   }
   
   /**
    * Retrieve the data of the resource with the given ID.
    *
    * @param pIdB The ID of the resource.
    * @return A response containing the data of the requested resource, or a response with status {@code 404} if there
    * is no resource with the given ID.
    */
   @GET
   @Path("/{id}")
   public Response getResourceB(@PathParam("id") final long pIdB) {
      if (!this.resourceHandler.hasResource(pIdB)) {
         return Response.status(Response.Status.NOT_FOUND).entity(getEntityForId(pIdB)).build();
      }
      
      return Response.ok().entity(this.resourceHandler.getResource(pIdB)).build();
   }
   
   /**
    * Remove the resource with the given ID.
    *
    * @param pIdB The ID of the resource.
    * @return An HTTP status {@code 404} response if the resource with the given ID is not available, or a response
    * containing the ID of the removed resource if the removal was successful.
    */
   @DELETE
   @Path("/{id}")
   public Response deleteResourceB(@PathParam("id") final long pIdB) {
      if (!this.resourceHandler.hasResource(pIdB)) {
         return Response.status(Response.Status.NOT_FOUND).entity(getEntityForId(pIdB)).build();
      }
      
      this.resourceHandler.removeResource(pIdB);
      return Response.ok().entity(new AbstractMap.SimpleEntry<>("deleted", Long.valueOf(pIdB))).build();
   }
   
   /**
    * Update the data of the resource with the given ID.
    *
    * @param pIdB The ID of the resource.
    * @param pResourceBDtoNew The updated data of the resource.
    * @return An HTTP status {@code 400} response if the given resource is invalid, or an HTTP status {@code 404}
    * response if the resource with the given ID is not available, or an HTTP status {@code 202} response containing
    * the data of the updated resource if it could be updated successfully.
    */
   @PUT
   @Path("/{id}")
   public Response updateResourceB(@PathParam("id") final long pIdB, final ResourceBDto pResourceBDtoNew) {
      if (null == pResourceBDtoNew) {
         return Response.status(Response.Status.BAD_REQUEST).entity(getEntityForId(pIdB)).build();
      }
      if (!this.resourceHandler.hasResource(pIdB)) {
         return Response.status(Response.Status.NOT_FOUND).entity(getEntityForId(pIdB)).build();
      }
      
      final var resourceBDtoExisting = this.resourceHandler.getResource(pIdB);
      if (null != pResourceBDtoNew.getParamB1()) {
         resourceBDtoExisting.setParamB1(pResourceBDtoNew.getParamB1());
      }
      if (null != pResourceBDtoNew.getParamB2()) {
         resourceBDtoExisting.setParamB2(pResourceBDtoNew.getParamB2());
      }
      if (null != pResourceBDtoNew.getParamB3()) {
         resourceBDtoExisting.setParamB3(pResourceBDtoNew.getParamB3());
      }
      if (null != pResourceBDtoNew.getParamB4()) {
         resourceBDtoExisting.setParamB4(pResourceBDtoNew.getParamB4());
      }
      if (null != pResourceBDtoNew.getParamB5()) {
         resourceBDtoExisting.setParamB5(pResourceBDtoNew.getParamB5());
      }
      this.resourceHandler.replaceResource(pIdB, resourceBDtoExisting);
      return Response.status(Response.Status.ACCEPTED).entity(resourceBDtoExisting).build();
   }
}
