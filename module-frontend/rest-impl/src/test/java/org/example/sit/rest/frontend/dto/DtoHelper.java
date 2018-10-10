package org.example.sit.rest.frontend.dto;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

/**
 * Helper methods.
 */
@SuppressWarnings({"javadoc", "boxing"})
public final class DtoHelper extends org.example.sit.common.dto.DtoHelper {
   private DtoHelper() {
      // Hidden to prevent instantiation
   }
   
   /**
    * Create a {@code ResourceDDto} with random values for its fields.
    *
    * @return A instance of a {@code ResourceDDto}.
    */
   public static ResourceDDto createResourceDDto() {
      return new ResourceDDto(RandomUtils.nextLong())
            .setParamD1(RandomStringUtils.randomAlphanumeric(10))
            .setParamD2(RandomUtils.nextInt())
            .setParamD3(RandomUtils.nextBoolean())
            .setParamD4(createResourceDDtoParam4List());
   }
   
   /**
    * Create a list of random values for the field {@code param4} of a {@code ResourceDDto}.
    *
    * @return A list of random values for the field {@code param4} of a {@code ResourceDDto}.
    */
   public static List<Long> createResourceDDtoParam4List() {
      return Stream.generate(RandomUtils::nextLong)
            .distinct()
            .limit(RandomUtils.nextInt(2, 11))
            .collect(Collectors.toCollection(LinkedList::new));
   }
   
   /**
    * Return a deep copy of a {@code ResourceDDto}.
    *
    * @param pResourceDtoToClone The {@code ResourceDDto} to clone.
    * @return A cloned instance of a {@code ResourceDDto}.
    */
   public static ResourceDDto cloneResourceDDto(final ResourceDDto pResourceDtoToClone) {
      if (null == pResourceDtoToClone) {
         return null;
      }
      return new ResourceDDto(pResourceDtoToClone.getIdD())
            .setParamD1(pResourceDtoToClone.getParamD1())
            .setParamD2(pResourceDtoToClone.getParamD2())
            .setParamD3(pResourceDtoToClone.getParamD3())
            .setParamD4(pResourceDtoToClone.getParamD4());
   }
   
   /**
    * Create a {@code ResourceEDto} with random values for its fields.
    *
    * @return A instance of a {@code ResourceEDto}.
    */
   public static ResourceEDto createResourceEDto() {
      return new ResourceEDto(RandomUtils.nextLong())
            .setParamE1(RandomUtils.nextInt())
            .setParamE2(RandomStringUtils.randomAlphanumeric(10))
            .setParamE3(RandomStringUtils.randomAlphanumeric(11))
            .setParamE4(RandomUtils.nextLong())
            .setParamE5(RandomUtils.nextBoolean())
            .setParamE6(createResourceEDtoParam6List());
   }
   
   /**
    * Create a list of random values for the field {@code param6} of a {@code ResourceEDto}.
    *
    * @return A list of random values for the field {@code param6} of a {@code ResourceEDto}.
    */
   public static List<Integer> createResourceEDtoParam6List() {
      return Stream.generate(RandomUtils::nextInt)
            .distinct()
            .limit(RandomUtils.nextInt(2, 11))
            .collect(Collectors.toCollection(LinkedList::new));
   }
   
   /**
    * Return a deep copy of a {@code ResourceEDto}.
    *
    * @param pResourceDtoToClone The {@code ResourceEDto} to clone.
    * @return A cloned instance of a {@code ResourceEDto}.
    */
   public static ResourceEDto cloneResourceEDto(final ResourceEDto pResourceDtoToClone) {
      if (null == pResourceDtoToClone) {
         return null;
      }
      return new ResourceEDto(pResourceDtoToClone.getIdE())
            .setParamE1(pResourceDtoToClone.getParamE1())
            .setParamE2(pResourceDtoToClone.getParamE2())
            .setParamE3(pResourceDtoToClone.getParamE3())
            .setParamE4(pResourceDtoToClone.getParamE4())
            .setParamE5(pResourceDtoToClone.getParamE5())
            .setParamE6(pResourceDtoToClone.getParamE6());
   }
   
   /**
    * Create a {@code ResourceFDto} with random values for its fields.
    *
    * @return A instance of a {@code ResourceFDto}.
    */
   public static ResourceFDto createResourceFDto() {
      return new ResourceFDto(RandomUtils.nextLong())
            .setParamF1(RandomStringUtils.randomAlphanumeric(10))
            .setParamF2(RandomUtils.nextInt())
            .setParamF3(RandomStringUtils.randomAlphanumeric(11))
            .setParamF4(RandomStringUtils.randomAlphanumeric(12))
            .setParamF5(RandomUtils.nextBoolean())
            .setParamF6(RandomUtils.nextLong())
            .setParamF7(RandomStringUtils.randomAlphanumeric(13))
            .setParamF8(createResourceFDtoParam8List());
   }
   
   /**
    * Create a list of random values for the field {@code param8} of a {@code ResourceFDto}.
    *
    * @return A list of random values for the field {@code param8} of a {@code ResourceFDto}.
    */
   public static List<String> createResourceFDtoParam8List() {
      return Stream.generate(() -> RandomStringUtils.randomAlphanumeric(5))
            .limit(RandomUtils.nextInt(2, 11))
            .collect(Collectors.toCollection(LinkedList::new));
   }
   
   /**
    * Return a deep copy of a {@code ResourceFDto}.
    *
    * @param pResourceDtoToClone The {@code ResourceFDto} to clone.
    * @return A cloned instance of a {@code ResourceFDto}.
    */
   public static ResourceFDto cloneResourceFDto(final ResourceFDto pResourceDtoToClone) {
      if (null == pResourceDtoToClone) {
         return null;
      }
      return new ResourceFDto(pResourceDtoToClone.getIdF())
            .setParamF1(pResourceDtoToClone.getParamF1())
            .setParamF2(pResourceDtoToClone.getParamF2())
            .setParamF3(pResourceDtoToClone.getParamF3())
            .setParamF4(pResourceDtoToClone.getParamF4())
            .setParamF5(pResourceDtoToClone.getParamF5())
            .setParamF6(pResourceDtoToClone.getParamF6())
            .setParamF7(pResourceDtoToClone.getParamF7())
            .setParamF8(pResourceDtoToClone.getParamF8());
   }
}
