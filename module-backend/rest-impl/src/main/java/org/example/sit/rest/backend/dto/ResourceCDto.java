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
 * The DTO for {@code ResourceC}.
 */
@Data
@AllArgsConstructor
@Builder
@JsonRootName(value = "resource-c")
@JsonPropertyOrder({"id", "param1", "param2", "param3", "param4", "param5", "param6", "param7"})
public class ResourceCDto {
   @NonNull
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
}
