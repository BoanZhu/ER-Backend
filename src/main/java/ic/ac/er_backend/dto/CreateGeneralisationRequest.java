package ic.ac.er_backend.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateGeneralisationRequest {
  Long schemaID;
  @NotBlank
  String subsetName;
  Long belongStrongEntityID;
  Integer aimPort;
  @NotNull
  LayoutInfoVO layoutInfo;
}
