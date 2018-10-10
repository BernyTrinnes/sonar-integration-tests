package org.example.sit.rest.frontend.dto;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * The DTO for {@code ResourceF}.
 */
@JsonRootName(value = "resource-f")
@JsonPropertyOrder({"id", "param1", "param2", "param3", "param4", "param5", "param6", "param7",
      "param8"})
@SuppressWarnings({"javadoc", "boxing"})
public class ResourceFDto {
   @JsonProperty(value = "id", required = true)
   private long idF;
   @JsonProperty(value = "param1")
   private String paramF1;
   @JsonProperty(value = "param2")
   private Integer paramF2;
   @JsonProperty(value = "param3")
   private String paramF3;
   @JsonProperty(value = "param4")
   private String paramF4;
   @JsonProperty(value = "param5")
   private Boolean paramF5;
   @JsonProperty(value = "param6")
   private Long paramF6;
   @JsonProperty(value = "param7")
   private String paramF7;
   @JsonProperty(value = "param8")
   private List<String> paramF8;
   
   private ResourceFDto() {
      // Needed for Jackson de-/serialization
   }
   
   /**
    * Create a new DTO.
    *
    * @param pIdF The Id of this DTO.
    */
   public ResourceFDto(final long pIdF) {
      this.idF = pIdF;
   }
   
   @Override
   public boolean equals(final Object pO) {
      if (this == pO) {
         return true;
      }
      if ((null == pO) || (getClass() != pO.getClass())) {
         return false;
      }
      final ResourceFDto that = (ResourceFDto) pO;
      return this.idF == that.idF && Objects.equals(this.paramF1, that.paramF1) &&
            Objects.equals(this.paramF2, that.paramF2) &&
            Objects.equals(this.paramF3, that.paramF3) &&
            Objects.equals(this.paramF4, that.paramF4) &&
            Objects.equals(this.paramF5, that.paramF5) &&
            Objects.equals(this.paramF6, that.paramF6) &&
            Objects.equals(this.paramF7, that.paramF7) &&
            Objects.equals(this.paramF8, that.paramF8);
   }
   
   @Override
   public int hashCode() {
      return Objects.hash(this.idF, this.paramF1, this.paramF2, this.paramF3, this.paramF4,
            this.paramF5, this.paramF6, this.paramF7, this.paramF8);
   }
   
   @Override
   public String toString() {
      return "ResourceFDto{" + "idF=" + this.idF + ", paramF1='" + this.paramF1 + '\'' +
            ", paramF2=" + this.paramF2 + ", paramF3='" + this.paramF3 + '\'' + ", paramF4='" +
            this.paramF4 + '\'' + ", paramF5=" + this.paramF5 + ", paramF6=" + this.paramF6 +
            ", paramF7='" + this.paramF7 + '\'' + ", paramF8=" + this.paramF8 + '}';
   }
   
   public long getIdF() {
      return this.idF;
   }
   
   public ResourceFDto setIdF(final long pIdF) {
      this.idF = pIdF;
      return this;
   }
   
   public String getParamF1() {
      return this.paramF1;
   }
   
   public ResourceFDto setParamF1(final String pParamF1) {
      this.paramF1 = pParamF1;
      return this;
   }
   
   public Integer getParamF2() {
      return this.paramF2;
   }
   
   public ResourceFDto setParamF2(final Integer pParamF2) {
      this.paramF2 = pParamF2;
      return this;
   }
   
   public String getParamF3() {
      return this.paramF3;
   }
   
   public ResourceFDto setParamF3(final String pParamF3) {
      this.paramF3 = pParamF3;
      return this;
   }
   
   public String getParamF4() {
      return this.paramF4;
   }
   
   public ResourceFDto setParamF4(final String pParamF4) {
      this.paramF4 = pParamF4;
      return this;
   }
   
   public Boolean getParamF5() {
      return this.paramF5;
   }
   
   public ResourceFDto setParamF5(final Boolean pParamF5) {
      this.paramF5 = pParamF5;
      return this;
   }
   
   public Long getParamF6() {
      return this.paramF6;
   }
   
   public ResourceFDto setParamF6(final Long pParamF6) {
      this.paramF6 = pParamF6;
      return this;
   }
   
   public String getParamF7() {
      return this.paramF7;
   }
   
   public ResourceFDto setParamF7(final String pParamF7) {
      this.paramF7 = pParamF7;
      return this;
   }
   
   public List<String> getParamF8() {
      return this.paramF8;
   }
   
   public ResourceFDto setParamF8(final List<String> pParamF8) {
      this.paramF8 = pParamF8;
      return this;
   }
}
