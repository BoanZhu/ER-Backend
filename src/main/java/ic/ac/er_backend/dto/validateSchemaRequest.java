package ic.ac.er_backend.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class validateSchemaRequest {
  @Positive
  @NotNull
  Long schemaID;
}
