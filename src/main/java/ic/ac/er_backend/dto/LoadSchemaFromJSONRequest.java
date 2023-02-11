package ic.ac.er_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoadSchemaFromJSONRequest {
    @NotNull
    @NotBlank
    String schemaJSON;
}
