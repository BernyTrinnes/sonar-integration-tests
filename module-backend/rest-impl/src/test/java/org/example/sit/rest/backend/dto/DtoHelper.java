package org.example.sit.rest.backend.dto;

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
    * Create a {@code ResourceADto} with random values for its fields.
    *
    * @return An instance of a {@code ResourceADto}.
    */
   public static ResourceADto createResourceADto() {
      return ResourceADto.builder()
            .idA(RandomUtils.nextLong())
            .paramA1(RandomStringUtils.randomAlphanumeric(10))
            .paramA2(RandomUtils.nextBoolean())
            .paramA3(createResourceADtoParam3List())
            .build();
   }
   
   /**
    * Create a list of random values for the field {@code param3} of a {@code ResourceADto}.
    *
    * @return A list of random values for the field {@code param3} of a {@code ResourceADto}.
    */
   public static List<Long> createResourceADtoParam3List() {
      return Stream.generate(RandomUtils::nextLong)
            .distinct()
            .limit(RandomUtils.nextInt(2, 11))
            .collect(Collectors.toCollection(LinkedList::new));
   }
   
   /**
    * Return a deep copy of a {@code ResourceADto}.
    *
    * @param pResourceDtoToClone The {@code ResourceADto} to clone.
    * @return A cloned instance of a {@code ResourceADto}.
    */
   public static ResourceADto cloneResourceADto(final ResourceADto pResourceDtoToClone) {
      if (null == pResourceDtoToClone) {
         return null;
      }
      return ResourceADto.builder()
            .idA(pResourceDtoToClone.getIdA())
            .paramA1(pResourceDtoToClone.getParamA1())
            .paramA2(pResourceDtoToClone.getParamA2())
            .paramA3(pResourceDtoToClone.getParamA3())
            .build();
   }
   
   /**
    * Create a {@code ResourceBDto} with random values for its fields.
    *
    * @return An instance of a {@code ResourceBDto}.
    */
   public static ResourceBDto createResourceBDto() {
      return ResourceBDto.builder()
            .idB(RandomUtils.nextLong())
            .paramB1(RandomUtils.nextInt())
            .paramB2(RandomStringUtils.randomAlphanumeric(10))
            .paramB3(RandomStringUtils.randomAlphanumeric(11))
            .paramB4(RandomUtils.nextBoolean())
            .paramB5(createResourceBDtoParam5List())
            .build();
   }
   
   /**
    * Create a list of random values for the field {@code param5} of a {@code ResourceBDto}.
    *
    * @return A list of random values for the field {@code param5} of a {@code ResourceBDto}.
    */
   public static List<Integer> createResourceBDtoParam5List() {
      return Stream.generate(RandomUtils::nextInt)
            .distinct()
            .limit(RandomUtils.nextInt(2, 11))
            .collect(Collectors.toCollection(LinkedList::new));
   }
   
   /**
    * Return a deep copy of a {@code ResourceBDto}.
    *
    * @param pResourceDtoToClone The {@code ResourceBDto} to clone.
    * @return A cloned instance of a {@code ResourceBDto}.
    */
   public static ResourceBDto cloneResourceBDto(final ResourceBDto pResourceDtoToClone) {
      if (null == pResourceDtoToClone) {
         return null;
      }
      return ResourceBDto.builder()
            .idB(pResourceDtoToClone.getIdB())
            .paramB1(pResourceDtoToClone.getParamB1())
            .paramB2(pResourceDtoToClone.getParamB2())
            .paramB3(pResourceDtoToClone.getParamB3())
            .paramB4(pResourceDtoToClone.getParamB4())
            .paramB5(pResourceDtoToClone.getParamB5())
            .build();
   }
   
   /**
    * Create a {@code ResourceCDto} with random values for its fields.
    *
    * @return An instance of a {@code ResourceCDto}.
    */
   public static ResourceCDto createResourceCDto() {
      return ResourceCDto.builder()
            .idC(RandomUtils.nextLong())
            .paramC1(RandomStringUtils.randomAlphanumeric(10))
            .paramC2(RandomUtils.nextInt())
            .paramC3(RandomStringUtils.randomAlphanumeric(10))
            .paramC4(RandomStringUtils.randomAlphanumeric(11))
            .paramC5(RandomUtils.nextBoolean())
            .paramC6(RandomUtils.nextLong())
            .paramC7(createResourceCDtoParam7List())
            .build();
   }
   
   /**
    * Create a list of random values for the field {@code param7} of a {@code ResourceCDto}.
    *
    * @return A list of random values for the field {@code param7} of a {@code ResourceCDto}.
    */
   public static List<String> createResourceCDtoParam7List() {
      return Stream.generate(() -> RandomStringUtils.randomAlphanumeric(5))
            .limit(RandomUtils.nextInt(2, 11))
            .collect(Collectors.toCollection(LinkedList::new));
   }
   
   /**
    * Return a deep copy of a {@code ResourceCDto}.
    *
    * @param pResourceDtoToClone The {@code ResourceCDto} to clone.
    * @return A cloned instance of a {@code ResourceCDto}.
    */
   public static ResourceCDto cloneResourceCDto(final ResourceCDto pResourceDtoToClone) {
      if (null == pResourceDtoToClone) {
         return null;
      }
      return ResourceCDto.builder()
            .idC(pResourceDtoToClone.getIdC())
            .paramC1(pResourceDtoToClone.getParamC1())
            .paramC2(pResourceDtoToClone.getParamC2())
            .paramC3(pResourceDtoToClone.getParamC3())
            .paramC4(pResourceDtoToClone.getParamC4())
            .paramC5(pResourceDtoToClone.getParamC5())
            .paramC6(pResourceDtoToClone.getParamC6())
            .paramC7(pResourceDtoToClone.getParamC7())
            .build();
   }
}
