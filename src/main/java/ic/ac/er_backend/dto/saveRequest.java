package ic.ac.er_backend.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class saveRequest {
  @Positive
  @NotNull
  Long schemaID;
  String json;
}
