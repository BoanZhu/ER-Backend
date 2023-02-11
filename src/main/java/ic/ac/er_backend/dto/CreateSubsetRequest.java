package ic.ac.er_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class CreateSubsetRequest {
    Long schemaID;
    @NotBlank
    String subsetName;
    Long belongStrongEntityID;
    Integer aimPort;
    @NotNull
    LayoutInfoVO layoutInfo;
}
