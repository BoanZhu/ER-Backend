package ic.ac.er_backend.dto;

import io.github.MigadaTang.common.BelongObjType;
import io.github.MigadaTang.common.Cardinality;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateRelationshipEdgeRequest {
    @Positive
    @NotNull
    Long relationshipEdgeID;
    Long relationshipID;
    Long belongObjID;
    BelongObjType belongObjType;
    Cardinality cardinality;
    Boolean isKey;
    Integer portAtRelationship;
    Integer portAtBelongObj;
}
