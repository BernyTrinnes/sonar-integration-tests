package org.example.sit.rest.frontend;

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
import org.example.sit.rest.frontend.dto.ResourceDDto;

/**
 * The REST resource which will be tested using unit tests in {@code module-frontend/rest-impl}.
 */
@Path("resource-d")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ResourceD {
   private ResourceHandler<ResourceDDto> resources;
   
   /**
    * Initializes this resource.
    */
   public ResourceD() {
      this.resources = new ResourceHandler<>();
   }
   
   /**
    * Return the data of all resources.
    *
    * @return A response containing the data of all resources, or a response with status {@code 404}
    * if there are no resources available.
    */
   @GET
   public Response getAllResourceD() {
      if (!this.resources.hasResources()) {
         return Response.status(Response.Status.NOT_FOUND).build();
      }
      return Response.ok().entity(this.resources.getAllResources()).build();
   }
   
   /**
    * Store the data of the given resource.
    *
    * @param pResourceD The data of the resource.
    * @return A response with the status of the process.
    */
   @POST
   public Response createResourceD(@BeanParam final ResourceDDto pResourceD) {
      if (null == pResourceD) {
         return Response.status(Response.Status.BAD_REQUEST).build();
      }
      if (this.resources.hasResource(pResourceD.getIdD())) {
         return Response.status(Response.Status.CONFLICT)
               .entity(getEntityForId(pResourceD.getIdD())).build();
      }
      
      this.resources.addResource(pResourceD.getIdD(), pResourceD);
      return Response.status(Response.Status.CREATED).entity(pResourceD).build();
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
   public Response getResourceD(@PathParam("id") final long pId) {
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
   public Response deleteResourceD(@PathParam("id") final long pId) {
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
    * @param pResourceDDto The data of the resource.
    * @return A response containing the result of the process.
    */
   @PUT
   @Path("/{id}")
   public Response updateResourceD(@PathParam("id") final long pId,
         @BeanParam final ResourceDDto pResourceDDto) {
      if (!this.resources.hasResource(pId)) {
         return Response.status(Response.Status.NOT_FOUND).entity(getEntityForId(pId)).build();
      }
      if (null == pResourceDDto) {
         return Response.status(Response.Status.BAD_REQUEST).entity(getEntityForId(pId)).build();
      }
      
      final ResourceDDto resourceDDto = this.resources.getResource(pId);
      if (null != pResourceDDto.getParamD1()) {
         resourceDDto.setParamD1(pResourceDDto.getParamD1());
      }
      if (null != pResourceDDto.getParamD2()) {
         resourceDDto.setParamD2(pResourceDDto.getParamD2());
      }
      if (null != pResourceDDto.getParamD3()) {
         resourceDDto.setParamD3(pResourceDDto.getParamD3());
      }
      if (null != pResourceDDto.getParamD4()) {
         resourceDDto.setParamD4(pResourceDDto.getParamD4());
      }
      this.resources.replaceResource(pId, resourceDDto);
      return Response.status(Response.Status.ACCEPTED).entity(resourceDDto).build();
   }
}
