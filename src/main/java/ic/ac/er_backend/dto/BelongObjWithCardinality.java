package ic.ac.er_backend.dto;

import io.github.MigadaTang.common.BelongObjType;
import io.github.MigadaTang.common.Cardinality;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BelongObjWithCardinality {
    Long belongObjID;
    BelongObjType belongObjType;
    Cardinality cardinality;
    Integer portAtRelationship;
    Integer portAtEntity;
}
