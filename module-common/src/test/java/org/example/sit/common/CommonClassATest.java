package org.example.sit.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@code CommonClassA}.
 */
class CommonClassATest {
   private static final Offset<Double> OFFSET = Offset.offset(Double.valueOf(0.01));
   
   private CommonClassA classInstance;
   
   @BeforeEach
   public void setUp() {
      this.classInstance = new CommonClassA();
      assertNotNull(this.classInstance);
   }
   
   @Test
   void test_getSquareArea_SideLessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getSquareArea(CommonClassesConstantsFactory.SIDE_VALUE_MIN - 1));
   }
   
   @Test
   void test_getSquareArea_SideGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getSquareArea(CommonClassesConstantsFactory.SIDE_VALUE_MAX + 1));
   }
   
   @Test
   void test_getSquareArea_Success() {
      assertThat(this.classInstance.getSquareArea(10.0)).isEqualTo(100.0);
   }
   
   @Test
   void test_getSquareCircumference_SideLessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getSquareCircumference(CommonClassesConstantsFactory.SIDE_VALUE_MIN - 1));
   }
   
   @Test
   void test_getSquareCircumference_SideGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getSquareCircumference(CommonClassesConstantsFactory.SIDE_VALUE_MAX + 1));
   }
   
   @Test
   void test_getSquareCircumference_Success() {
      assertThat(this.classInstance.getSquareCircumference(20.0)).isEqualTo(80.0);
   }
   
   @Test
   void test_getRectangleArea_SideALessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getRectangleArea(CommonClassesConstantsFactory.SIDE_VALUE_MIN - 1,
                  CommonClassesConstantsFactory.SIDE_VALUE_MIN));
   }
   
   @Test
   void test_getRectangleArea_SideBLessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getRectangleArea(CommonClassesConstantsFactory.SIDE_VALUE_MIN,
                  CommonClassesConstantsFactory.SIDE_VALUE_MIN - 1));
   }
   
   @Test
   void test_getRectangleArea_SideAGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getRectangleArea(CommonClassesConstantsFactory.SIDE_VALUE_MAX + 1,
                  CommonClassesConstantsFactory.SIDE_VALUE_MAX));
   }
   
   @Test
   void test_getRectangleArea_SideBGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getRectangleArea(CommonClassesConstantsFactory.SIDE_VALUE_MAX,
                  CommonClassesConstantsFactory.SIDE_VALUE_MAX + 1));
   }
   
   @Test
   void test_getRectangleArea_Success() {
      assertThat(this.classInstance.getRectangleArea(30.0, 40.0)).isEqualTo(1_200.0);
   }
   
   @Test
   void test_getRectangleCircumference_SideALessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getRectangleCircumference(CommonClassesConstantsFactory.SIDE_VALUE_MIN - 1,
                  CommonClassesConstantsFactory.SIDE_VALUE_MIN));
   }
   
   @Test
   void test_getRectangleCircumference_SideBLessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getRectangleCircumference(CommonClassesConstantsFactory.SIDE_VALUE_MIN,
                  CommonClassesConstantsFactory.SIDE_VALUE_MIN - 1));
   }
   
   @Test
   void test_getRectangleCircumference_SideAGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getRectangleCircumference(CommonClassesConstantsFactory.SIDE_VALUE_MAX + 1,
                  CommonClassesConstantsFactory.SIDE_VALUE_MAX));
   }
   
   @Test
   void test_getRectangleCircumference_SideBGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getRectangleCircumference(CommonClassesConstantsFactory.SIDE_VALUE_MAX,
                  CommonClassesConstantsFactory.SIDE_VALUE_MAX + 1));
   }
   
   @Test
   void test_getRectangleCircumference_Success() {
      assertThat(this.classInstance.getRectangleCircumference(50.0, 60.0)).isEqualTo(220.0);
   }
   
   @Test
   void test_getTriangleArea_SideALessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getTriangleArea(CommonClassesConstantsFactory.SIDE_VALUE_MIN - 1,
                  CommonClassesConstantsFactory.SIDE_VALUE_MIN, CommonClassesConstantsFactory.SIDE_VALUE_MIN));
   }
   
   @Test
   void test_getTriangleArea_SideBLessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getTriangleArea(CommonClassesConstantsFactory.SIDE_VALUE_MIN,
                  CommonClassesConstantsFactory.SIDE_VALUE_MIN - 1, CommonClassesConstantsFactory.SIDE_VALUE_MIN));
   }
   
   @Test
   void test_getTriangleArea_SideCLessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getTriangleArea(CommonClassesConstantsFactory.SIDE_VALUE_MIN,
                  CommonClassesConstantsFactory.SIDE_VALUE_MIN, CommonClassesConstantsFactory.SIDE_VALUE_MIN - 1));
   }
   
   @Test
   void test_getTriangleArea_SideAGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getTriangleArea(CommonClassesConstantsFactory.SIDE_VALUE_MAX + 1,
                  CommonClassesConstantsFactory.SIDE_VALUE_MAX, CommonClassesConstantsFactory.SIDE_VALUE_MAX));
   }
   
   @Test
   void test_getTriangleArea_SideBGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getTriangleArea(CommonClassesConstantsFactory.SIDE_VALUE_MAX,
                  CommonClassesConstantsFactory.SIDE_VALUE_MAX + 1, CommonClassesConstantsFactory.SIDE_VALUE_MAX));
   }
   
   @Test
   void test_getTriangleArea_SideCGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getTriangleArea(CommonClassesConstantsFactory.SIDE_VALUE_MAX,
                  CommonClassesConstantsFactory.SIDE_VALUE_MAX, CommonClassesConstantsFactory.SIDE_VALUE_MAX + 1));
   }
   
   @Test
   void test_getTriangleArea_Success() {
      assertThat(this.classInstance.getTriangleArea(70.0, 80.0, 50.0)).isCloseTo(1_732.05, OFFSET);
   }
   
   @Test
   void test_getTriangleCircumference_SideALessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getTriangleCircumference(CommonClassesConstantsFactory.SIDE_VALUE_MIN - 1,
                  CommonClassesConstantsFactory.SIDE_VALUE_MIN, CommonClassesConstantsFactory.SIDE_VALUE_MIN));
   }
   
   @Test
   void test_getTriangleCircumference_SideBLessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getTriangleCircumference(CommonClassesConstantsFactory.SIDE_VALUE_MIN,
                  CommonClassesConstantsFactory.SIDE_VALUE_MIN - 1, CommonClassesConstantsFactory.SIDE_VALUE_MIN));
   }
   
   @Test
   void test_getTriangleCircumference_SideCLessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getTriangleCircumference(CommonClassesConstantsFactory.SIDE_VALUE_MIN,
                  CommonClassesConstantsFactory.SIDE_VALUE_MIN, CommonClassesConstantsFactory.SIDE_VALUE_MIN - 1));
   }
   
   @Test
   void test_getTriangleCircumference_SideAGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getTriangleCircumference(CommonClassesConstantsFactory.SIDE_VALUE_MAX + 1,
                  CommonClassesConstantsFactory.SIDE_VALUE_MAX, CommonClassesConstantsFactory.SIDE_VALUE_MAX));
   }
   
   @Test
   void test_getTriangleCircumference_SideBGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getTriangleCircumference(CommonClassesConstantsFactory.SIDE_VALUE_MAX,
                  CommonClassesConstantsFactory.SIDE_VALUE_MAX + 1, CommonClassesConstantsFactory.SIDE_VALUE_MAX));
   }
   
   @Test
   void test_getTriangleCircumference_SideCGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getTriangleCircumference(CommonClassesConstantsFactory.SIDE_VALUE_MAX,
                  CommonClassesConstantsFactory.SIDE_VALUE_MAX, CommonClassesConstantsFactory.SIDE_VALUE_MAX + 1));
   }
   
   @Test
   void test_getTriangleCircumference_Success() {
      assertThat(this.classInstance.getTriangleCircumference(90.0, 100.0, 70.0)).isEqualTo(260.0);
   }
   
   @Test
   void test_getCircleArea_RadiusLessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getCircleArea(CommonClassesConstantsFactory.RADIUS_VALUE_MIN - 1));
   }
   
   @Test
   void test_getCircleArea_RadiusGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getCircleArea(CommonClassesConstantsFactory.RADIUS_VALUE_MAX + 1));
   }
   
   @Test
   void test_getCircleArea_Success() {
      assertThat(this.classInstance.getCircleArea(25.0)).isCloseTo(1_963.49, OFFSET);
   }
   
   @Test
   void test_getCircleCircumference_RadiusLessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getCircleCircumference(CommonClassesConstantsFactory.RADIUS_VALUE_MIN - 1));
   }
   
   @Test
   void test_getCircleCircumference_SideGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getCircleCircumference(CommonClassesConstantsFactory.RADIUS_VALUE_MAX + 1));
   }
   
   @Test
   void test_getCircleCircumference_Success() {
      assertThat(this.classInstance.getCircleCircumference(35.0)).isCloseTo(219.91, OFFSET);
   }
}
