package org.example.sit.rest.backend.dto;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * The DTO for {@code ResourceB}.
 */
@JsonRootName(value = "resource-b")
@JsonPropertyOrder({"id", "param1", "param2", "param3", "param4", "param5"})
@SuppressWarnings({"javadoc", "boxing"})
public class ResourceBDto {
   @JsonProperty(value = "id", required = true)
   private long idB;
   @JsonProperty(value = "param1")
   private Integer paramB1;
   @JsonProperty(value = "param2")
   private String paramB2;
   @JsonProperty(value = "param3")
   private String paramB3;
   @JsonProperty(value = "param4")
   private Boolean paramB4;
   @JsonProperty(value = "param5")
   private List<Integer> paramB5;
   
   private ResourceBDto() {
      // Needed for Jackson de-/serialization
   }
   
   /**
    * Create a new DTO.
    *
    * @param pIdB The Id of this DTO.
    */
   public ResourceBDto(final long pIdB) {
      this.idB = pIdB;
   }
   
   @Override
   public boolean equals(final Object pO) {
      if (this == pO) {
         return true;
      }
      if ((null == pO) || (getClass() != pO.getClass())) {
         return false;
      }
      final ResourceBDto that = (ResourceBDto) pO;
      return (this.idB == that.idB) && Objects.equals(this.paramB1, that.paramB1) &&
            Objects.equals(this.paramB2, that.paramB2) &&
            Objects.equals(this.paramB3, that.paramB3) &&
            Objects.equals(this.paramB4, that.paramB4) &&
            Objects.equals(this.paramB5, that.paramB5);
   }
   
   @Override
   public int hashCode() {
      return Objects.hash(this.idB, this.paramB1, this.paramB2, this.paramB3,
            this.paramB4, this.paramB5);
   }
   
   @Override
   public String toString() {
      return "ResourceBDto{" + "idB=" + this.idB + ", paramB1=" + this.paramB1 + ", paramB2='" +
            this.paramB2 + '\'' + ", paramB3='" + this.paramB3 + '\'' + ", paramB4=" +
            this.paramB4 + ", paramB5=" + this.paramB5 + '}';
   }
   
   public long getIdB() {
      return this.idB;
   }
   
   public ResourceBDto setIdB(final long pIdB) {
      this.idB = pIdB;
      return this;
   }
   
   public Integer getParamB1() {
      return this.paramB1;
   }
   
   public ResourceBDto setParamB1(final Integer pParamB1) {
      this.paramB1 = pParamB1;
      return this;
   }
   
   public String getParamB2() {
      return this.paramB2;
   }
   
   public ResourceBDto setParamB2(final String pParamB2) {
      this.paramB2 = pParamB2;
      return this;
   }
   
   public String getParamB3() {
      return this.paramB3;
   }
   
   public ResourceBDto setParamB3(final String pParamB3) {
      this.paramB3 = pParamB3;
      return this;
   }
   
   public Boolean getParamB4() {
      return this.paramB4;
   }
   
   public ResourceBDto setParamB4(final Boolean pParamB4) {
      this.paramB4 = pParamB4;
      return this;
   }
   
   public List<Integer> getParamB5() {
      return this.paramB5;
   }
   
   public ResourceBDto setParamB5(final List<Integer> pParamB5) {
      this.paramB5 = pParamB5;
      return this;
   }
}
