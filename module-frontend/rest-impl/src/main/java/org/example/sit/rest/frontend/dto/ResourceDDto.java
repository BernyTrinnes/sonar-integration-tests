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
 * The DTO for {@code ResourceD}.
 */
@Data
@AllArgsConstructor
@Builder
@JsonRootName(value = "resource-d")
@JsonPropertyOrder({"id", "param1", "param2", "param3", "param4"})
public class ResourceDDto {
   @NonNull
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
}
