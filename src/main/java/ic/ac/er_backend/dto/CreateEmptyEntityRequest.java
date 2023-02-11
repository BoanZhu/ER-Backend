package ic.ac.er_backend.dto;

import io.github.MigadaTang.common.EntityType;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class CreateEmptyEntityRequest {
    Long schemaID;
    @NotBlank
    String name;
    @NotNull
    EntityType entityType;
    @NotNull
    LayoutInfoVO layoutInfo;
}
