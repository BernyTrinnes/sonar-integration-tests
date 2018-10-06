package org.example.sit.rest.backend.dto;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * The DTO for {@code ResourceC}.
 */
@JsonRootName(value = "resource-c")
@JsonPropertyOrder({"id", "param1", "param2", "param3", "param4", "param5", "param6", "param7"})
@SuppressWarnings({"javadoc", "boxing"})
public class ResourceCDto {
   @JsonProperty(value = "id", required = true)
   private long idC;
   @JsonProperty(value = "param1")
   private String paramC1;
   @JsonProperty(value = "param2")
   private Integer paramC2;
   @JsonProperty(value = "param3")
   private String paramC3;
   @JsonProperty(value = "param4")
   private String paramC4;
   @JsonProperty(value = "param5")
   private Boolean paramC5;
   @JsonProperty(value = "param6")
   private Long paramC6;
   @JsonProperty(value = "param7")
   private List<String> paramC7;
   
   private ResourceCDto() {
      // Needed for Jackson de-/serialization
   }
   
   /**
    * Create a new DTO.
    *
    * @param pIdC The Id of this DTO.
    */
   public ResourceCDto(final long pIdC) {
      this.idC = pIdC;
   }
   
   @Override
   public boolean equals(final Object pO) {
      if (this == pO) {
         return true;
      }
      if ((null == pO) || (getClass() != pO.getClass())) {
         return false;
      }
      final ResourceCDto that = (ResourceCDto) pO;
      return (this.idC == that.idC) && Objects.equals(this.paramC1, that.paramC1) &&
            Objects.equals(this.paramC2, that.paramC2) &&
            Objects.equals(this.paramC3, that.paramC3) &&
            Objects.equals(this.paramC4, that.paramC4) &&
            Objects.equals(this.paramC5, that.paramC5) &&
            Objects.equals(this.paramC6, that.paramC6) &&
            Objects.equals(this.paramC7, that.paramC7);
   }
   
   @Override
   public int hashCode() {
      return Objects.hash(this.idC, this.paramC1, this.paramC2, this.paramC3, this.paramC4,
            this.paramC5, this.paramC6, this.paramC7);
   }
   
   @Override
   public String toString() {
      return "ResourceCDto{" + "idC=" + this.idC + ", paramC1='" + this.paramC1 + '\'' +
            ", paramC2=" + this.paramC2 + ", paramC3='" + this.paramC3 + '\'' + ", paramC4='" +
            this.paramC4 + '\'' + ", paramC5=" + this.paramC5 + ", paramC6=" + this.paramC6 +
            ", paramC7=" + this.paramC7 + '}';
   }
   
   public long getIdC() {
      return this.idC;
   }
   
   public ResourceCDto setIdC(final long pIdC) {
      this.idC = pIdC;
      return this;
   }
   
   public String getParamC1() {
      return this.paramC1;
   }
   
   public ResourceCDto setParamC1(final String pParamC1) {
      this.paramC1 = pParamC1;
      return this;
   }
   
   public Integer getParamC2() {
      return this.paramC2;
   }
   
   public ResourceCDto setParamC2(final Integer pParamC2) {
      this.paramC2 = pParamC2;
      return this;
   }
   
   public String getParamC3() {
      return this.paramC3;
   }
   
   public ResourceCDto setParamC3(final String pParamC3) {
      this.paramC3 = pParamC3;
      return this;
   }
   
   public String getParamC4() {
      return this.paramC4;
   }
   
   public ResourceCDto setParamC4(final String pParamC4) {
      this.paramC4 = pParamC4;
      return this;
   }
   
   public Boolean getParamC5() {
      return this.paramC5;
   }
   
   public ResourceCDto setParamC5(final Boolean pParamC5) {
      this.paramC5 = pParamC5;
      return this;
   }
   
   public Long getParamC6() {
      return this.paramC6;
   }
   
   public ResourceCDto setParamC6(final Long pParamC6) {
      this.paramC6 = pParamC6;
      return this;
   }
   
   public List<String> getParamC7() {
      return this.paramC7;
   }
   
   public ResourceCDto setParamC7(final List<String> pParamC7) {
      this.paramC7 = pParamC7;
      return this;
   }
}
