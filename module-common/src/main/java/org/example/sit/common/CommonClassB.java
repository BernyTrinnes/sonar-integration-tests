package org.example.sit.common;

/**
 * The code in this class will be tested using unit tests in {@code module-backend/rest-impl}.
 */
@SuppressWarnings("boxing")
public class CommonClassB {
   /**
    * Initializer.
    */
   public CommonClassB() {
      // Nothing to do
   }
   
   /**
    * Compute the sum of {@code pArg1} and {@code pArg2}.
    *
    * @param pArg1 The first value.
    * @param pArg2 The second value.
    * @return The result of the computation.
    * @throws ArithmeticException If the result overflows the {@code int} range.
    */
   public int add(final int pArg1, final int pArg2) {
      final int result = pArg1 + pArg2;
      if (((pArg1 ^ result) & (pArg2 ^ result)) < 0) {
         throw new ArithmeticException(
               String.format("The result of '%d' added to '%d' overflows the int range.",
                     pArg1, pArg2));
      }
      return result;
   }
   
   /**
    * Compute the sum of {@code pArg1} and {@code pArg2}.
    *
    * @param pArg1 The first value.
    * @param pArg2 The second value.
    * @return The result of the computation.
    * @throws ArithmeticException If the result overflows the {@code long} range.
    */
   public long add(final long pArg1, final long pArg2) {
      final long result = pArg1 + pArg2;
      if (((pArg1 ^ result) & (pArg2 ^ result)) < 0) {
         throw new ArithmeticException(
               String.format("The result of '%d' added to '%d' overflows the long range.",
                     pArg1, pArg2));
      }
      return result;
   }
   
   /**
    * Compute the difference of {@code pArg1} and {@code pArg2}.
    *
    * @param pArg1 The first value.
    * @param pArg2 The second value.
    * @return The result of the computation.
    * @throws ArithmeticException If the result overflows the {@code int} range.
    */
   public int subtract(final int pArg1, final int pArg2) {
      final int result = pArg1 - pArg2;
      if (((pArg1 ^ pArg2) & (pArg1 ^ result)) < 0) {
         throw new ArithmeticException(
               String.format("The result of '%d' minus '%d' overflows the int range.",
                     pArg1, pArg2));
      }
      return result;
   }
   
   /**
    * Compute the difference of {@code pArg1} and {@code pArg2}.
    *
    * @param pArg1 The first value.
    * @param pArg2 The second value.
    * @return The result of the computation.
    * @throws ArithmeticException If the result overflows the {@code long} range.
    */
   public long subtract(final long pArg1, final long pArg2) {
      final long result = pArg1 - pArg2;
      if (((pArg1 ^ pArg2) & (pArg1 ^ result)) < 0) {
         throw new ArithmeticException(
               String.format("The result of '%d' minus '%d' overflows the long range.",
                     pArg1, pArg2));
      }
      return result;
   }
   
   /**
    * Compute the product of {@code pArg1} and {@code pArg2}.
    *
    * @param pArg1 The first value.
    * @param pArg2 The second value.
    * @return The result of the computation.
    * @throws ArithmeticException If the result overflows the {@code int} range.
    */
   public int multiply(final int pArg1, final int pArg2) {
      final long result = (long) pArg1 * (long) pArg2;
      if ((int) result != result) {
         throw new ArithmeticException(
               String.format("The result of '%d' multiplied with '%d' overflows the int range.",
                     pArg1, pArg2));
      }
      return (int) result;
   }
   
   /**
    * Compute the product of {@code pArg1} and {@code pArg2}.
    *
    * @param pArg1 The first value.
    * @param pArg2 The second value.
    * @return The result of the computation.
    * @throws ArithmeticException If the result overflows the {@code long} range.
    */
   public long multiply(final long pArg1, final long pArg2) {
      return Math.multiplyExact(pArg1, pArg2);
   }
   
   /**
    * Compute the division of {@code pArg1} and {@code pArg2}.
    *
    * @param pArg1 The first value.
    * @param pArg2 The second value.
    * @return The result of the computation.
    */
   public int divide(final int pArg1, final int pArg2) {
      return pArg1 / pArg2;
   }
   
   /**
    * Compute the division of {@code pArg1} and {@code pArg2}.
    *
    * @param pArg1 The first value.
    * @param pArg2 The second value.
    * @return The result of the computation.
    */
   public long divide(final long pArg1, final long pArg2) {
      return pArg1 / pArg2;
   }
}
