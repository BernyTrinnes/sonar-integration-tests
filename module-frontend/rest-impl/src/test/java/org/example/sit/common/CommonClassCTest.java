package org.example.sit.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@code CommonClassC} from {@code module-common}.
 */
@SuppressWarnings("javadoc")
public class CommonClassCTest {
   private CommonClassC classInstance;
   
   @Before
   public void setUp() {
      this.classInstance = new CommonClassC();
      assertNotNull(this.classInstance);
   }
   
   @Test
   public void test_isStringEmpty() {
      assertThat(this.classInstance.isStringEmpty(null)).isTrue();
      assertThat(this.classInstance.isStringEmpty("")).isTrue();
      assertThat(this.classInstance.isStringEmpty(" ")).isFalse();
      assertThat(this.classInstance.isStringEmpty(" \t\r\n")).isFalse();
      assertThat(this.classInstance.isStringEmpty("test")).isFalse();
      assertThat(this.classInstance.isStringEmpty(" test ")).isFalse();
   }
   
   @Test
   public void test_isStringNotEmpty() {
      assertThat(this.classInstance.isStringNotEmpty(null)).isFalse();
      assertThat(this.classInstance.isStringNotEmpty("")).isFalse();
      assertThat(this.classInstance.isStringNotEmpty(" ")).isTrue();
      assertThat(this.classInstance.isStringNotEmpty(" \t\r\n")).isTrue();
      assertThat(this.classInstance.isStringNotEmpty("test")).isTrue();
      assertThat(this.classInstance.isStringNotEmpty(" test ")).isTrue();
   }
   
   @Test
   public void test_isStringBlank() {
      assertThat(this.classInstance.isStringBlank(null)).isTrue();
      assertThat(this.classInstance.isStringBlank("")).isTrue();
      assertThat(this.classInstance.isStringBlank(" ")).isTrue();
      assertThat(this.classInstance.isStringBlank(" \t\r\n")).isTrue();
      assertThat(this.classInstance.isStringBlank("test")).isFalse();
      assertThat(this.classInstance.isStringBlank(" test ")).isFalse();
   }
   
   @Test
   public void test_isStringNotBlank() {
      assertThat(this.classInstance.isStringNotBlank(null)).isFalse();
      assertThat(this.classInstance.isStringNotBlank("")).isFalse();
      assertThat(this.classInstance.isStringNotBlank(" ")).isFalse();
      assertThat(this.classInstance.isStringNotBlank(" \t\r\n")).isFalse();
      assertThat(this.classInstance.isStringNotBlank("test")).isTrue();
      assertThat(this.classInstance.isStringNotBlank(" test ")).isTrue();
   }
}
