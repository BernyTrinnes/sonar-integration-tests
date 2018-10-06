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
@SuppressWarnings({"javadoc", "boxing"})
public final class DtoHelper extends org.example.sit.common.dto.DtoHelper {
   private DtoHelper() {
      // Hidden to prevent instantiation
   }
   
   /**
    * Create a {@code ResourceADto} with random values for its fields.
    *
    * @return A instance of a {@code ResourceADto}.
    */
   public static ResourceADto createResourceADto() {
      return new ResourceADto(RandomUtils.nextLong())
            .setParamA1(RandomStringUtils.randomAlphanumeric(10))
            .setParamA2(RandomUtils.nextBoolean())
            .setParamA3(createResourceADtoParam3List());
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
      return new ResourceADto(pResourceDtoToClone.getIdA())
            .setParamA1(pResourceDtoToClone.getParamA1())
            .setParamA2(pResourceDtoToClone.getParamA2())
            .setParamA3(pResourceDtoToClone.getParamA3());
   }
   
   /**
    * Create a {@code ResourceBDto} with random values for its fields.
    *
    * @return A instance of a {@code ResourceBDto}.
    */
   public static ResourceBDto createResourceBDto() {
      return new ResourceBDto(RandomUtils.nextLong())
            .setParamB1(RandomUtils.nextInt())
            .setParamB2(RandomStringUtils.randomAlphanumeric(10))
            .setParamB3(RandomStringUtils.randomAlphanumeric(11))
            .setParamB4(RandomUtils.nextBoolean())
            .setParamB5(createResourceBDtoParam5List());
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
      return new ResourceBDto(pResourceDtoToClone.getIdB())
            .setParamB1(pResourceDtoToClone.getParamB1())
            .setParamB2(pResourceDtoToClone.getParamB2())
            .setParamB3(pResourceDtoToClone.getParamB3())
            .setParamB4(pResourceDtoToClone.getParamB4())
            .setParamB5(pResourceDtoToClone.getParamB5());
   }
   
   /**
    * Create a {@code ResourceCDto} with random values for its fields.
    *
    * @return A instance of a {@code ResourceCDto}.
    */
   public static ResourceCDto createResourceCDto() {
      return new ResourceCDto(RandomUtils.nextLong())
            .setParamC1(RandomStringUtils.randomAlphanumeric(10))
            .setParamC2(RandomUtils.nextInt())
            .setParamC3(RandomStringUtils.randomAlphanumeric(10))
            .setParamC4(RandomStringUtils.randomAlphanumeric(11))
            .setParamC5(RandomUtils.nextBoolean())
            .setParamC6(RandomUtils.nextLong())
            .setParamC7(createResourceCDtoParam7List());
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
      return new ResourceCDto(pResourceDtoToClone.getIdC())
            .setParamC1(pResourceDtoToClone.getParamC1())
            .setParamC2(pResourceDtoToClone.getParamC2())
            .setParamC3(pResourceDtoToClone.getParamC3())
            .setParamC4(pResourceDtoToClone.getParamC4())
            .setParamC5(pResourceDtoToClone.getParamC5())
            .setParamC6(pResourceDtoToClone.getParamC6())
            .setParamC7(pResourceDtoToClone.getParamC7());
   }
}
