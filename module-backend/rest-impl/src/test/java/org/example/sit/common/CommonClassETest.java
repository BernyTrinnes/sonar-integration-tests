package org.example.sit.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@code CommonClassE} from {@code module-common}.
 */
class CommonClassETest {
   private CommonClassE classInstance;
   
   @BeforeEach
   public void setUp() {
      this.classInstance = new CommonClassE();
      assertNotNull(this.classInstance);
   }
   
   @Test
   void test_max_float_Success() {
      assertThat(this.classInstance.max(0.0f, 0.0f)).isEqualTo(0.0f);
      assertThat(this.classInstance.max(0.0f, -0.0f)).isEqualTo(0.0f);
      assertThat(this.classInstance.max(-0.0f, 0.0f)).isEqualTo(-0.0f);
      assertThat(this.classInstance.max(-0.0f, -0.0f)).isEqualTo(-0.0f);
      
      final float arg1 = RandomUtils.nextFloat(0.0f, Float.MAX_VALUE / 2.0f);
      assertThat(this.classInstance.max(Float.NaN, arg1)).isNaN();
      assertThat(this.classInstance.max(arg1, Float.NaN)).isNaN();
      assertThat(this.classInstance.max(arg1, arg1)).isEqualTo(arg1);
      
      final float arg2 = RandomUtils.nextFloat(arg1 + 1, Float.MAX_VALUE);
      assertThat(this.classInstance.max(arg1, arg2)).isEqualTo(arg2);
      assertThat(this.classInstance.max(arg2, arg1)).isEqualTo(arg2);
      assertThat(this.classInstance.max(arg1, -1 * arg2)).isEqualTo(arg1);
   }
   
   @Test
   void test_max_double_Success() {
      assertThat(this.classInstance.max(0.0d, 0.0d)).isEqualTo(0.0d);
      assertThat(this.classInstance.max(0.0d, -0.0d)).isEqualTo(0.0d);
      assertThat(this.classInstance.max(-0.0d, 0.0d)).isEqualTo(-0.0d);
      assertThat(this.classInstance.max(-0.0d, -0.0d)).isEqualTo(-0.0d);
      
      final double arg1 = RandomUtils.nextDouble(0.0d, Double.MAX_VALUE / 2.0d);
      assertThat(this.classInstance.max(Double.NaN, arg1)).isNaN();
      assertThat(this.classInstance.max(arg1, Double.NaN)).isNaN();
      assertThat(this.classInstance.max(arg1, arg1)).isEqualTo(arg1);
      
      final double arg2 = RandomUtils.nextDouble(arg1 + 1, Double.MAX_VALUE);
      assertThat(this.classInstance.max(arg1, arg2)).isEqualTo(arg2);
      assertThat(this.classInstance.max(arg2, arg1)).isEqualTo(arg2);
      assertThat(this.classInstance.max(arg1, -1 * arg2)).isEqualTo(arg1);
   }
}
