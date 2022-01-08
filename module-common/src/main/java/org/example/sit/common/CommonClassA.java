package org.example.sit.common;

/**
 * The code in this class will be tested using unit tests in {@code module-common}.
 */
public class CommonClassA {
   /**
    * Initialize this class.
    */
   public CommonClassA() {
      // Nothing to do
   }
   
   /**
    * Calculate the area of a square.
    *
    * @param pSide The value of the square's side.
    * @return The area of a square with the given side.
    * @throws IllegalArgumentException If the value of the square's side is less than
    * <code>{@value CommonClassesConstantsFactory#SIDE_VALUE_MIN}</code> or greater than
    * <code>{@value CommonClassesConstantsFactory#SIDE_VALUE_MAX}</code>.
    */
   public double getSquareArea(final double pSide) {
      validateSideValue(pSide);
      
      return pSide * pSide;
   }
   
   private void validateSideValue(final double pSide) {
      if ((pSide < CommonClassesConstantsFactory.SIDE_VALUE_MIN) ||
            (pSide > CommonClassesConstantsFactory.SIDE_VALUE_MAX)) {
         throw new IllegalArgumentException(String.format(
               "The value for the side of a geometric object must be between %f and %f.",
               CommonClassesConstantsFactory.SIDE_VALUE_MIN, CommonClassesConstantsFactory.SIDE_VALUE_MAX));
      }
   }
   
   /**
    * Calculate the circumference of a square.
    *
    * @param pSide The value of the square's side.
    * @return The circumference of a square with the given side.
    * @throws IllegalArgumentException If the value of the square's side is less than
    * <code>{@value CommonClassesConstantsFactory#SIDE_VALUE_MIN}</code> or greater than
    * <code>{@value CommonClassesConstantsFactory#SIDE_VALUE_MAX}</code>.
    */
   public double getSquareCircumference(final double pSide) {
      validateSideValue(pSide);
      
      return 4 * pSide;
   }
   
   /**
    * Calculate the area of a rectangle.
    *
    * @param pSideA The value of the first rectangle side.
    * @param pSideB The value of the second rectangle side.
    * @return The area of a rectangle with the given sides.
    * @throws IllegalArgumentException If one of the values of the rectangle's sides is less than
    * <code>{@value CommonClassesConstantsFactory#SIDE_VALUE_MIN}</code> or greater than
    * <code>{@value CommonClassesConstantsFactory#SIDE_VALUE_MAX}</code>.
    */
   public double getRectangleArea(final double pSideA, final double pSideB) {
      validateSideValue(pSideA);
      validateSideValue(pSideB);
      
      return pSideA * pSideB;
   }
   
   /**
    * Calculate the circumference of a rectangle.
    *
    * @param pSideA The value of the first rectangle side.
    * @param pSideB The value of the second rectangle side.
    * @return The circumference of a rectangle with the given sides.
    * @throws IllegalArgumentException If one of the values of the rectangle's sides is less than
    * <code>{@value CommonClassesConstantsFactory#SIDE_VALUE_MIN}</code> or greater than
    * <code>{@value CommonClassesConstantsFactory#SIDE_VALUE_MAX}</code>.
    */
   public double getRectangleCircumference(final double pSideA, final double pSideB) {
      validateSideValue(pSideA);
      validateSideValue(pSideB);
      
      return 2 * (pSideA + pSideB);
   }
   
   /**
    * Calculate the area of a triangle.
    *
    * @param pSideA The value of the first triangle side.
    * @param pSideB The value of the second triangle side.
    * @param pSideC The value of the third triangle side.
    * @return The area of a triangle with the given sides.
    * @throws IllegalArgumentException If one of the values of the triangle's sides is less than
    * <code>{@value CommonClassesConstantsFactory#SIDE_VALUE_MIN}</code> or greater than
    * <code>{@value CommonClassesConstantsFactory#SIDE_VALUE_MAX}</code>.
    */
   public double getTriangleArea(final double pSideA, final double pSideB, final double pSideC) {
      validateSideValue(pSideA);
      validateSideValue(pSideB);
      validateSideValue(pSideC);
      
      final double halfCircumference = getTriangleCircumference(pSideA, pSideB, pSideC) / 2.0;
      return Math.sqrt(halfCircumference * (halfCircumference - pSideA) * (halfCircumference - pSideB) *
            (halfCircumference - pSideC));
   }
   
   /**
    * Calculate the circumference of a rectangle.
    *
    * @param pSideA The value of the first triangle side.
    * @param pSideB The value of the second triangle side.
    * @param pSideC The value of the third triangle side.
    * @return The circumference of a triangle with the given sides.
    * @throws IllegalArgumentException If one of the values of the triangle's sides is less than
    * <code>{@value CommonClassesConstantsFactory#SIDE_VALUE_MIN}</code> or greater than
    * <code>{@value CommonClassesConstantsFactory#SIDE_VALUE_MAX}</code>.
    */
   public double getTriangleCircumference(final double pSideA, final double pSideB, final double pSideC) {
      validateSideValue(pSideA);
      validateSideValue(pSideB);
      validateSideValue(pSideC);
      
      return pSideA + pSideB + pSideC;
   }
   
   /**
    * Calculate the area of a circle.
    *
    * @param pRadius The value of the circle's radius.
    * @return The area of a circle with the given radius.
    * @throws IllegalArgumentException If the value of the circle's radius is less than
    * <code>{@value CommonClassesConstantsFactory#RADIUS_VALUE_MIN}</code> or greater than
    * <code>{@value CommonClassesConstantsFactory#RADIUS_VALUE_MAX}</code>.
    */
   public double getCircleArea(final double pRadius) {
      validateRadiusValue(pRadius);
      
      return pRadius * pRadius * Math.PI;
   }
   
   private void validateRadiusValue(final double pRadius) {
      if ((pRadius < CommonClassesConstantsFactory.RADIUS_VALUE_MIN) ||
            (pRadius > CommonClassesConstantsFactory.RADIUS_VALUE_MAX)) {
         throw new IllegalArgumentException(String.format(
               "The value for the radius of a circle must be between %f and %f.",
               CommonClassesConstantsFactory.RADIUS_VALUE_MIN, CommonClassesConstantsFactory.RADIUS_VALUE_MAX));
      }
   }
   
   /**
    * Calculate the circumference of a circle.
    *
    * @param pRadius The value of the circle's radius.
    * @return The circumference of a circle with the given radius.
    * @throws IllegalArgumentException If the value of the circle's radius is less than
    * <code>{@value CommonClassesConstantsFactory#RADIUS_VALUE_MIN}</code> or greater than
    * <code>{@value CommonClassesConstantsFactory#RADIUS_VALUE_MAX}</code>.
    */
   public double getCircleCircumference(final double pRadius) {
      validateRadiusValue(pRadius);
      
      return 2 * pRadius * Math.PI;
   }
}
