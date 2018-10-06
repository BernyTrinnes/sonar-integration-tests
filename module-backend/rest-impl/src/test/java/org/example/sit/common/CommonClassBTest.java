package org.example.sit.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@code CommonClassB} from {@code module-common}.
 */
@SuppressWarnings("javadoc")
public class CommonClassBTest {
   private CommonClassB classInstance;
   
   @Before
   public void setUp() {
      this.classInstance = new CommonClassB();
      assertNotNull(this.classInstance);
   }
   
   @Test
   public void test_add_int_ArithmeticException() {
      assertThatExceptionOfType(ArithmeticException.class).isThrownBy(
            () -> this.classInstance.add(Integer.MAX_VALUE, 2));
      assertThatExceptionOfType(ArithmeticException.class).isThrownBy(
            () -> this.classInstance.add(Integer.MIN_VALUE, -2));
      assertThatExceptionOfType(ArithmeticException.class).isThrownBy(
            () -> this.classInstance.add(2, Integer.MAX_VALUE));
      assertThatExceptionOfType(ArithmeticException.class).isThrownBy(
            () -> this.classInstance.add(-2, Integer.MIN_VALUE));
   }
   
   @Test
   public void test_add_int_Success() {
      assertThat(this.classInstance.add(1, 2)).isEqualTo(3);
      assertThat(this.classInstance.add(1, -2)).isEqualTo(-1);
      assertThat(this.classInstance.add(-1, 2)).isEqualTo(1);
      assertThat(this.classInstance.add(-1, -2)).isEqualTo(-3);
      assertThat(this.classInstance.add(2, 1)).isEqualTo(3);
      assertThat(this.classInstance.add(2, -1)).isEqualTo(1);
      assertThat(this.classInstance.add(-2, 1)).isEqualTo(-1);
      assertThat(this.classInstance.add(-2, -1)).isEqualTo(-3);
   }
   
   @Test
   public void test_add_long_ArithmeticException() {
      assertThatExceptionOfType(ArithmeticException.class).isThrownBy(
            () -> this.classInstance.add(Long.MAX_VALUE, 2L));
      assertThatExceptionOfType(ArithmeticException.class).isThrownBy(
            () -> this.classInstance.add(Long.MIN_VALUE, -2L));
      assertThatExceptionOfType(ArithmeticException.class).isThrownBy(
            () -> this.classInstance.add(2L, Long.MAX_VALUE));
      assertThatExceptionOfType(ArithmeticException.class).isThrownBy(
            () -> this.classInstance.add(-2L, Long.MIN_VALUE));
   }
   
   @Test
   public void test_add_long_Success() {
      assertThat(this.classInstance.add(3L, 4L)).isEqualTo(7L);
      assertThat(this.classInstance.add(3L, -4L)).isEqualTo(-1L);
      assertThat(this.classInstance.add(-3L, 4L)).isEqualTo(1L);
      assertThat(this.classInstance.add(-3L, -4L)).isEqualTo(-7L);
      assertThat(this.classInstance.add(4L, 3L)).isEqualTo(7L);
      assertThat(this.classInstance.add(4L, -3L)).isEqualTo(1L);
      assertThat(this.classInstance.add(-4L, 3L)).isEqualTo(-1L);
      assertThat(this.classInstance.add(-4L, -3L)).isEqualTo(-7L);
   }
   
   @Test
   public void test_subtract_int_ArithmeticException() {
      assertThatExceptionOfType(ArithmeticException.class).isThrownBy(
            () -> this.classInstance.subtract(Integer.MIN_VALUE, 2));
      assertThatExceptionOfType(ArithmeticException.class).isThrownBy(
            () -> this.classInstance.subtract(-2, Integer.MAX_VALUE));
   }
   
   @Test
   public void test_subtract_int_Success() {
      assertThat(this.classInstance.subtract(5, 6)).isEqualTo(-1);
      assertThat(this.classInstance.subtract(5, -6)).isEqualTo(11);
      assertThat(this.classInstance.subtract(-5, 6)).isEqualTo(-11);
      assertThat(this.classInstance.subtract(-5, -6)).isEqualTo(1);
      assertThat(this.classInstance.subtract(6, 5)).isEqualTo(1);
      assertThat(this.classInstance.subtract(6, -5)).isEqualTo(11);
      assertThat(this.classInstance.subtract(-6, 5)).isEqualTo(-11);
      assertThat(this.classInstance.subtract(-6, -5)).isEqualTo(-1);
   }
   
   @Test
   public void test_subtract_long_ArithmeticException() {
      assertThatExceptionOfType(ArithmeticException.class).isThrownBy(
            () -> this.classInstance.subtract(Long.MIN_VALUE, 2L));
      assertThatExceptionOfType(ArithmeticException.class).isThrownBy(
            () -> this.classInstance.subtract(-2L, Long.MAX_VALUE));
   }
   
