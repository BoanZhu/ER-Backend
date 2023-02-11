package ic.ac.er_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CreateWeakEntityResponse {
    Long weakEntityID;
    Long relationshipID;
    List<RelationshipEdgeVO> relationshipEdgeList;
}
