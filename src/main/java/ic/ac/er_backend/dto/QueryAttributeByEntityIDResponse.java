package ic.ac.er_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QueryAttributeByEntityIDResponse {
    List<AttributeVO> attributeList;
}
