package org.example.sit.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@code CommonClassD}.
 */
class CommonClassDIT {
   private static final Offset<Double> OFFSET = Offset.offset(Double.valueOf(0.01));
   
   private CommonClassD classInstance;
   
   @BeforeEach
   public void setUp() {
      this.classInstance = new CommonClassD();
      
      assertNotNull(this.classInstance);
   }
   
   @Test
   void test_getCubeSurface_SideLessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getCubeSurface(CommonClassesConstantsFactory.SIDE_VALUE_MIN - 1));
   }
   
   @Test
   void test_getCubeSurface_SideGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getCubeSurface(CommonClassesConstantsFactory.SIDE_VALUE_MAX + 1));
   }
   
   @Test
   void test_getCubeSurface_Success() {
      assertThat(this.classInstance.getCubeSurface(10.0)).isEqualTo(600.0);
   }
   
   @Test
   void test_getCubeVolume_SideLessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getCubeVolume(CommonClassesConstantsFactory.SIDE_VALUE_MIN - 1));
   }
   
   @Test
   void test_getCubeVolume_SideGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getCubeVolume(CommonClassesConstantsFactory.SIDE_VALUE_MAX + 1));
   }
   
   @Test
   void test_getCubeVolume_Success() {
      assertThat(this.classInstance.getCubeVolume(20.0)).isEqualTo(8_000.0);
   }
   
   @Test
   void test_getCuboidSurface_SideALessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getCuboidSurface(CommonClassesConstantsFactory.SIDE_VALUE_MIN - 1,
                  CommonClassesConstantsFactory.SIDE_VALUE_MIN, CommonClassesConstantsFactory.SIDE_VALUE_MIN));
   }
   
   @Test
   void test_getCuboidSurface_SideBLessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getCuboidSurface(CommonClassesConstantsFactory.SIDE_VALUE_MIN,
                  CommonClassesConstantsFactory.SIDE_VALUE_MIN - 1, CommonClassesConstantsFactory.SIDE_VALUE_MIN));
   }
   
   @Test
   void test_getCuboidSurface_SideCLessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getCuboidSurface(CommonClassesConstantsFactory.SIDE_VALUE_MIN,
                  CommonClassesConstantsFactory.SIDE_VALUE_MIN, CommonClassesConstantsFactory.SIDE_VALUE_MIN - 1));
   }
   
   @Test
   void test_getCuboidSurface_SideAGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getCuboidSurface(CommonClassesConstantsFactory.SIDE_VALUE_MAX + 1,
                  CommonClassesConstantsFactory.SIDE_VALUE_MAX, CommonClassesConstantsFactory.SIDE_VALUE_MAX));
   }
   
   @Test
   void test_getCuboidSurface_SideBGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getCuboidSurface(CommonClassesConstantsFactory.SIDE_VALUE_MAX,
                  CommonClassesConstantsFactory.SIDE_VALUE_MAX + 1, CommonClassesConstantsFactory.SIDE_VALUE_MAX));
   }
   
   @Test
   void test_getCuboidSurface_SideCGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getCuboidSurface(CommonClassesConstantsFactory.SIDE_VALUE_MAX,
                  CommonClassesConstantsFactory.SIDE_VALUE_MAX, CommonClassesConstantsFactory.SIDE_VALUE_MAX + 1));
   }
   
   @Test
   void test_getCuboidSurface_Success() {
      assertThat(this.classInstance.getCuboidSurface(30.0, 40.0, 50.0)).isEqualTo(9_400.0);
   }
   
   @Test
   void test_getCuboidVolume_SideALessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getCuboidVolume(CommonClassesConstantsFactory.SIDE_VALUE_MIN - 1,
                  CommonClassesConstantsFactory.SIDE_VALUE_MIN, CommonClassesConstantsFactory.SIDE_VALUE_MIN));
   }
   
   @Test
   void test_getCuboidVolume_SideBLessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getCuboidVolume(CommonClassesConstantsFactory.SIDE_VALUE_MIN,
                  CommonClassesConstantsFactory.SIDE_VALUE_MIN - 1, CommonClassesConstantsFactory.SIDE_VALUE_MIN));
   }
   
   @Test
   void test_getCuboidVolume_SideCLessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getCuboidVolume(CommonClassesConstantsFactory.SIDE_VALUE_MIN,
                  CommonClassesConstantsFactory.SIDE_VALUE_MIN, CommonClassesConstantsFactory.SIDE_VALUE_MIN - 1));
   }
   
   @Test
   void test_getCuboidVolume_SideAGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getCuboidVolume(CommonClassesConstantsFactory.SIDE_VALUE_MAX + 1,
                  CommonClassesConstantsFactory.SIDE_VALUE_MAX, CommonClassesConstantsFactory.SIDE_VALUE_MAX));
   }
   
   @Test
   void test_getCuboidVolume_SideBGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getCuboidVolume(CommonClassesConstantsFactory.SIDE_VALUE_MAX,
                  CommonClassesConstantsFactory.SIDE_VALUE_MAX + 1, CommonClassesConstantsFactory.SIDE_VALUE_MAX));
   }
   
   @Test
   void test_getCuboidVolume_SideCGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getCuboidVolume(CommonClassesConstantsFactory.SIDE_VALUE_MAX,
                  CommonClassesConstantsFactory.SIDE_VALUE_MAX, CommonClassesConstantsFactory.SIDE_VALUE_MAX + 1));
   }
   
   @Test
   void test_getCuboidVolume_Success() {
      assertThat(this.classInstance.getCuboidVolume(60.0, 70.0, 80.0)).isEqualTo(336_000.0);
   }
   
   @Test
   void test_getPyramidSurface_SideLessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getPyramidSurface(CommonClassesConstantsFactory.SIDE_VALUE_MIN - 1,
                  CommonClassesConstantsFactory.SIDE_VALUE_MIN));
   }
   
   @Test
   void test_getPyramidSurface_HeightLessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getPyramidSurface(CommonClassesConstantsFactory.SIDE_VALUE_MIN,
                  CommonClassesConstantsFactory.SIDE_VALUE_MIN - 1));
   }
   
   @Test
   void test_getPyramidSurface_SideGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getPyramidSurface(CommonClassesConstantsFactory.SIDE_VALUE_MAX + 1,
                  CommonClassesConstantsFactory.SIDE_VALUE_MAX));
   }
   
   @Test
   void test_getPyramidSurface_HeightGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getPyramidSurface(CommonClassesConstantsFactory.SIDE_VALUE_MAX,
                  CommonClassesConstantsFactory.SIDE_VALUE_MAX + 1));
   }
   
   @Test
   void test_getPyramidSurface_Success() {
      assertThat(this.classInstance.getPyramidSurface(90.0, 100.0)).isCloseTo(27_838.54, OFFSET);
   }
   
   @Test
   void test_getPyramidVolume_SideLessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getPyramidVolume(CommonClassesConstantsFactory.SIDE_VALUE_MIN - 1,
                  CommonClassesConstantsFactory.SIDE_VALUE_MIN));
   }
   
   @Test
   void test_getPyramidVolume_HeightLessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getPyramidVolume(CommonClassesConstantsFactory.SIDE_VALUE_MIN,
                  CommonClassesConstantsFactory.SIDE_VALUE_MIN - 1));
   }
   
   @Test
   void test_getPyramidVolume_SideGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getPyramidVolume(CommonClassesConstantsFactory.SIDE_VALUE_MAX + 1,
                  CommonClassesConstantsFactory.SIDE_VALUE_MAX));
   }
   
   @Test
   void test_getPyramidVolume_HeightGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getPyramidVolume(CommonClassesConstantsFactory.SIDE_VALUE_MAX,
                  CommonClassesConstantsFactory.SIDE_VALUE_MAX + 1));
   }
   
   @Test
   void test_getPyramidVolume_Success() {
      assertThat(this.classInstance.getPyramidVolume(110.0, 120.0)).isEqualTo(484_000.0);
   }
   
   @Test
   void test_getSphereSurface_RadiusLessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getSphereSurface(CommonClassesConstantsFactory.RADIUS_VALUE_MIN - 1));
   }
   
   @Test
   void test_getSphereSurface_RadiusGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getSphereSurface(CommonClassesConstantsFactory.RADIUS_VALUE_MAX + 1));
   }
   
   @Test
   void test_getSphereSurface_Success() {
      assertThat(this.classInstance.getSphereSurface(45.0)).isCloseTo(25_446.90, OFFSET);
   }
   
   @Test
   void test_getSphereVolume_RadiusLessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getSphereVolume(CommonClassesConstantsFactory.RADIUS_VALUE_MIN - 1));
   }
   
   @Test
   void test_getSphereVolume_SideGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getSphereVolume(CommonClassesConstantsFactory.RADIUS_VALUE_MAX + 1));
   }
   
   @Test
   void test_getSphereVolume_Success() {
      assertThat(this.classInstance.getSphereVolume(55.0)).isCloseTo(696_909.97, OFFSET);
   }
}
