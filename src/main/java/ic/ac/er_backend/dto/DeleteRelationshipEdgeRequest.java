package ic.ac.er_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeleteRelationshipEdgeRequest {
    @Positive
    @NotNull
    Long edgeID;
}
