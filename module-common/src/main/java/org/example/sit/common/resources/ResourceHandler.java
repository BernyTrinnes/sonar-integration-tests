package org.example.sit.common.resources;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Handler for {@code ResourceXDto} elements.
 *
 * @param <T> The type of the DTO which will be stored.
 */
public class ResourceHandler<T> {
   private static final Map<Long, Object> RESOURCES = new LinkedHashMap<>();
   
   /**
    * Add a resource.
    *
    * @param pId The ID of the resource.
    * @param pResourceDto The data of the resource.
    */
   public void addResource(final long pId, final T pResourceDto) {
      RESOURCES.put(Long.valueOf(pId), pResourceDto);
   }
   
   /**
    * Return the data of all resources.
    *
    * @return A list containing the data of all resources.
    */
   @SuppressWarnings("unchecked")
   public List<T> getAllResources() {
      return RESOURCES.values().stream().map(elem -> (T) elem).collect(Collectors.toCollection(LinkedList::new));
   }
   
   /**
    * Return the data of the resource with the given ID.
    *
    * @param pId The ID of the resource.
    * @return The data of the resource with the given ID, <code>null</code> if the resource with the given ID does not
    * exist.
    */
   @SuppressWarnings("unchecked")
   public T getResource(final long pId) {
      return (T) RESOURCES.get(Long.valueOf(pId));
   }
   
   /**
    * Check if any resources are available.
    *
    * @return {@code true} if there are resources available, {@code false} otherwise.
    */
   public boolean hasResources() {
      return !RESOURCES.isEmpty();
   }
   
   /**
    * Check if a resource with the given ID is available.
    *
    * @param pId The ID of the resource.
    * @return {@code true} if a resource with the given ID is available, {@code false} otherwise.
    */
   public boolean hasResource(final long pId) {
      return RESOURCES.containsKey(Long.valueOf(pId));
   }
   
   /**
    * Replace the resource with the given ID.
    *
    * @param pId The ID of the resource to replace.
    * @param pResourceDto The data of the resource.
    */
   public void replaceResource(final long pId, final T pResourceDto) {
      RESOURCES.replace(Long.valueOf(pId), pResourceDto);
   }
   
   /**
    * Remove all resources.
    */
   public void removeAllResources() {
      RESOURCES.clear();
   }
   
   /**
    * Remove the resource with the given ID.
    *
    * @param pId The ID of the resource to remove.
    */
   public void removeResource(final long pId) {
      RESOURCES.remove(Long.valueOf(pId));
   }
}
