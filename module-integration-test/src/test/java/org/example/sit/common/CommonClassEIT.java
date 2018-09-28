package org.example.sit.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@code CommonClassE}.
 */
@SuppressWarnings("javadoc")
public class CommonClassEIT {
   private CommonClassE classInstance;
   
   @Before
   public void setUp() {
      this.classInstance = new CommonClassE();
      assertNotNull(this.classInstance);
   }
   
   @Test
   public void test_min_float_Success() {
      assertThat(this.classInstance.min(0.0f, 0.0f)).isEqualTo(0.0f);
      assertThat(this.classInstance.min(0.0f, -0.0f)).isEqualTo(0.0f);
      assertThat(this.classInstance.min(-0.0f, 0.0f)).isEqualTo(-0.0f);
      assertThat(this.classInstance.min(-0.0f, -0.0f)).isEqualTo(-0.0f);
      
      final float arg1 = RandomUtils.nextFloat(0.0f, Float.MAX_VALUE / 2.0f);
      assertThat(this.classInstance.min(Float.NaN, arg1)).isEqualTo(Float.NaN);
      assertThat(this.classInstance.min(arg1, Float.NaN)).isEqualTo(Float.NaN);
      assertThat(this.classInstance.min(arg1, arg1)).isEqualTo(arg1);
      
      final float arg2 = RandomUtils.nextFloat(arg1 + 1, Float.MAX_VALUE);
      assertThat(this.classInstance.min(arg1, arg2)).isEqualTo(arg1);
      assertThat(this.classInstance.min(arg2, arg1)).isEqualTo(arg1);
      assertThat(this.classInstance.min(arg1, -1 * arg2)).isEqualTo(-1 * arg2);
   }
   
   @Test
   public void test_min_double_Success() {
      assertThat(this.classInstance.min(0.0d, 0.0d)).isEqualTo(0.0d);
      assertThat(this.classInstance.min(0.0d, -0.0d)).isEqualTo(0.0d);
      assertThat(this.classInstance.min(-0.0d, 0.0d)).isEqualTo(-0.0d);
      assertThat(this.classInstance.min(-0.0d, -0.0d)).isEqualTo(-0.0d);
      
      final double arg1 = RandomUtils.nextDouble(0.0d, Double.MAX_VALUE / 2.0d);
      assertThat(this.classInstance.min(Double.NaN, arg1)).isEqualTo(Double.NaN);
      assertThat(this.classInstance.min(arg1, Double.NaN)).isEqualTo(Double.NaN);
      assertThat(this.classInstance.min(arg1, arg1)).isEqualTo(arg1);
      
      final double arg2 = RandomUtils.nextDouble(arg1 + 1, Double.MAX_VALUE);
      assertThat(this.classInstance.min(arg1, arg2)).isEqualTo(arg1);
      assertThat(this.classInstance.min(arg2, arg1)).isEqualTo(arg1);
      assertThat(this.classInstance.min(arg1, -1 * arg2)).isEqualTo(-1 * arg2);
   }
}
