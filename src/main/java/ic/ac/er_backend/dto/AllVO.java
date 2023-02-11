package ic.ac.er_backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.MigadaTang.common.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
class AttributeVO {
    Long ID;
    Long belongObjID;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    BelongObjType belongObjType;
    String name;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    DataType dataType;
    Boolean isPrimary;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    AttributeType attributeType;
    Integer aimPort;
    LayoutInfoVO layoutInfo;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class EntityVO {
    Long ID;
    String name;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    EntityType entityType;
    List<AttributeVO> attributeList;
    Integer aimPort;
    Long belongStrongEntityID;
    LayoutInfoVO layoutInfo;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class RelationshipVO {
    Long ID;
    String name;
    List<RelationshipEdgeVO> edgeList;
    List<AttributeVO> attributeList;
    LayoutInfoVO layoutInfo;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class RelationshipEdgeVO {
    Long ID;
    Long relationshipID;
    Long belongObjID;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    BelongObjType belongObjType;
    String belongObjName;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    Cardinality cardinality;
    Integer portAtRelationship;
    Integer portAtBelongObj;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class SchemaVO {
    Long ID;
    String name;
    List<EntityVO> entityList;
    List<RelationshipVO> relationshipList;
}

