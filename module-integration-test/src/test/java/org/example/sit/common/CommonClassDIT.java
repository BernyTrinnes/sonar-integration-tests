package org.example.sit.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.Assert.assertNotNull;

import org.assertj.core.data.Offset;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@code CommonClassD}.
 */
@SuppressWarnings("javadoc")
public class CommonClassDIT {
   private static final Offset<Double> OFFSET = Offset.offset(Double.valueOf(0.01));
   
   private CommonClassD classInstance;
   
   @Before
   public void setUp() {
      this.classInstance = new CommonClassD();
      assertNotNull(this.classInstance);
   }
   
   @Test
   public void test_getCubeSurface_SideLessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getCubeSurface(CommonClassA.SIDE_VALUE_MIN - 1));
   }
   
   @Test
   public void test_getCubeSurface_SideGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getCubeSurface(CommonClassA.SIDE_VALUE_MAX + 1));
   }
   
   @Test
   public void test_getCubeSurface_Success() {
      assertThat(this.classInstance.getCubeSurface(10.0)).isEqualTo(600.0);
   }
   
   @Test
   public void test_getCubeVolume_SideLessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getCubeVolume(CommonClassA.SIDE_VALUE_MIN - 1));
   }
   
   @Test
   public void test_getCubeVolume_SideGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getCubeVolume(CommonClassA.SIDE_VALUE_MAX + 1));
   }
   
   @Test
   public void test_getCubeVolume_Success() {
      assertThat(this.classInstance.getCubeVolume(20.0)).isEqualTo(8_000.0);
   }
   
   @Test
   public void test_getCuboidSurface_SideALessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getCuboidSurface(CommonClassA.SIDE_VALUE_MIN - 1, CommonClassA.SIDE_VALUE_MIN,
                  CommonClassA.SIDE_VALUE_MIN));
   }
   
   @Test
   public void test_getCuboidSurface_SideBLessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getCuboidSurface(CommonClassA.SIDE_VALUE_MIN, CommonClassA.SIDE_VALUE_MIN - 1,
                  CommonClassA.SIDE_VALUE_MIN));
   }
   
   @Test
   public void test_getCuboidSurface_SideCLessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getCuboidSurface(CommonClassA.SIDE_VALUE_MIN, CommonClassA.SIDE_VALUE_MIN,
                  CommonClassA.SIDE_VALUE_MIN - 1));
   }
   
   @Test
   public void test_getCuboidSurface_SideAGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getCuboidSurface(CommonClassA.SIDE_VALUE_MAX + 1, CommonClassA.SIDE_VALUE_MAX,
                  CommonClassA.SIDE_VALUE_MAX));
   }
   
   @Test
   public void test_getCuboidSurface_SideBGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getCuboidSurface(CommonClassA.SIDE_VALUE_MAX, CommonClassA.SIDE_VALUE_MAX + 1,
                  CommonClassA.SIDE_VALUE_MAX));
   }
   
   @Test
   public void test_getCuboidSurface_SideCGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getCuboidSurface(CommonClassA.SIDE_VALUE_MAX, CommonClassA.SIDE_VALUE_MAX,
                  CommonClassA.SIDE_VALUE_MAX + 1));
   }
   
   @Test
   public void test_getCuboidSurface_Success() {
      assertThat(this.classInstance.getCuboidSurface(30.0, 40.0, 50.0)).isEqualTo(9_400.0);
   }
   
   @Test
   public void test_getCuboidVolume_SideALessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getCuboidVolume(CommonClassA.SIDE_VALUE_MIN - 1, CommonClassA.SIDE_VALUE_MIN,
                  CommonClassA.SIDE_VALUE_MIN));
   }
   
   @Test
   public void test_getCuboidVolume_SideBLessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getCuboidVolume(CommonClassA.SIDE_VALUE_MIN, CommonClassA.SIDE_VALUE_MIN - 1,
                  CommonClassA.SIDE_VALUE_MIN));
   }
   
   @Test
   public void test_getCuboidVolume_SideCLessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getCuboidVolume(CommonClassA.SIDE_VALUE_MIN, CommonClassA.SIDE_VALUE_MIN,
                  CommonClassA.SIDE_VALUE_MIN - 1));
   }
   
   @Test
   public void test_getCuboidVolume_SideAGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getCuboidVolume(CommonClassA.SIDE_VALUE_MAX + 1, CommonClassA.SIDE_VALUE_MAX,
                  CommonClassA.SIDE_VALUE_MAX));
   }
   
   @Test
   public void test_getCuboidVolume_SideBGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getCuboidVolume(CommonClassA.SIDE_VALUE_MAX, CommonClassA.SIDE_VALUE_MAX + 1,
                  CommonClassA.SIDE_VALUE_MAX));
   }
   
   @Test
   public void test_getCuboidVolume_SideCGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getCuboidVolume(CommonClassA.SIDE_VALUE_MAX, CommonClassA.SIDE_VALUE_MAX,
                  CommonClassA.SIDE_VALUE_MAX + 1));
   }
   
   @Test
   public void test_getCuboidVolume_Success() {
      assertThat(this.classInstance.getCuboidVolume(60.0, 70.0, 80.0)).isEqualTo(336_000.0);
   }
   
   @Test
   public void test_getPyramidSurface_SideLessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getPyramidSurface(CommonClassA.SIDE_VALUE_MIN - 1, CommonClassA.SIDE_VALUE_MIN));
   }
   
   @Test
   public void test_getPyramidSurface_HeightLessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getPyramidSurface(CommonClassA.SIDE_VALUE_MIN, CommonClassA.SIDE_VALUE_MIN - 1));
   }
   
   @Test
   public void test_getPyramidSurface_SideGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getPyramidSurface(CommonClassA.SIDE_VALUE_MAX + 1, CommonClassA.SIDE_VALUE_MAX));
   }
   
   @Test
   public void test_getPyramidSurface_HeightGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getPyramidSurface(CommonClassA.SIDE_VALUE_MAX, CommonClassA.SIDE_VALUE_MAX + 1));
   }
   
   @Test
   public void test_getPyramidSurface_Success() {
      assertThat(this.classInstance.getPyramidSurface(90.0, 100.0)).isCloseTo(27_838.54, OFFSET);
   }
   
   @Test
   public void test_getPyramidVolume_SideLessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getPyramidVolume(CommonClassA.SIDE_VALUE_MIN - 1, CommonClassA.SIDE_VALUE_MIN));
   }
   
   @Test
   public void test_getPyramidVolume_HeightLessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getPyramidVolume(CommonClassA.SIDE_VALUE_MIN, CommonClassA.SIDE_VALUE_MIN - 1));
   }
   
   @Test
   public void test_getPyramidVolume_SideGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getPyramidVolume(CommonClassA.SIDE_VALUE_MAX + 1, CommonClassA.SIDE_VALUE_MAX));
   }
   
   @Test
   public void test_getPyramidVolume_HeightGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(() -> this.classInstance
            .getPyramidVolume(CommonClassA.SIDE_VALUE_MAX, CommonClassA.SIDE_VALUE_MAX + 1));
   }
   
   @Test
   public void test_getPyramidVolume_Success() {
      assertThat(this.classInstance.getPyramidVolume(110.0, 120.0)).isEqualTo(484_000.0);
   }
   
   @Test
   public void test_getSphereSurface_RadiusLessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getSphereSurface(CommonClassA.RADIUS_VALUE_MIN - 1));
   }
   
   @Test
   public void test_getSphereSurface_RadiusGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getSphereSurface(CommonClassA.RADIUS_VALUE_MAX + 1));
   }
   
   @Test
   public void test_getSphereSurface_Success() {
      assertThat(this.classInstance.getSphereSurface(45.0)).isCloseTo(25_446.90, OFFSET);
   }
   
   @Test
   public void test_getSphereVolume_RadiusLessThanMinimum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getSphereVolume(CommonClassA.RADIUS_VALUE_MIN - 1));
   }
   
   @Test
   public void test_getSphereVolume_SideGreaterThanMaximum() {
      assertThatIllegalArgumentException().isThrownBy(
            () -> this.classInstance.getSphereVolume(CommonClassA.RADIUS_VALUE_MAX + 1));
   }
   
   @Test
   public void test_getSphereVolume_Success() {
      assertThat(this.classInstance.getSphereVolume(55.0)).isCloseTo(696_909.97, OFFSET);
   }
}
