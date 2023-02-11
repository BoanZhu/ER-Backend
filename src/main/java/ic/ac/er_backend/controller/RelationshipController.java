package ic.ac.er_backend.controller;

import ic.ac.er_backend.dto.*;
import io.github.MigadaTang.*;
import io.github.MigadaTang.common.BelongObjType;
import io.github.MigadaTang.common.ConnObjWithCardinality;
import io.github.MigadaTang.exception.ERException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/er/relationship")
public class RelationshipController {

    @PostMapping("/create_nary")
    public CreateNaryRelationshipResponse createNary(@RequestBody @Valid CreateNaryRelationshipRequest request) {
        Schema schema = Schema.queryByID(request.getSchemaID());
        Relationship relationship = null;
        if (request.getBelongObjWithCardinalityList() != null && request.getBelongObjWithCardinalityList().size() != 0) {
            Map<Long, BelongObjWithCardinality> entityToCardinality = new HashMap<>();
            Map<Long, BelongObjWithCardinality> relationshipToCardinality = new HashMap<>();
            ArrayList<ConnObjWithCardinality> connObjWithCardinalityList = new ArrayList<>();
            for (BelongObjWithCardinality eCard : request.getBelongObjWithCardinalityList()) {
                if (eCard.getBelongObjType() == BelongObjType.ENTITY) {
                    Entity entity = Entity.queryByID(eCard.getBelongObjID());
                    connObjWithCardinalityList.add(new ConnObjWithCardinality(entity, eCard.getCardinality()));
                    entityToCardinality.put(entity.getID(), eCard);
                } else if (eCard.getBelongObjType() == BelongObjType.RELATIONSHIP) {
                    Relationship linkRelationship = Relationship.queryByID(eCard.getBelongObjID());
                    connObjWithCardinalityList.add(new ConnObjWithCardinality(linkRelationship, eCard.getCardinality()));
                    relationshipToCardinality.put(linkRelationship.getID(), eCard);
                }
            }
            relationship = schema.createNaryRelationship(request.getName(), connObjWithCardinalityList);
            for (RelationshipEdge edge : relationship.getEdgeList()) {
                BelongObjWithCardinality eCard = null;
                if (edge.getConnObj() instanceof Entity) {
                    eCard = entityToCardinality.get(edge.getConnObj().getID());
                } else if (edge.getConnObj() instanceof Relationship) {
                    eCard = relationshipToCardinality.get(edge.getConnObj().getID());
                }
                edge.updatePorts(eCard.getPortAtRelationship(), eCard.getPortAtEntity());
            }
        } else {
            relationship = schema.createEmptyRelationship(request.getName());
        }
        if (request.getLayoutInfo() != null) {
            relationship.updateLayoutInfo(request.getLayoutInfo().getLayoutX(), request.getLayoutInfo().getLayoutY());
        }
        return new CreateNaryRelationshipResponse(relationship.getID(), Trans.TransRelationshipEdgeList(relationship.getEdgeList()));
    }

    @PostMapping("/link_obj")
    public LinkObjResponse linkObj(@RequestBody @Valid LinkObjRequest request) {
        Relationship relationship = Relationship.queryByID(request.getRelationshipID());
        ERConnectableObj connectableObj = null;
        if (request.getBelongObjType() == BelongObjType.ENTITY) {
            connectableObj = Entity.queryByID(request.getBelongObjID());
        } else if (request.getBelongObjType() == BelongObjType.RELATIONSHIP) {
            connectableObj = Relationship.queryByID(request.getBelongObjID());
        }
        RelationshipEdge edge = relationship.linkObj(connectableObj, request.getCardinality());
        edge.updatePorts(request.getPortAtRelationship(), request.getPortAtEntity());
        return new LinkObjResponse(edge.getID());
    }

    @PostMapping("/update")
    public UpdateRelationshipResponse update(@RequestBody @Valid UpdateRelationshipRequest request) {
        Relationship relationship = Relationship.queryByID(request.getRelationshipID());
        if (request.getLayoutInfo() != null) {
            relationship.updateLayoutInfo(request.getLayoutInfo().getLayoutX(), request.getLayoutInfo().getLayoutY());
        }
        return new UpdateRelationshipResponse();
    }

    @PostMapping("/update_edge")
    public UpdateRelationshipEdgeResponse updateEdge(@RequestBody @Valid UpdateRelationshipEdgeRequest request) {
        RelationshipEdge edge = RelationshipEdge.queryByID(request.getRelationshipEdgeID());
        ERConnectableObj connObj = null;
        if (request.getBelongObjType() == BelongObjType.ENTITY) {
            connObj = Entity.queryByID(request.getBelongObjID());
        } else if (request.getBelongObjType() == BelongObjType.RELATIONSHIP) {
            connObj = Relationship.queryByID(request.getBelongObjID());
        }
        edge.updateInfo(request.getRelationshipID(), request.getCardinality(), connObj, request.getIsKey());
        edge.updatePorts(request.getPortAtRelationship(), request.getPortAtBelongObj());
        return new UpdateRelationshipEdgeResponse();
    }

    @PostMapping("/delete")
    public DeleteRelationshipResponse delete(@RequestBody @Valid DeleteRelationshipRequest request) {
        Relationship relationship;
        try {
            relationship = Relationship.queryByID(request.getID());
        } catch (ERException ex) {
            return new DeleteRelationshipResponse();
        }
        Schema schema = Schema.queryByID(relationship.getSchemaID());
        schema.deleteRelationship(relationship);
        return new DeleteRelationshipResponse();
    }

    @PostMapping("/delete_edge")
    public DeleteRelationshipEdgeResponse deleteEdge(@RequestBody @Valid DeleteRelationshipEdgeRequest request) {
        RelationshipEdge edge;
        try {
            edge = RelationshipEdge.queryByID(request.getEdgeID());
        } catch (ERException ex) {
            return new DeleteRelationshipEdgeResponse();
        }

        Relationship relationship = Relationship.queryByID(edge.getRelationshipID());
        relationship.deleteEdge(edge);
        return new DeleteRelationshipEdgeResponse();
    }
}
