package ic.ac.er_backend.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class reverseEngineerRequest {
  @NotNull
  String databaseType;
  @NotNull
  String hostname;
  @NotBlank
  String portNumber;
  @NotNull
  String databaseName;
  @NotNull
  String username;
  @NotNull
  String password;
  @NotNull
  String requirement;
}
