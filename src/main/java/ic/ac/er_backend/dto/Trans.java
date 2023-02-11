package ic.ac.er_backend.dto;

import io.github.MigadaTang.*;
import io.github.MigadaTang.common.BelongObjType;

import java.util.ArrayList;
import java.util.List;

public class Trans {
    public static LayoutInfoVO Transform(LayoutInfo layoutInfo) {
        if (layoutInfo == null) {
            return null;
        }
        return new LayoutInfoVO(layoutInfo.getLayoutX(), layoutInfo.getLayoutY());
    }

    public static List<LayoutInfoVO> TransLayoutInfoList(List<LayoutInfo> doList) {
        if (doList == null) {
            return null;
        }
        List<LayoutInfoVO> ret = new ArrayList<>();
        for (LayoutInfo layoutInfo : doList) {
            ret.add(Transform(layoutInfo));
        }
        return ret;
    }

    public static AttributeVO Transform(Attribute attribute) {
        return new AttributeVO(attribute.getID(), attribute.getBelongObjID(), attribute.getBelongObjType(), attribute.getName(), attribute.getDataType(), attribute.getIsPrimary(), attribute.getAttributeType(), attribute.getAimPort(), Transform(attribute.getLayoutInfo()));
    }

    public static List<AttributeVO> TransAttributeList(List<Attribute> doList) {
        if (doList == null) {
            return null;
        }
        List<AttributeVO> ret = new ArrayList<>();
        for (Attribute attribute : doList) {
            ret.add(Transform(attribute));
        }
        return ret;
    }

    public static EntityVO Transform(Entity entityDO) {
        EntityVO entityVO = new EntityVO(entityDO.getID(), entityDO.getName(), entityDO.getEntityType(), TransAttributeList(entityDO.getAttributeList()), entityDO.getAimPort(), null, Transform(entityDO.getLayoutInfo()));
        if (entityDO.getBelongStrongEntity() != null) {
            entityVO.belongStrongEntityID = entityDO.getBelongStrongEntity().getID();
        }
        return entityVO;
    }

    public static List<EntityVO> TransEntityList(List<Entity> doList) {
        if (doList == null) {
            return null;
        }
        List<EntityVO> ret = new ArrayList<>();
        for (Entity Entity : doList) {
            ret.add(Transform(Entity));
        }
        return ret;
    }


    public static RelationshipVO Transform(Relationship relationship) {
        return new RelationshipVO(relationship.getID(), relationship.getName(), TransRelationshipEdgeList(relationship.getEdgeList()), TransAttributeList(relationship.getAttributeList()), Transform(relationship.getLayoutInfo()));
    }

    public static List<RelationshipVO> TransRelationshipList(List<Relationship> doList) {
        if (doList == null) {
            return null;
        }
        List<RelationshipVO> ret = new ArrayList<>();
        for (Relationship relationship : doList) {
            ret.add(Transform(relationship));
        }
        return ret;
    }

    public static RelationshipEdgeVO Transform(RelationshipEdge relationshipEdge) {
        BelongObjType belongObjType = relationshipEdge.getConnObjType();
        return new RelationshipEdgeVO(relationshipEdge.getID(), relationshipEdge.getRelationshipID(), relationshipEdge.getConnObj().getID(), belongObjType, relationshipEdge.getConnObj().getName(),
                relationshipEdge.getCardinality(), relationshipEdge.getPortAtRelationship(), relationshipEdge.getPortAtBelongObj());
    }

    public static List<RelationshipEdgeVO> TransRelationshipEdgeList(List<RelationshipEdge> doList) {
        if (doList == null) {
            return null;
        }
        List<RelationshipEdgeVO> ret = new ArrayList<>();
        for (RelationshipEdge relationshipEdge : doList) {
            ret.add(Transform(relationshipEdge));
        }
        return ret;
    }

    public static SchemaVO Transform(Schema schema) {
        return new SchemaVO(schema.getID(), schema.getName(), TransEntityList(schema.getEntityList()), TransRelationshipList(schema.getRelationshipList()));
    }

    public static List<SchemaVO> TransSchemaList(List<Schema> doList) {
        if (doList == null) {
            return null;
        }
        List<SchemaVO> ret = new ArrayList<>();
        for (Schema schema : doList) {
            ret.add(Transform(schema));
        }
        return ret;
    }
}
