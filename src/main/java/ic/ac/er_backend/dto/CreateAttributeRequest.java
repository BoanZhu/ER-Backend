package ic.ac.er_backend.dto;

import io.github.MigadaTang.common.AttributeType;
import io.github.MigadaTang.common.BelongObjType;
import io.github.MigadaTang.common.DataType;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class CreateAttributeRequest {
    @NotNull
    Long belongObjID;
    @NotNull
    BelongObjType belongObjType;
    @NotBlank
    String name;
    @NotNull
    DataType dataType;
    @NotNull
    Boolean isPrimary;
    @NotNull
    AttributeType attributeType;
    @NotNull
    Integer aimPort;
    @NotNull
    LayoutInfoVO layoutInfo;
}
