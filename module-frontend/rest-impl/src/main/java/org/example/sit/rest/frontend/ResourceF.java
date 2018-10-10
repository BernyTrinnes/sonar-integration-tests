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
import org.example.sit.rest.frontend.dto.ResourceFDto;

/**
 * The REST resource which will be tested using unit tests in {@code module-frontend/rest-impl} and
 * integration tests in {@code module-integration-test}.
 *
 * <ul>
 *    <li>
 *       Unit tests in module {@code module-backend/rest-impl}:
 *       <ul>
 *          <li>{@code createResourceF(ResourceFDto)}</li>
 *          <li>{@code deleteResourceF(long)}</li>
 *          <li>{@code updateResourceF(long, ResourceFDto)}</li>
 *       </ul>
 *    </li>
 *    <li>
 *       Integration tests in module {@code module-integration-test}:
 *       <ul>
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
public class ResourceF {
   private ResourceHandler<ResourceFDto> resources;
   
   /**
    * Initializes this resource.
    */
   public ResourceF() {
      this.resources = new ResourceHandler<>();
   }
   
   /**
    * Return the data of all resources.
    *
    * @return A response containing the data of all resources, or a response with status {@code 404}
    * if there are no resources available.
    */
   @GET
   public Response getAllResourceF() {
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
   public Response deleteAllResourceF() {
      this.resources.removeAllResources();
      return Response.ok().entity(new AbstractMap.SimpleEntry<>("cleared", Boolean.TRUE)).build();
   }
   
   /**
    * Store the data of the given resource.
    *
    * @param pResourceF The data of the resource.
    * @return A response with the status of the process.
    */
   @POST
   public Response createResourceF(final ResourceFDto pResourceF) {
      if (null == pResourceF) {
         return Response.status(Response.Status.BAD_REQUEST).build();
      }
      if (this.resources.hasResource(pResourceF.getIdF())) {
         return Response.status(Response.Status.CONFLICT)
               .entity(getEntityForId(pResourceF.getIdF())).build();
      }
      
      this.resources.addResource(pResourceF.getIdF(), pResourceF);
      return Response.status(Response.Status.CREATED).entity(pResourceF).build();
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
   public Response getResourceF(@PathParam("id") final long pId) {
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
   public Response deleteResourceF(@PathParam("id") final long pId) {
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
    * @param pResourceF The data of the resource.
    * @return A response containing the result of the process.
    */
   @PUT
   @Path("/{id}")
   public Response updateResourceF(@PathParam("id") final long pId,
         final ResourceFDto pResourceF) {
      if (!this.resources.hasResource(pId)) {
         return Response.status(Response.Status.NOT_FOUND).entity(getEntityForId(pId)).build();
      }
      if (null == pResourceF) {
         return Response.status(Response.Status.BAD_REQUEST).entity(getEntityForId(pId)).build();
      }
      
      final ResourceFDto resourceFDto = this.resources.getResource(pId);
      if (null != pResourceF.getParamF1()) {
         resourceFDto.setParamF1(pResourceF.getParamF1());
      }
      if (null != pResourceF.getParamF2()) {
         resourceFDto.setParamF2(pResourceF.getParamF2());
      }
      if (null != pResourceF.getParamF3()) {
         resourceFDto.setParamF3(pResourceF.getParamF3());
      }
      if (null != pResourceF.getParamF4()) {
         resourceFDto.setParamF4(pResourceF.getParamF4());
      }
      if (null != pResourceF.getParamF5()) {
         resourceFDto.setParamF5(pResourceF.getParamF5());
      }
      if (null != pResourceF.getParamF6()) {
         resourceFDto.setParamF6(pResourceF.getParamF6());
      }
      if (null != pResourceF.getParamF7()) {
         resourceFDto.setParamF7(pResourceF.getParamF7());
      }
      if (null != pResourceF.getParamF8()) {
         resourceFDto.setParamF8(pResourceF.getParamF8());
      }
      this.resources.replaceResource(pId, resourceFDto);
      return Response.status(Response.Status.ACCEPTED).entity(resourceFDto).build();
   }
}
