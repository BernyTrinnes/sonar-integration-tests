package org.example.sit.rest.frontend.dto;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * The DTO for {@code ResourceD}.
 */
@JsonRootName(value = "resource-d")
@JsonPropertyOrder({"id", "param1", "param2", "param3", "param4"})
@SuppressWarnings({"javadoc", "boxing"})
public class ResourceDDto {
   @JsonProperty(value = "id", required = true)
   private long idD;
   @JsonProperty(value = "param1")
   private String paramD1;
   @JsonProperty(value = "param2")
   private Integer paramD2;
   @JsonProperty(value = "param3")
   private Boolean paramD3;
   @JsonProperty(value = "param4")
   private List<Long> paramD4;
   
   private ResourceDDto() {
      // Needed for Jackson de-/serialization
   }
   
   /**
    * Create a new DTO.
    *
    * @param pIdD The Id of this DTO.
    */
   public ResourceDDto(final long pIdD) {
      this.idD = pIdD;
   }
   
   @Override
   public boolean equals(final Object pO) {
      if (this == pO) {
         return true;
      }
      if (pO == null || getClass() != pO.getClass()) {
         return false;
      }
      final ResourceDDto that = (ResourceDDto) pO;
      return (this.idD == that.idD) && Objects.equals(this.paramD1, that.paramD1) &&
            Objects.equals(this.paramD2, that.paramD2) &&
            Objects.equals(this.paramD3, that.paramD3) &&
            Objects.equals(this.paramD4, that.paramD4);
   }
   
   @Override
   public int hashCode() {
      return Objects.hash(this.idD, this.paramD1, this.paramD2, this.paramD3, this.paramD4);
   }
   
   @Override
   public String toString() {
      return "ResourceDDto{" + "idD=" + this.idD + ", paramD1='" + this.paramD1 + '\'' +
            ", paramD2='" + this.paramD2 + '\'' + ", paramD3=" + this.paramD3 + ", paramD4=" +
            this.paramD4 + '}';
   }
   
   public long getIdD() {
      return this.idD;
   }
   
   public ResourceDDto setIdD(final long pIdD) {
      this.idD = pIdD;
      return this;
   }
   
   public String getParamD1() {
      return this.paramD1;
   }
   
   public ResourceDDto setParamD1(final String pParamD1) {
      this.paramD1 = pParamD1;
      return this;
   }
   
   public Integer getParamD2() {
      return this.paramD2;
   }
   
   public ResourceDDto setParamD2(final Integer pParamD2) {
      this.paramD2 = pParamD2;
      return this;
   }
   
   public Boolean getParamD3() {
      return this.paramD3;
   }
   
   public ResourceDDto setParamD3(final Boolean pParamD3) {
      this.paramD3 = pParamD3;
      return this;
   }
   
   public List<Long> getParamD4() {
      return this.paramD4;
   }
   
   public ResourceDDto setParamD4(final List<Long> pParamD4) {
      this.paramD4 = pParamD4;
      return this;
   }
}
