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
import org.example.sit.rest.backend.dto.ResourceCDto;

/**
 * The REST resource which will be tested using unit tests in {@code module-backend/rest-impl} and integration tests in
 * {@code module-integration-tests}.
 *
 * <ul>
 *    <li>
 *       Unit tests in module {@code module-backend/rest-impl}:
 *       <ul>
 *          <li>{@code getAvailability()}</li>
 *          <li>{@code getAllResourceC()}</li>
 *          <li>{@code getResourceC(long)}</li>
 *          <li>{@code deleteResourceC(long)}</li>
 *       </ul>
 *    </li>
 *    <li>
 *       Integration tests in module {@code module-integration-test}:
 *       <ul>
 *          <li>{@code getAvailability()}</li>
 *          <li>{@code deleteAllResourceC()}</li>
 *          <li>{@code createResourceC(ResourceCDto)}</li>
 *          <li>{@code updateResourceC(long, ResourceCDto)}</li>
 *       </ul>
 *    </li>
 * </ul>
 */
@Path("resource-c")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ResourceC extends AbstractBaseResource {
   private final ResourceHandler<ResourceCDto> resourceHandler;
   
   /**
    * Initialize this resource. <br>
    * This initialization is needed by Jersey.
    */
   @SuppressWarnings("unused")
   public ResourceC() {
      this.resourceHandler = new ResourceHandler<>();
   }
   
   /**
    * Initialize this resource. <br>
    * This initialization is needed by the test mocks.
    *
    * @param pResourceHandler The {@link ResourceHandler} for this resource.
    */
   @SuppressWarnings("unused")
   public ResourceC(final ResourceHandler<ResourceCDto> pResourceHandler) {
      this.resourceHandler = pResourceHandler;
   }
   
   /**
    * Return the data of all resources.
    *
    * @return A response containing the data of all resources, or a response with status {@code 404} if there are no
    * resources available.
    */
   @GET
   public Response getAllResourceC() {
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
   public Response deleteAllResourceC() {
      this.resourceHandler.removeAllResources();
      return Response.ok().entity(new AbstractMap.SimpleEntry<>("cleared", Boolean.TRUE)).build();
   }
   
   /**
    * Store the data of the given resource.
    *
    * @param pResourceC The data of the resource.
    * @return An HTTP status {@code 400} response if the given resource is invalid, or an HTTP status {@code 409}
    * response if the given resource is already stored, or an HTTP status {@code 201} response containing the data of
    * the resource itself if it could be stored successfully.
    */
   @POST
   public Response createResourceC(final ResourceCDto pResourceC) {
      if (null == pResourceC) {
         return Response.status(Response.Status.BAD_REQUEST).build();
      }
      if (this.resourceHandler.hasResource(pResourceC.getIdC())) {
         return Response.status(Response.Status.CONFLICT).entity(getEntityForId(pResourceC.getIdC())).build();
      }
      
      this.resourceHandler.addResource(pResourceC.getIdC(), pResourceC);
      return Response.status(Response.Status.CREATED).entity(pResourceC).build();
   }
   
   /**
    * Retrieve the data of the resource with the given ID.
    *
    * @param pIdC The ID of the resource.
    * @return A response containing the data of the requested resource, or a response with status {@code 404} if there
    * is no resource with the given ID.
    */
   @GET
   @Path("/{id}")
   public Response getResourceC(@PathParam("id") final long pIdC) {
      if (!this.resourceHandler.hasResource(pIdC)) {
         return Response.status(Response.Status.NOT_FOUND).entity(getEntityForId(pIdC)).build();
      }
      
      return Response.ok().entity(this.resourceHandler.getResource(pIdC)).build();
   }
   
   /**
    * Remove the resource with the given ID.
    *
    * @param pIdC The ID of the resource.
    * @return A response containing the result of the process.
    */
   @DELETE
   @Path("/{id}")
   public Response deleteResourceC(@PathParam("id") final long pIdC) {
      if (!this.resourceHandler.hasResource(pIdC)) {
         return Response.status(Response.Status.NOT_FOUND).entity(getEntityForId(pIdC)).build();
      }
      
      this.resourceHandler.removeResource(pIdC);
      return Response.ok().entity(new AbstractMap.SimpleEntry<>("deleted", Long.valueOf(pIdC))).build();
   }
   
   /**
    * Update the data of the resource with the given ID.
    *
    * @param pIdC The ID of the resource.
    * @param pResourceCDtoNew The updated data of the resource.
    * @return An HTTP status {@code 400} response if the given resource is invalid, or an HTTP status {@code 404}
    * response if the resource with the given ID is not available, or an HTTP status {@code 202} response containing
    * the data of the updated resource if it could be updated successfully.
    */
   @PUT
   @Path("/{id}")
   public Response updateResourceC(@PathParam("id") final long pIdC, final ResourceCDto pResourceCDtoNew) {
      if (null == pResourceCDtoNew) {
         return Response.status(Response.Status.BAD_REQUEST).entity(getEntityForId(pIdC)).build();
      }
      if (!this.resourceHandler.hasResource(pIdC)) {
         return Response.status(Response.Status.NOT_FOUND).entity(getEntityForId(pIdC)).build();
      }
      
      final var resourceCDto = this.resourceHandler.getResource(pIdC);
      if (null != pResourceCDtoNew.getParamC1()) {
         resourceCDto.setParamC1(pResourceCDtoNew.getParamC1());
      }
      if (null != pResourceCDtoNew.getParamC2()) {
         resourceCDto.setParamC2(pResourceCDtoNew.getParamC2());
      }
      if (null != pResourceCDtoNew.getParamC3()) {
         resourceCDto.setParamC3(pResourceCDtoNew.getParamC3());
      }
      if (null != pResourceCDtoNew.getParamC4()) {
         resourceCDto.setParamC4(pResourceCDtoNew.getParamC4());
      }
      if (null != pResourceCDtoNew.getParamC5()) {
         resourceCDto.setParamC5(pResourceCDtoNew.getParamC5());
      }
      if (null != pResourceCDtoNew.getParamC6()) {
         resourceCDto.setParamC6(pResourceCDtoNew.getParamC6());
      }
      if (null != pResourceCDtoNew.getParamC7()) {
         resourceCDto.setParamC7(pResourceCDtoNew.getParamC7());
      }
      this.resourceHandler.replaceResource(pIdC, resourceCDto);
      return Response.status(Response.Status.ACCEPTED).entity(resourceCDto).build();
   }
}
