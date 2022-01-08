package org.example.sit.common;

/**
 * Abstract class containing common constants.
 */
public abstract class CommonClassesConstantsFactory {
   /**
    * Hide the constructor, since class is not meant to be instantiated.
    */
   private CommonClassesConstantsFactory() {
   }
   
   /**
    * The minimum allowed value for the side of a geometric object.
    */
   public static final double SIDE_VALUE_MIN = 1.0;
   /**
    * The maximum allowed value for the side of a geometric object.
    */
   public static final double SIDE_VALUE_MAX = 1_000.0;
   /**
    * The minimum allowed value for the radius of a circle.
    */
   public static final double RADIUS_VALUE_MIN = 1.0;
   /**
    * The maximum allowed value for the side of a geometric object.
    */
   public static final double RADIUS_VALUE_MAX = 100.0;
}
