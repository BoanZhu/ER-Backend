package ic.ac.er_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QueryAttributeByEntityIDRequest {
    Long entityID;
}
