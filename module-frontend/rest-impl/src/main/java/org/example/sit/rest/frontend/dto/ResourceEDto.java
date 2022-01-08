package org.example.sit.rest.frontend.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * The DTO for {@code ResourceE}.
 */
@Data
@AllArgsConstructor
@Builder
@JsonRootName(value = "resource-e")
@JsonPropertyOrder({"id", "param1", "param2", "param3", "param4", "param5", "param6"})
public class ResourceEDto {
   @NonNull
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
}
