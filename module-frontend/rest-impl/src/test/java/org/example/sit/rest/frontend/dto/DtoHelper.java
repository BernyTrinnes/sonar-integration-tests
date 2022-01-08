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
public final class DtoHelper extends org.example.sit.common.dto.DtoHelper {
   private DtoHelper() {
      // Hidden to prevent instantiation
   }
   
   /**
    * Create a {@code ResourceDDto} with random values for its fields.
    *
    * @return An instance of a {@code ResourceDDto}.
    */
   public static ResourceDDto createResourceDDto() {
      return ResourceDDto.builder()
            .idD(RandomUtils.nextLong())
            .paramD1(RandomStringUtils.randomAlphanumeric(10))
            .paramD2(RandomUtils.nextInt())
            .paramD3(RandomUtils.nextBoolean())
            .paramD4(createResourceDDtoParam4List())
            .build();
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
      return ResourceDDto.builder()
            .idD(pResourceDtoToClone.getIdD())
            .paramD1(pResourceDtoToClone.getParamD1())
            .paramD2(pResourceDtoToClone.getParamD2())
            .paramD3(pResourceDtoToClone.getParamD3())
            .paramD4(pResourceDtoToClone.getParamD4())
            .build();
   }
   
   /**
    * Create a {@code ResourceEDto} with random values for its fields.
    *
    * @return An instance of a {@code ResourceEDto}.
    */
   public static ResourceEDto createResourceEDto() {
      return ResourceEDto.builder()
            .idE(RandomUtils.nextLong())
            .paramE1(RandomUtils.nextInt())
            .paramE2(RandomStringUtils.randomAlphanumeric(10))
            .paramE3(RandomStringUtils.randomAlphanumeric(11))
            .paramE4(RandomUtils.nextLong())
            .paramE5(RandomUtils.nextBoolean())
            .paramE6(createResourceEDtoParam6List())
            .build();
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
      return ResourceEDto.builder()
            .idE(pResourceDtoToClone.getIdE())
            .paramE1(pResourceDtoToClone.getParamE1())
            .paramE2(pResourceDtoToClone.getParamE2())
            .paramE3(pResourceDtoToClone.getParamE3())
            .paramE4(pResourceDtoToClone.getParamE4())
            .paramE5(pResourceDtoToClone.getParamE5())
            .paramE6(pResourceDtoToClone.getParamE6())
            .build();
   }
   
   /**
    * Create a {@code ResourceFDto} with random values for its fields.
    *
    * @return An instance of a {@code ResourceFDto}.
    */
   public static ResourceFDto createResourceFDto() {
      return ResourceFDto.builder()
            .idF(RandomUtils.nextLong())
            .paramF1(RandomStringUtils.randomAlphanumeric(10))
            .paramF2(RandomUtils.nextInt())
            .paramF3(RandomStringUtils.randomAlphanumeric(11))
            .paramF4(RandomStringUtils.randomAlphanumeric(12))
            .paramF5(RandomUtils.nextBoolean())
            .paramF6(RandomUtils.nextLong())
            .paramF7(RandomStringUtils.randomAlphanumeric(13))
            .paramF8(createResourceFDtoParam8List())
            .build();
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
      return ResourceFDto.builder()
            .idF(pResourceDtoToClone.getIdF())
            .paramF1(pResourceDtoToClone.getParamF1())
            .paramF2(pResourceDtoToClone.getParamF2())
            .paramF3(pResourceDtoToClone.getParamF3())
            .paramF4(pResourceDtoToClone.getParamF4())
            .paramF5(pResourceDtoToClone.getParamF5())
            .paramF6(pResourceDtoToClone.getParamF6())
            .paramF7(pResourceDtoToClone.getParamF7())
            .paramF8(pResourceDtoToClone.getParamF8())
            .build();
   }
}
