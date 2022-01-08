package org.example.sit.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@code CommonClassE}.
 */
class CommonClassETest {
   private CommonClassE classInstance;
   
   @BeforeEach
   public void setUp() {
      this.classInstance = new CommonClassE();
      assertNotNull(this.classInstance);
   }
   
   @Test
   void test_max_int_Success() {
      final int arg1 = RandomUtils.nextInt(0, Integer.MAX_VALUE / 2);
      assertThat(this.classInstance.max(arg1, arg1)).isEqualTo(arg1);
      
      final int arg2 = RandomUtils.nextInt(arg1 + 1, Integer.MAX_VALUE);
      assertThat(this.classInstance.max(arg1, arg2)).isEqualTo(arg2);
      assertThat(this.classInstance.max(arg2, arg1)).isEqualTo(arg2);
      assertThat(this.classInstance.max(arg1, -1 * arg2)).isEqualTo(arg1);
   }
   
   @Test
   void test_max_long_Success() {
      final long arg1 = RandomUtils.nextLong(0, Long.MAX_VALUE / 2);
      assertThat(this.classInstance.max(arg1, arg1)).isEqualTo(arg1);
      
      final long arg2 = RandomUtils.nextLong(arg1 + 1, Long.MAX_VALUE);
      assertThat(this.classInstance.max(arg1, arg2)).isEqualTo(arg2);
      assertThat(this.classInstance.max(arg2, arg1)).isEqualTo(arg2);
      assertThat(this.classInstance.max(arg1, -1 * arg2)).isEqualTo(arg1);
   }
}
