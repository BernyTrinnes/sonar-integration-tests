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
 * The DTO for {@code ResourceF}.
 */
@Data
@AllArgsConstructor
@Builder
@JsonRootName(value = "resource-f")
@JsonPropertyOrder({"id", "param1", "param2", "param3", "param4", "param5", "param6", "param7", "param8"})
public class ResourceFDto {
   @NonNull
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
}
