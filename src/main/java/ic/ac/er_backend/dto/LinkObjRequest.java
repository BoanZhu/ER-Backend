package ic.ac.er_backend.dto;

import io.github.MigadaTang.common.BelongObjType;
import io.github.MigadaTang.common.Cardinality;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
public class LinkObjRequest {
    @NotNull
    @Positive
    Long relationshipID;
    @NotNull
    @Positive
    Long belongObjID;
    BelongObjType belongObjType;
    Cardinality cardinality;
    Integer portAtRelationship;
    Integer portAtEntity;
}

