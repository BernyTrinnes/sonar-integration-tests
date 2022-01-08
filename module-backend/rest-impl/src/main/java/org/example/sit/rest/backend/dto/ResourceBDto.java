package org.example.sit.rest.backend.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * The DTO for {@code ResourceB}.
 */
@Data
@AllArgsConstructor
@Builder
@JsonRootName(value = "resource-b")
@JsonPropertyOrder({"id", "param1", "param2", "param3", "param4", "param5"})
public class ResourceBDto {
   @NonNull
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
}
