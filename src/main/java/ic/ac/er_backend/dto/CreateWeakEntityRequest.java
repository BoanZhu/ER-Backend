package ic.ac.er_backend.dto;

import io.github.MigadaTang.common.Cardinality;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class CreateWeakEntityRequest {
    Long schemaID;
    @NotBlank
    String weakEntityName;
    Cardinality weakEntityCardinality;
    Long strongEntityID;
    Cardinality strongEntityCardinality;
    String relationshipName;
    @NotNull
    LayoutInfoVO weakEntityLayoutInfo;
}
