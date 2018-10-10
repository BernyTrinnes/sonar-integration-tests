package org.example.sit.rest.frontend.dto;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * The DTO for {@code ResourceE}.
 */
@JsonRootName(value = "resource-e")
@JsonPropertyOrder({"id", "param1", "param2", "param3", "param4", "param5", "param6"})
@SuppressWarnings({"javadoc", "boxing"})
public class ResourceEDto {
   @JsonProperty(value = "id", required = true)
   private long idE;
   @JsonProperty(value = "param1")
   private Integer paramE1;
   @JsonProperty(value = "param2")
   private String paramE2;
   @JsonProperty(value = "param3")
   private String paramE3;
   @JsonProperty(value = "param4")
   private Long paramE4;
   @JsonProperty(value = "param5")
   private Boolean paramE5;
   @JsonProperty(value = "param6")
   private List<Integer> paramE6;
   
   private ResourceEDto() {
      // Needed for Jackson de-/serialization
   }
   
   /**
    * Create a new DTO.
    *
    * @param pIdE The Id of this DTO.
    */
   public ResourceEDto(final long pIdE) {
      this.idE = pIdE;
   }
   
   @Override
   public boolean equals(final Object pO) {
      if (this == pO) {
         return true;
      }
      if ((null == pO) || (getClass() != pO.getClass())) {
         return false;
      }
      final ResourceEDto that = (ResourceEDto) pO;
      return (this.idE == that.idE) && Objects.equals(this.paramE1, that.paramE1) &&
            Objects.equals(this.paramE2, that.paramE2) &&
            Objects.equals(this.paramE3, that.paramE3) &&
            Objects.equals(this.paramE4, that.paramE4) &&
            Objects.equals(this.paramE5, that.paramE5) &&
            Objects.equals(this.paramE6, that.paramE6);
   }
   
   @Override
   public int hashCode() {
      return Objects.hash(this.idE, this.paramE1, this.paramE2, this.paramE3, this.paramE4,
            this.paramE5, this.paramE6);
   }
   
   @Override
   public String toString() {
      return "ResourceEDto{" + "idE=" + this.idE + ", paramE1=" + this.paramE1 + ", paramE2='" +
            this.paramE2 + '\'' + ", paramE3='" + this.paramE3 + '\'' + ", paramE4=" +
            this.paramE4 + ", paramE5=" + this.paramE5 + ", paramE6=" + this.paramE6 + '}';
   }
   
   public long getIdE() {
      return this.idE;
   }
   
   public ResourceEDto setIdE(final long pIdE) {
      this.idE = pIdE;
      return this;
   }
   
   public Integer getParamE1() {
      return this.paramE1;
   }
   
   public ResourceEDto setParamE1(final Integer pParamE1) {
      this.paramE1 = pParamE1;
      return this;
   }
   
   public String getParamE2() {
      return this.paramE2;
   }
   
   public ResourceEDto setParamE2(final String pParamE2) {
      this.paramE2 = pParamE2;
      return this;
   }
   
   public String getParamE3() {
      return this.paramE3;
   }
   
   public ResourceEDto setParamE3(final String pParamE3) {
      this.paramE3 = pParamE3;
      return this;
   }
   
   public Long getParamE4() {
      return this.paramE4;
   }
   
   public ResourceEDto setParamE4(final Long pParamE4) {
      this.paramE4 = pParamE4;
      return this;
   }
   
   public Boolean getParamE5() {
      return this.paramE5;
   }
   
   public ResourceEDto setParamE5(final Boolean pParamE5) {
      this.paramE5 = pParamE5;
      return this;
   }
   
   public List<Integer> getParamE6() {
      return this.paramE6;
   }
   
   public ResourceEDto setParamE6(final List<Integer> pParamE6) {
      this.paramE6 = pParamE6;
      return this;
   }
}
