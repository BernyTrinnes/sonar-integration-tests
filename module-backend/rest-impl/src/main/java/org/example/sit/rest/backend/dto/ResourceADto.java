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
 * The DTO for {@code ResourceA}.
 */
@Data
@AllArgsConstructor
@Builder
@JsonRootName(value = "resource-a")
@JsonPropertyOrder({"id", "param1", "param2", "param3"})
public class ResourceADto {
   @NonNull
   @JsonProperty(value = "id", required = true)
   private long idA;
   @JsonProperty(value = "param1")
   private String paramA1;
   @JsonProperty(value = "param2")
   private Boolean paramA2;
   @JsonProperty(value = "param3")
   private List<Long> paramA3;
}
