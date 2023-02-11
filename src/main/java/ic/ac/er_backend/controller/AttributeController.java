package ic.ac.er_backend.controller;

import ic.ac.er_backend.dto.*;
import io.github.MigadaTang.Attribute;
import io.github.MigadaTang.Entity;
import io.github.MigadaTang.Relationship;
import io.github.MigadaTang.common.BelongObjType;
import io.github.MigadaTang.entity.AttributeDO;
import io.github.MigadaTang.exception.ERException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/er/attribute")
public class AttributeController {

    @PostMapping("/create")
    public CreateAttributeResponse createAttribute(@Valid @RequestBody CreateAttributeRequest request) throws ERException {
        Attribute attribute = null;
        switch (request.getBelongObjType()) {
            case ENTITY: {
                Entity entity = Entity.queryByID(request.getBelongObjID());
                attribute = entity.addAttribute(request.getName(), request.getDataType(), request.getIsPrimary(), request.getAttributeType());
                attribute.updateLayoutInfo(request.getLayoutInfo().getLayoutX(), request.getLayoutInfo().getLayoutY());
                attribute.updateAimPort(request.getAimPort());
                break;
            }
            case RELATIONSHIP: {
                Relationship relationship = Relationship.queryByID(request.getBelongObjID());
                attribute = relationship.addAttribute(request.getName(), request.getDataType(), request.getAttributeType());
                attribute.updateLayoutInfo(request.getLayoutInfo().getLayoutX(), request.getLayoutInfo().getLayoutY());
                attribute.updateAimPort(request.getAimPort());
                break;
            }
            default: {
                throw new ERException(String.format("does not support adding attribute to %s", request.getBelongObjType().toString()));
            }
        }
        return new CreateAttributeResponse(attribute.getID());
    }

    @PostMapping("/update")
    public UpdateAttributeResponse updateAttribute(@RequestBody @Valid UpdateAttributeRequest request) {
        Attribute attribute = Attribute.queryByID(request.getAttributeID());
        attribute.updateInfo(request.getName(), request.getDataType(), request.getIsPrimary(), request.getAttributeType());
        if (request.getLayoutInfo() != null) {
            attribute.updateLayoutInfo(request.getLayoutInfo().getLayoutX(), request.getLayoutInfo().getLayoutY());
        }
        if (request.getAimPort() != null) {
            attribute.updateAimPort(request.getAimPort());
        }
        return new UpdateAttributeResponse();
    }

    @PostMapping("/delete")
    public DeleteAttributeResponse deleteAttribute(@RequestBody DeleteAttributeRequest request) {
        Attribute attribute;
        try {
            attribute = Attribute.queryByID(request.getID());
        } catch (ERException ex) {
            return new DeleteAttributeResponse();
        }
        switch (attribute.getBelongObjType()) {
            case ENTITY: {
                Entity entity = Entity.queryByID(attribute.getBelongObjID());
                entity.deleteAttribute(attribute);
                break;
            }
            case RELATIONSHIP: {
                Relationship relationship = Relationship.queryByID(attribute.getBelongObjID());
                relationship.deleteAttribute(attribute);
                break;
            }
        }
        return new DeleteAttributeResponse();
    }

    @GetMapping("/query_by_entity_id")
    public QueryAttributeByEntityIDResponse queryByEntityID(QueryAttributeByEntityIDRequest request) {
        List<Attribute> attributeList = Attribute.query(new AttributeDO(request.getEntityID(), BelongObjType.ENTITY, null, null));
        return new QueryAttributeByEntityIDResponse(Trans.TransAttributeList(attributeList));
    }
}