   @Test
   public void test_subtract_long_Success() {
      assertThat(this.classInstance.subtract(7L, 8L)).isEqualTo(-1L);
      assertThat(this.classInstance.subtract(7L, -8L)).isEqualTo(15L);
      assertThat(this.classInstance.subtract(-7L, 8L)).isEqualTo(-15L);
      assertThat(this.classInstance.subtract(-7L, -8L)).isEqualTo(1L);
      assertThat(this.classInstance.subtract(8L, 7L)).isEqualTo(1L);
      assertThat(this.classInstance.subtract(8L, -7L)).isEqualTo(15L);
      assertThat(this.classInstance.subtract(-8L, 7L)).isEqualTo(-15L);
      assertThat(this.classInstance.subtract(-8L, -7L)).isEqualTo(-1L);
   }
   
   @Test
   public void test_multiply_int_ArithmeticException() {
      assertThatExceptionOfType(ArithmeticException.class).isThrownBy(
            () -> this.classInstance.multiply(Integer.MAX_VALUE, 2));
      assertThatExceptionOfType(ArithmeticException.class).isThrownBy(
            () -> this.classInstance.multiply(Integer.MIN_VALUE, -2));
      assertThatExceptionOfType(ArithmeticException.class).isThrownBy(
            () -> this.classInstance.multiply(2, Integer.MAX_VALUE));
      assertThatExceptionOfType(ArithmeticException.class).isThrownBy(
            () -> this.classInstance.multiply(-2, Integer.MIN_VALUE));
   }
   
   @Test
   public void test_multiply_int_Success() {
      assertThat(this.classInstance.multiply(9, 10)).isEqualTo(90);
      assertThat(this.classInstance.multiply(9, -10)).isEqualTo(-90);
      assertThat(this.classInstance.multiply(-9, 10)).isEqualTo(-90);
      assertThat(this.classInstance.multiply(-9, -10)).isEqualTo(90);
      assertThat(this.classInstance.multiply(10, 9)).isEqualTo(90);
      assertThat(this.classInstance.multiply(10, -9)).isEqualTo(-90);
      assertThat(this.classInstance.multiply(-10, 9)).isEqualTo(-90);
      assertThat(this.classInstance.multiply(-10, -9)).isEqualTo(90);
   }
   
   @Test
   public void test_multiply_long_ArithmeticException() {
      assertThatExceptionOfType(ArithmeticException.class).isThrownBy(
            () -> this.classInstance.multiply(Long.MAX_VALUE, 2L));
      assertThatExceptionOfType(ArithmeticException.class).isThrownBy(
            () -> this.classInstance.multiply(Long.MIN_VALUE, -2L));
      assertThatExceptionOfType(ArithmeticException.class).isThrownBy(
            () -> this.classInstance.multiply(Long.MIN_VALUE, -1L));
      assertThatExceptionOfType(ArithmeticException.class).isThrownBy(
            () -> this.classInstance.multiply(2L, Long.MAX_VALUE));
      assertThatExceptionOfType(ArithmeticException.class).isThrownBy(
            () -> this.classInstance.multiply(-2L, Long.MIN_VALUE));
   }
   
   @Test
   public void test_multiply_long_Success() {
      assertThat(this.classInstance.multiply(11L, 12L)).isEqualTo(132L);
      assertThat(this.classInstance.multiply(11L, -12L)).isEqualTo(-132L);
      assertThat(this.classInstance.multiply(-11L, 12L)).isEqualTo(-132L);
      assertThat(this.classInstance.multiply(-11L, -12L)).isEqualTo(132L);
      assertThat(this.classInstance.multiply(12L, 11L)).isEqualTo(132L);
      assertThat(this.classInstance.multiply(12L, -11L)).isEqualTo(-132L);
      assertThat(this.classInstance.multiply(-12L, 11L)).isEqualTo(-132L);
      assertThat(this.classInstance.multiply(-12L, -11L)).isEqualTo(132L);
      assertThat(this.classInstance.multiply(0L, 1L)).isEqualTo(0L);
   }
   
   @Test
   public void test_divide_int_Success() {
      assertThat(this.classInstance.divide(13, 14)).isEqualTo(0);
      assertThat(this.classInstance.divide(13, -14)).isEqualTo(0);
      assertThat(this.classInstance.divide(-13, 14)).isEqualTo(0);
      assertThat(this.classInstance.divide(-13, -14)).isEqualTo(0);
      assertThat(this.classInstance.divide(26, 13)).isEqualTo(2);
      assertThat(this.classInstance.divide(26, -13)).isEqualTo(-2);
      assertThat(this.classInstance.divide(-26, 13)).isEqualTo(-2);
      assertThat(this.classInstance.divide(-26, -13)).isEqualTo(2);
   }
   
   @Test
   public void test_divide_long_Success() {
      assertThat(this.classInstance.divide(11L, 12L)).isEqualTo(0L);
      assertThat(this.classInstance.divide(11L, -12L)).isEqualTo(0L);
      assertThat(this.classInstance.divide(-11L, 12L)).isEqualTo(0L);
      assertThat(this.classInstance.divide(-11L, -12L)).isEqualTo(0L);
      assertThat(this.classInstance.divide(30L, 15L)).isEqualTo(2L);
      assertThat(this.classInstance.divide(30L, -15L)).isEqualTo(-2L);
      assertThat(this.classInstance.divide(-30L, 15L)).isEqualTo(-2L);
      assertThat(this.classInstance.divide(-30L, -15L)).isEqualTo(2L);
   }
}
