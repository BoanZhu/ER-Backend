package ic.ac.er_backend.dto;

import io.github.MigadaTang.common.AttributeType;
import io.github.MigadaTang.common.DataType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateAttributeRequest {
    @Positive
    @NotNull
    Long attributeID;
    String name;
    DataType dataType;
    Boolean isPrimary;
    AttributeType attributeType;
    Integer aimPort;
    LayoutInfoVO layoutInfo;
}
