package org.example.sit.common;

/**
 * The code in this class will be tested using unit tests in {@code module-common} and {@code module-backend/rest-impl},
 * and integration tests in module {@code module-integration-tests}.
 *
 * <ul>
 *    <li>
 *       Unit tests in {@code module module-common}:
 *       <ul>
 *          <li>{@code max(int, int)}</li>
 *          <li>{@code max(long, long)}</li>
 *       </ul>
 *    </li>
 *    <li>
 *       Unit tests in module {@code module-backend/rest-impl}:
 *       <ul>
 *          <li>{@code max(float, float)}</li>
 *          <li>{@code max(double, double)}</li>
 *       </ul>
 *    </li>
 *    <li>
 *       Unit tests in {@code module module-frontend/rest-impl}:
 *       <ul>
 *          <li>{@code min(int, int)}</li>
 *          <li>{@code min(long, long)}</li>
 *       </ul>
 *    </li>
 *    <li>
 *       Integration tests in module {@code module-integration-tests}:
 *       <ul>
 *          <li>{@code min(float, float)}</li>
 *          <li>{@code min(double, double)}</li>
 *       </ul>
 *    </li>
 * </ul>
 */
public class CommonClassE {
   /**
    * Initialize this class.
    */
   public CommonClassE() {
      // Nothing to do
   }
   
   /**
    * Return the greater of two {@code int} values.
    *
    * @param pArg1 The first {@code int} value.
    * @param pArg2 The second {@code int} value.
    * @return The greater value of {@code pArg1} and {@code pArg2}.
    */
   public int max(final int pArg1, final int pArg2) {
      return pArg1 >= pArg2 ? pArg1 : pArg2;
   }
   
   /**
    * Return the greater of two {@code long} values.
    *
    * @param pArg1 The first {@code long} value.
    * @param pArg2 The second {@code long} value.
    * @return The greater value of {@code pArg1} and {@code pArg2}.
    */
   public long max(final long pArg1, final long pArg2) {
      return pArg1 >= pArg2 ? pArg1 : pArg2;
   }
   
   /**
    * Return the greater of two {@code float} values.
    *
    * @param pArg1 The first {@code float} value.
    * @param pArg2 The second {@code float} value.
    * @return The greater value of {@code pArg1} and {@code pArg2}.
    */
   public float max(final float pArg1, final float pArg2) {
      if (pArg1 != pArg1) { // NOSONAR
         // pArg1 is NaN
         return pArg1;
      }
      return pArg1 >= pArg2 ? pArg1 : pArg2;
   }
   
   /**
    * Return the greater of two {@code double} values.
    *
    * @param pArg1 The first {@code double} value.
    * @param pArg2 The second {@code double} value.
    * @return The greater value of {@code pArg1} and {@code pArg2}.
    */
   public double max(final double pArg1, final double pArg2) {
      if (pArg1 != pArg1) { // NOSONAR
         // pArg1 is NaN
         return pArg1;
      }
      return pArg1 >= pArg2 ? pArg1 : pArg2;
   }
   
   /**
    * Returns the smaller of two {@code int} values.
    *
    * @param pArg1 The first {@code int} value.
    * @param pArg2 The second {@code int} value.
    * @return The smaller value of {@code pArg1} and {@code pArg2}.
    */
   public int min(final int pArg1, final int pArg2) {
      return pArg1 <= pArg2 ? pArg1 : pArg2;
   }
   
   /**
    * Returns the smaller of two {@code long} values.
    *
    * @param pArg1 The first {@code long} value.
    * @param pArg2 The second {@code long} value.
    * @return The smaller value of {@code pArg1} and {@code pArg2}.
    */
   public long min(final long pArg1, final long pArg2) {
      return pArg1 <= pArg2 ? pArg1 : pArg2;
   }
   
   /**
    * Return the smaller of two {@code float} values.
    *
    * @param pArg1 The first {@code float} value.
    * @param pArg2 The second {@code float} value.
    * @return The smaller value of {@code pArg1} and {@code pArg2}.
    */
   public float min(final float pArg1, final float pArg2) {
      if (pArg1 != pArg1) { // NOSONAR
         // pArg1 is NaN
         return pArg1;
      }
      return pArg1 <= pArg2 ? pArg1 : pArg2;
   }
   
   /**
    * Return the smaller of two {@code double} values.
    *
    * @param pArg1 The first {@code double} value.
    * @param pArg2 The second {@code double} value.
    * @return The smaller value of {@code pArg1} and {@code pArg2}.
    */
   public double min(final double pArg1, final double pArg2) {
      if (pArg1 != pArg1) { // NOSONAR
         // pArg1 is NaN
         return pArg1;
      }
      return pArg1 <= pArg2 ? pArg1 : pArg2;
   }
}
