package org.example.sit.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@code CommonClassC} from {@code module-common}.
 */
class CommonClassCTest {
   private CommonClassC classInstance;
   
   @BeforeEach
   public void setUp() {
      this.classInstance = new CommonClassC();
      assertNotNull(this.classInstance);
   }
   
   @Test
   void test_isStringEmpty() {
      assertThat(this.classInstance.isStringEmpty(null)).isTrue();
      assertThat(this.classInstance.isStringEmpty("")).isTrue();
      assertThat(this.classInstance.isStringEmpty(" ")).isFalse();
      assertThat(this.classInstance.isStringEmpty(" \t\r\n")).isFalse();
      assertThat(this.classInstance.isStringEmpty("test")).isFalse();
      assertThat(this.classInstance.isStringEmpty(" test ")).isFalse();
   }
   
   @Test
   void test_isStringNotEmpty() {
      assertThat(this.classInstance.isStringNotEmpty(null)).isFalse();
      assertThat(this.classInstance.isStringNotEmpty("")).isFalse();
      assertThat(this.classInstance.isStringNotEmpty(" ")).isTrue();
      assertThat(this.classInstance.isStringNotEmpty(" \t\r\n")).isTrue();
      assertThat(this.classInstance.isStringNotEmpty("test")).isTrue();
      assertThat(this.classInstance.isStringNotEmpty(" test ")).isTrue();
   }
   
   @Test
   void test_isStringBlank() {
      assertThat(this.classInstance.isStringBlank(null)).isTrue();
      assertThat(this.classInstance.isStringBlank("")).isTrue();
      assertThat(this.classInstance.isStringBlank(" ")).isTrue();
      assertThat(this.classInstance.isStringBlank(" \t\r\n")).isTrue();
      assertThat(this.classInstance.isStringBlank("test")).isFalse();
      assertThat(this.classInstance.isStringBlank(" test ")).isFalse();
   }
   
   @Test
   void test_isStringNotBlank() {
      assertThat(this.classInstance.isStringNotBlank(null)).isFalse();
      assertThat(this.classInstance.isStringNotBlank("")).isFalse();
      assertThat(this.classInstance.isStringNotBlank(" ")).isFalse();
      assertThat(this.classInstance.isStringNotBlank(" \t\r\n")).isFalse();
      assertThat(this.classInstance.isStringNotBlank("test")).isTrue();
      assertThat(this.classInstance.isStringNotBlank(" test ")).isTrue();
   }
}
