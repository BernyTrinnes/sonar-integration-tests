package org.example.sit.rest.backend.dto;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * The DTO for {@code ResourceA}.
 */
@JsonRootName(value = "resource-a")
@JsonPropertyOrder({"id", "param1", "param2", "param3"})
@SuppressWarnings({"javadoc", "boxing"})
public class ResourceADto {
   @JsonProperty(value = "id", required = true)
   private long idA;
   @JsonProperty(value = "param1")
   private String paramA1;
   @JsonProperty(value = "param2")
   private Boolean paramA2;
   @JsonProperty(value = "param3")
   private List<Long> paramA3;
   
   private ResourceADto() {
      // Needed for Jackson de-/serialization
   }
   
   /**
    * Create a new DTO.
    *
    * @param pIdA The Id of this DTO.
    */
   public ResourceADto(final long pIdA) {
      this.idA = pIdA;
   }
   
   @Override
   public boolean equals(final Object pO) {
      if (this == pO) {
         return true;
      }
      if (pO == null || getClass() != pO.getClass()) {
         return false;
      }
      final ResourceADto that = (ResourceADto) pO;
      return (this.idA == that.idA) && Objects.equals(this.paramA1, that.paramA1) &&
            Objects.equals(this.paramA2, that.paramA2) &&
            Objects.equals(this.paramA3, that.paramA3);
   }
   
   @Override
   public int hashCode() {
      return Objects.hash(this.idA, this.paramA1, this.paramA2, this.paramA3);
   }
   
   @Override
   public String toString() {
      return "ResourceADto{" + "idA=" + this.idA + ", paramA1='" + this.paramA1 + '\'' +
            ", paramA2=" + this.paramA2 + ", paramA3=" + this.paramA3 + '}';
   }
   
   public long getIdA() {
      return this.idA;
   }
   
   public String getParamA1() {
      return this.paramA1;
   }
   
   public ResourceADto setParamA1(final String pParamA1) {
      this.paramA1 = pParamA1;
      return this;
   }
   
   public Boolean getParamA2() {
      return this.paramA2;
   }
   
   public ResourceADto setParamA2(final Boolean pParamA2) {
      this.paramA2 = pParamA2;
      return this;
   }
   
   public List<Long> getParamA3() {
      return this.paramA3;
   }
   
   public ResourceADto setParamA3(final List<Long> pParamA3) {
      this.paramA3 = pParamA3;
      return this;
   }
}
