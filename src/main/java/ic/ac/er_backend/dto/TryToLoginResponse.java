package ic.ac.er_backend.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
//@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
public class TryToLoginResponse {
  String message;
}
