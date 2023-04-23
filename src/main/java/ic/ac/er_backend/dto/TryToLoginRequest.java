package ic.ac.er_backend.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TryToLoginRequest {
  @NonNull
  String username;
  @NotNull
  String password;
}
