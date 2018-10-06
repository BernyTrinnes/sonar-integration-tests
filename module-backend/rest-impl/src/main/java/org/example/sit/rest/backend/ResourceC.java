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
import org.example.sit.rest.backend.dto.ResourceCDto;

/**
 * The REST resource which will be tested using unit tests in {@code module-backend/rest-impl} and
 * integration tests in {@code module-integration-test}.
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
public class ResourceC implements BaseResource {
   private ResourceHandler<ResourceCDto> resources;
   
   /**
    * Initializes this resource.
    */
   public ResourceC() {
      this.resources = new ResourceHandler<>();
   }
   
   /**
    * Return the data of all resources.
    *
    * @return A response containing the data of all resources, or a response with status {@code 404}
    * if there are no resources available.
    */
   @GET
   public Response getAllResourceC() {
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
   public Response deleteAllResourceC() {
      this.resources.removeAllResources();
      return Response.ok().entity(new AbstractMap.SimpleEntry<>("cleared", Boolean.TRUE)).build();
   }
   
   /**
    * Store the data of the given resource.
    *
    * @param pResourceC The data of the resource.
    * @return A response with the status of the process.
    */
   @POST
   public Response createResourceC(final ResourceCDto pResourceC) {
      if (null == pResourceC) {
         return Response.status(Response.Status.BAD_REQUEST).build();
      }
      if (this.resources.hasResource(pResourceC.getIdC())) {
         return Response.status(Response.Status.CONFLICT)
               .entity(getEntityForId(pResourceC.getIdC())).build();
      }
      
      this.resources.addResource(pResourceC.getIdC(), pResourceC);
      return Response.status(Response.Status.CREATED).entity(pResourceC).build();
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
   public Response getResourceC(@PathParam("id") final long pId) {
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
   public Response deleteResourceC(@PathParam("id") final long pId) {
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
    * @param pResourceCDto The data of the resource.
    * @return A response containing the result of the process.
    */
   @PUT
   @Path("/{id}")
   public Response updateResourceC(@PathParam("id") final long pId,
         final ResourceCDto pResourceCDto) {
      if (!this.resources.hasResource(pId)) {
         return Response.status(Response.Status.NOT_FOUND).entity(getEntityForId(pId)).build();
      }
      if (null == pResourceCDto) {
         return Response.status(Response.Status.BAD_REQUEST).entity(getEntityForId(pId)).build();
      }
      
      final ResourceCDto resourceCDto = this.resources.getResource(pId);
      if (null != pResourceCDto.getParamC1()) {
         resourceCDto.setParamC1(pResourceCDto.getParamC1());
      }
      if (null != pResourceCDto.getParamC2()) {
         resourceCDto.setParamC2(pResourceCDto.getParamC2());
      }
      if (null != pResourceCDto.getParamC3()) {
         resourceCDto.setParamC3(pResourceCDto.getParamC3());
      }
      if (null != pResourceCDto.getParamC4()) {
         resourceCDto.setParamC4(pResourceCDto.getParamC4());
      }
      if (null != pResourceCDto.getParamC5()) {
         resourceCDto.setParamC5(pResourceCDto.getParamC5());
      }
      if (null != pResourceCDto.getParamC6()) {
         resourceCDto.setParamC6(pResourceCDto.getParamC6());
      }
      if (null != pResourceCDto.getParamC7()) {
         resourceCDto.setParamC7(pResourceCDto.getParamC7());
      }
      this.resources.replaceResource(pId, resourceCDto);
      return Response.status(Response.Status.ACCEPTED).entity(resourceCDto).build();
   }
}
