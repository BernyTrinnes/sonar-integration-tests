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
public class CommonClassETest {
   private CommonClassE classInstance;
   
   @Before
   public void setUp() {
      this.classInstance = new CommonClassE();
      assertNotNull(this.classInstance);
   }
   
   @Test
   public void test_max_int_Success() {
      final int arg1 = RandomUtils.nextInt(0, Integer.MAX_VALUE / 2);
      assertThat(this.classInstance.max(arg1, arg1)).isEqualTo(arg1);
      
      final int arg2 = RandomUtils.nextInt(arg1 + 1, Integer.MAX_VALUE);
      assertThat(this.classInstance.max(arg1, arg2)).isEqualTo(arg2);
      assertThat(this.classInstance.max(arg2, arg1)).isEqualTo(arg2);
      assertThat(this.classInstance.max(arg1, -1 * arg2)).isEqualTo(arg1);
   }
   
   @Test
   public void test_max_long_Success() {
      final long arg1 = RandomUtils.nextLong(0, Long.MAX_VALUE / 2);
      assertThat(this.classInstance.max(arg1, arg1)).isEqualTo(arg1);
      
      final long arg2 = RandomUtils.nextLong(arg1 + 1, Long.MAX_VALUE);
      assertThat(this.classInstance.max(arg1, arg2)).isEqualTo(arg2);
      assertThat(this.classInstance.max(arg2, arg1)).isEqualTo(arg2);
      assertThat(this.classInstance.max(arg1, -1 * arg2)).isEqualTo(arg1);
   }
}
