package ic.ac.er_backend.dto;

import io.github.MigadaTang.common.AttributeType;
import io.github.MigadaTang.common.BelongObjType;
import io.github.MigadaTang.common.DataType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class connectDatabaseAndExecuteDDLRequest {
  @NotNull
  String databaseType;
  @NotNull
  String hostname;
  @NotBlank
  String portNumber;
  @NotNull
  String databaseName;
  @NotNull
  String username;
  @NotNull
  String password;
  @NotNull
  String ddl;
}
