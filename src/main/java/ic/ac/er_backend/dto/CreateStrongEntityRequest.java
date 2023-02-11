package ic.ac.er_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Data
//@Getter
@AllArgsConstructor
public class CreateStrongEntityRequest {
    Long schemaID;
    @NotBlank
    String name;
    @NotNull
    LayoutInfoVO layoutInfo;
}
