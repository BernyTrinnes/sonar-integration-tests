package org.example.sit.common;

/**
 * The code in this class will be tested using integration tests in {@code module-integration-tests}.
 */
public class CommonClassD {
   /**
    * Initialize this class.
    */
   public CommonClassD() {
      // Nothing to do
   }
   
   /**
    * Compute the surface of a cube.
    *
    * @param pSide The value of the cube's side.
    * @return The surface of a cube with the given side.
    * @throws IllegalArgumentException If the value of the square's side is less than
    * <code>{@value CommonClassesConstantsFactory#SIDE_VALUE_MIN}</code> or greater than
    * <code>{@value CommonClassesConstantsFactory#SIDE_VALUE_MAX}</code>.
    */
   public double getCubeSurface(final double pSide) {
      validateSideValue(pSide);
      
      return 2 * 3 * pSide * pSide;
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
    * Compute the volume of a cube.
    *
    * @param pSide The value of the cube's side.
    * @return The volume of a cube with the given side.
    * @throws IllegalArgumentException If the value of the cube's side is less than
    * <code>{@value CommonClassesConstantsFactory#SIDE_VALUE_MIN}</code> or greater than
    * <code>{@value CommonClassesConstantsFactory#SIDE_VALUE_MAX}</code>.
    */
   public double getCubeVolume(final double pSide) {
      validateSideValue(pSide);
      
      return Math.pow(pSide, 3);
   }
   
   /**
    * Compute the surface of a cuboid.
    *
    * @param pSideA The value of the first cuboid side.
    * @param pSideB The value of the second cuboid side.
    * @param pSideC The value of the third cuboid side.
    * @return The surface of a cuboid with the given sides.
    * @throws IllegalArgumentException If one of the values of the cuboids sides is less than
    * <code>{@value CommonClassesConstantsFactory#SIDE_VALUE_MIN}</code> or greater than
    * <code>{@value CommonClassesConstantsFactory#SIDE_VALUE_MAX}</code>.
    */
   public double getCuboidSurface(final double pSideA, final double pSideB, final double pSideC) {
      validateSideValue(pSideA);
      validateSideValue(pSideB);
      validateSideValue(pSideC);
      
      return 2 * (pSideA * pSideB + pSideA * pSideC + pSideB * pSideC);
   }
   
   /**
    * Compute the volume of a cuboid.
    *
    * @param pSideA The value of the first cuboid side.
    * @param pSideB The value of the second cuboid side.
    * @param pSideC The value of the third cuboid side.
    * @return The volume of a cuboid with the given sides.
    * @throws IllegalArgumentException If one of the values of the cuboids sides is less than
    * <code>{@value CommonClassesConstantsFactory#SIDE_VALUE_MIN}</code> or greater than
    * <code>{@value CommonClassesConstantsFactory#SIDE_VALUE_MAX}</code>.
    */
   public double getCuboidVolume(final double pSideA, final double pSideB, final double pSideC) {
      validateSideValue(pSideA);
      validateSideValue(pSideB);
      validateSideValue(pSideC);
      
      return pSideA * pSideB * pSideC;
   }
   
   /**
    * Compute the surface of a pyramid.
    *
    * @param pSide The value of the pyramid's base side.
    * @param pHeight The value of the pyramid's height.
    * @return The surface of a pyramid with the given base side and height.
    * @throws IllegalArgumentException If the value of the pyramid's base side or the pyramid's height is less than
    * <code>{@value CommonClassesConstantsFactory#SIDE_VALUE_MIN}</code> or greater than
    * <code>{@value CommonClassesConstantsFactory#SIDE_VALUE_MAX}</code>.
    */
   public double getPyramidSurface(final double pSide, final double pHeight) {
      validateSideValue(pSide);
      validateSideValue(pHeight);
      
      final double areaBase = pSide * pSide;
      final double heightA = Math.sqrt(pHeight * pHeight + areaBase / 4);
      final double lateralShell = 2 * pSide * heightA;
      return areaBase + lateralShell;
   }
   
   /**
    * Compute the volume of a pyramid.
    *
    * @param pSide The value of the pyramid's base side.
    * @param pHeight The value of the pyramid's height.
    * @return The volume of a pyramid with the given base side and height.
    * @throws IllegalArgumentException If the value of the pyramid's base side or the pyramid's height is less than
    * <code>{@value CommonClassesConstantsFactory#SIDE_VALUE_MIN}</code> or greater than
    * <code>{@value CommonClassesConstantsFactory#SIDE_VALUE_MAX}</code>.
    */
   public double getPyramidVolume(final double pSide, final double pHeight) {
      validateSideValue(pSide);
      validateSideValue(pHeight);
      
      final double areaBase = pSide * pSide;
      return areaBase * pHeight / 3;
   }
   
   /**
    * Compute the surface of a sphere.
    *
    * @param pRadius The value of the sphere's radius.
    * @return The surface of a sphere with the given radius.
    * @throws IllegalArgumentException If the value of the sphere's radius is less than
    * <code>{@value CommonClassesConstantsFactory#RADIUS_VALUE_MIN}</code> or greater than
    * <code>{@value CommonClassesConstantsFactory#RADIUS_VALUE_MAX}</code>.
    */
   public double getSphereSurface(final double pRadius) {
      validateRadiusValue(pRadius);
      
      return 4 * pRadius * pRadius * Math.PI;
   }
   
   private void validateRadiusValue(final double pRadius) {
      if ((pRadius < CommonClassesConstantsFactory.RADIUS_VALUE_MIN) ||
            (pRadius > CommonClassesConstantsFactory.RADIUS_VALUE_MAX)) {
         throw new IllegalArgumentException(
               String.format("The value for the radius of a sphere must be between %f and %f.",
                     CommonClassesConstantsFactory.RADIUS_VALUE_MIN, CommonClassesConstantsFactory.RADIUS_VALUE_MAX));
      }
   }
   
   /**
    * Compute the volume of a sphere.
    *
    * @param pRadius The value of the sphere's radius.
    * @return The volume of a sphere with the given radius.
    * @throws IllegalArgumentException If the value of the sphere's radius is less than
    * <code>{@value CommonClassesConstantsFactory#RADIUS_VALUE_MIN}</code> or greater than
    * <code>{@value CommonClassesConstantsFactory#RADIUS_VALUE_MAX}</code>.
    */
   public double getSphereVolume(final double pRadius) {
      validateRadiusValue(pRadius);
      
      return 4 * Math.pow(pRadius, 3) * Math.PI / 3;
   }
}
