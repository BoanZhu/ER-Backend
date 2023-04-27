package ic.ac.er_backend.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Data;

@Data
public class renderSchemaAsImageRequest {
  @Positive
  @NotNull
  Long schemaID;
  String filePath;
}
