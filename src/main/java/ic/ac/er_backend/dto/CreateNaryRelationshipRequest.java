package ic.ac.er_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
public class CreateNaryRelationshipRequest {
    Long schemaID;
    @NotBlank
    String name;
    List<BelongObjWithCardinality> belongObjWithCardinalityList;
    @NotNull
    LayoutInfoVO layoutInfo;
}

