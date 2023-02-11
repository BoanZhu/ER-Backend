package ic.ac.er_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateSchemaRequest {
    @Positive
    @NotNull
    Long schemaID;
    String name;
}
