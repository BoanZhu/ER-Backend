package ic.ac.er_backend.dto;

import io.github.MigadaTang.common.EntityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateEntityRequest {
    @Positive
    @NotNull
    Long entityID;
    String name;
    EntityType entityType;
    Long belongStrongEntity;
    Integer aimPort;
    LayoutInfoVO layoutInfo;
}
