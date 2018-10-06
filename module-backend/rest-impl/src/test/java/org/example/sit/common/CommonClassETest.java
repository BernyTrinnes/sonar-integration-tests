package org.example.sit.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@code CommonClassE} from {@code module-common}.
 */
@SuppressWarnings("javadoc")
public class CommonClassETest {
   private CommonClassE classInstance;
   
   @Before
   public void setUp() {
      this.classInstance = new CommonClassE();
      assertNotNull(this.classInstance);
   }
   
   @Test
   public void test_max_float_Success() {
      assertThat(this.classInstance.max(0.0f, 0.0f)).isEqualTo(0.0f);
      assertThat(this.classInstance.max(0.0f, -0.0f)).isEqualTo(0.0f);
      assertThat(this.classInstance.max(-0.0f, 0.0f)).isEqualTo(-0.0f);
      assertThat(this.classInstance.max(-0.0f, -0.0f)).isEqualTo(-0.0f);
      
      final float arg1 = RandomUtils.nextFloat(0.0f, Float.MAX_VALUE / 2.0f);
      assertThat(this.classInstance.max(Float.NaN, arg1)).isEqualTo(Float.NaN);
      assertThat(this.classInstance.max(arg1, Float.NaN)).isEqualTo(Float.NaN);
      assertThat(this.classInstance.max(arg1, arg1)).isEqualTo(arg1);
      
      final float arg2 = RandomUtils.nextFloat(arg1 + 1, Float.MAX_VALUE);
      assertThat(this.classInstance.max(arg1, arg2)).isEqualTo(arg2);
      assertThat(this.classInstance.max(arg2, arg1)).isEqualTo(arg2);
      assertThat(this.classInstance.max(arg1, -1 * arg2)).isEqualTo(arg1);
   }
   
   @Test
   public void test_max_double_Success() {
      assertThat(this.classInstance.max(0.0d, 0.0d)).isEqualTo(0.0d);
      assertThat(this.classInstance.max(0.0d, -0.0d)).isEqualTo(0.0d);
      assertThat(this.classInstance.max(-0.0d, 0.0d)).isEqualTo(-0.0d);
      assertThat(this.classInstance.max(-0.0d, -0.0d)).isEqualTo(-0.0d);
      
      final double arg1 = RandomUtils.nextDouble(0.0d, Double.MAX_VALUE / 2.0d);
      assertThat(this.classInstance.max(Double.NaN, arg1)).isEqualTo(Double.NaN);
      assertThat(this.classInstance.max(arg1, Double.NaN)).isEqualTo(Double.NaN);
      assertThat(this.classInstance.max(arg1, arg1)).isEqualTo(arg1);
      
      final double arg2 = RandomUtils.nextDouble(arg1 + 1, Double.MAX_VALUE);
      assertThat(this.classInstance.max(arg1, arg2)).isEqualTo(arg2);
      assertThat(this.classInstance.max(arg2, arg1)).isEqualTo(arg2);
      assertThat(this.classInstance.max(arg1, -1 * arg2)).isEqualTo(arg1);
   }
}
