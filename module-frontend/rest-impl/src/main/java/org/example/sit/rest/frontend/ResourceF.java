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
import org.example.sit.rest.frontend.dto.ResourceFDto;

/**
 * The REST resource which will be tested using unit tests in {@code module-frontend/rest-impl} and
 * integration tests in {@code module-integration-test}.
 *
 * <ul>
 *    <li>
 *       Unit tests in module {@code module-backend/rest-impl}:
 *       <ul>
 *          <li>{@code getAvailability()}</li>
 *          <li>{@code createResourceF(ResourceFDto)}</li>
 *          <li>{@code deleteResourceF(long)}</li>
 *          <li>{@code updateResourceF(long, ResourceFDto)}</li>
 *       </ul>
 *    </li>
 *    <li>
 *       Integration tests in module {@code module-integration-tests}:
 *       <ul>
 *          <li>{@code getAvailability()}</li>
 *          <li>{@code deleteAllResourceF()}</li>
 *          <li>{@code getAllResourceF()}</li>
 *          <li>{@code getResourceF(long)}</li>
 *       </ul>
 *    </li>
 * </ul>
 */
@Path("resource-f")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ResourceF extends AbstractBaseResource {
   private final ResourceHandler<ResourceFDto> resourceHandler;
   
   /**
    * Initialize this resource. <br>
    * This initialization is needed by Jersey.
    */
   @SuppressWarnings("unused")
   public ResourceF() {
      this.resourceHandler = new ResourceHandler<>();
   }
   
   /**
    * Initialize this resource. <br>
    * This initialization is needed by the test mocks.
    *
    * @param pResourceHandler The {@link ResourceHandler} for this resource.
    */
   @SuppressWarnings("unused")
   public ResourceF(final ResourceHandler<ResourceFDto> pResourceHandler) {
      this.resourceHandler = pResourceHandler;
   }
   
   /**
    * Return the data of all resources.
    *
    * @return A response containing the data of all resources, or a response with status {@code 404} if there are no
    * resources available.
    */
   @GET
   public Response getAllResourceF() {
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
   public Response deleteAllResourceF() {
      this.resourceHandler.removeAllResources();
      return Response.ok().entity(new AbstractMap.SimpleEntry<>("cleared", Boolean.TRUE)).build();
   }
   
   /**
    * Store the data of the given resource.
    *
    * @param pResourceF The data of the resource.
    * @return An HTTP status {@code 400} response if the given resource is invalid, or an HTTP status {@code 409}
    * response if the given resource is already stored, or an HTTP status {@code 201} response containing the data of
    * the resource itself if it could be stored successfully.
    */
   @POST
   public Response createResourceF(final ResourceFDto pResourceF) {
      if (null == pResourceF) {
         return Response.status(Response.Status.BAD_REQUEST).build();
      }
      if (this.resourceHandler.hasResource(pResourceF.getIdF())) {
         return Response.status(Response.Status.CONFLICT).entity(getEntityForId(pResourceF.getIdF())).build();
      }
      
      this.resourceHandler.addResource(pResourceF.getIdF(), pResourceF);
      return Response.status(Response.Status.CREATED).entity(pResourceF).build();
   }
   
   /**
    * Retrieve the data of the resource with the given ID.
    *
    * @param pIdF The ID of the resource.
    * @return A response containing the data of the requested resource, or a response with status {@code 404} if there
    * is no resource with the given ID.
    */
   @GET
   @Path("/{id}")
   public Response getResourceF(@PathParam("id") final long pIdF) {
      if (!this.resourceHandler.hasResource(pIdF)) {
         return Response.status(Response.Status.NOT_FOUND).entity(getEntityForId(pIdF)).build();
      }
      
      return Response.ok().entity(this.resourceHandler.getResource(pIdF)).build();
   }
   
   /**
    * Remove the resource with the given ID.
    *
    * @param pIdF The ID of the resource.
    * @return An HTTP status {@code 404} response if the resource with the given ID is not available, or a response
    * containing the ID of the removed resource if the removal was successful.
    */
   @DELETE
   @Path("/{id}")
   public Response deleteResourceF(@PathParam("id") final long pIdF) {
      if (!this.resourceHandler.hasResource(pIdF)) {
         return Response.status(Response.Status.NOT_FOUND).entity(getEntityForId(pIdF)).build();
      }
      
      this.resourceHandler.removeResource(pIdF);
      return Response.ok().entity(new AbstractMap.SimpleEntry<>("deleted", Long.valueOf(pIdF))).build();
   }
   
   /**
    * Update the data of the resource with the given ID.
    *
    * @param pId The ID of the resource.
    * @param pResourceFDtoNew The updated data of the resource.
    * @return An HTTP status {@code 400} response if the given resource is invalid, or an HTTP status {@code 404}
    * response if the resource with the given ID is not available, or an HTTP status {@code 202} response containing
    * the data of the updated resource if it could be updated successfully.
    */
   @PUT
   @Path("/{id}")
   public Response updateResourceF(@PathParam("id") final long pId, final ResourceFDto pResourceFDtoNew) {
      if (null == pResourceFDtoNew) {
         return Response.status(Response.Status.BAD_REQUEST).entity(getEntityForId(pId)).build();
      }
      if (!this.resourceHandler.hasResource(pId)) {
         return Response.status(Response.Status.NOT_FOUND).entity(getEntityForId(pId)).build();
      }
      
      final var resourceFDtoExisting = this.resourceHandler.getResource(pId);
      if (null != pResourceFDtoNew.getParamF1()) {
         resourceFDtoExisting.setParamF1(pResourceFDtoNew.getParamF1());
      }
      if (null != pResourceFDtoNew.getParamF2()) {
         resourceFDtoExisting.setParamF2(pResourceFDtoNew.getParamF2());
      }
      if (null != pResourceFDtoNew.getParamF3()) {
         resourceFDtoExisting.setParamF3(pResourceFDtoNew.getParamF3());
      }
      if (null != pResourceFDtoNew.getParamF4()) {
         resourceFDtoExisting.setParamF4(pResourceFDtoNew.getParamF4());
      }
      if (null != pResourceFDtoNew.getParamF5()) {
         resourceFDtoExisting.setParamF5(pResourceFDtoNew.getParamF5());
      }
      if (null != pResourceFDtoNew.getParamF6()) {
         resourceFDtoExisting.setParamF6(pResourceFDtoNew.getParamF6());
      }
      if (null != pResourceFDtoNew.getParamF7()) {
         resourceFDtoExisting.setParamF7(pResourceFDtoNew.getParamF7());
      }
      if (null != pResourceFDtoNew.getParamF8()) {
         resourceFDtoExisting.setParamF8(pResourceFDtoNew.getParamF8());
      }
      this.resourceHandler.replaceResource(pId, resourceFDtoExisting);
      return Response.status(Response.Status.ACCEPTED).entity(resourceFDtoExisting).build();
   }
}
