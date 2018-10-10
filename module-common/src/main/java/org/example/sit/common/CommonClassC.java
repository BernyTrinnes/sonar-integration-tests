package org.example.sit.common;

import java.util.stream.Collectors;

/**
 * The code in this class will be tested using unit tests in {@code module-frontend/rest-impl}.
 */
public class CommonClassC {
   /**
    * Initializer.
    */
   public CommonClassC() {
      // Nothing to do
   }
   
   /**
    * Check if a string is empty ({@code ""}) or {@code null}.
    *
    * @param pString The string to check, may be {@code null}.
    * @return {@code true} if the string is empty ({@code ""}) or {@code null}, {@code false}
    * otherwise.
    */
   public boolean isStringEmpty(final String pString) {
      return (null == pString) || (0 == pString.length());
   }
   
   /**
    * Check if a string is not empty ({@code ""}) and not {@code null}.
    *
    * @param pString The string to check, may be {@code null}.
    * @return {@code true} if the string is not empty ({@code ""}) and not {@code null}.
    */
   public boolean isStringNotEmpty(final String pString) {
      return !isStringEmpty(pString);
   }
   
   /**
    * Check if a string is empty ({@code ""}), {@code null} or contains only whitespaces
    * (defined by {@link Character#isWhitespace(char)}).
    *
    * @param pString The string to check, may be {@code null}.
    * @return {@code true} if the string is empty ({@code ""}), {@code null} or contains only
    * whitespaces, {@code false} otherwise.
    */
   public boolean isStringBlank(final String pString) {
      final int strLen = (null == pString) ? 0 : pString.length();
      if (0 == strLen) {
         return true;
      }
      final String newString = pString.codePoints().filter(c -> !Character.isWhitespace(c))
            .mapToObj(c -> Character.toString((char) c)).collect(Collectors.joining());
      return 0 == newString.length();
   }
   
   /**
    * Check if a string is not empty ({@code ""}), not {@code null} and contains not only
    * whitespaces (defined by {@link Character#isWhitespace(char)}).
    *
    * @param pString The string to check, may be {@code null}.
    * @return {@code true} if the string is not empty ({@code ""}) and not {@code null} and contains
    * not only whitespaces.
    */
   public boolean isStringNotBlank(final String pString) {
      return !isStringBlank(pString);
   }
}
