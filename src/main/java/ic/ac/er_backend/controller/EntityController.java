package ic.ac.er_backend.controller;

import ic.ac.er_backend.dto.*;
import io.github.MigadaTang.Entity;
import io.github.MigadaTang.Relationship;
import io.github.MigadaTang.Schema;
import io.github.MigadaTang.exception.ERException;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/er/entity")
public class EntityController {

    @PostMapping("/create_strong")
    public CreateStrongEntityResponse createStrongEntity(@RequestBody @Valid CreateStrongEntityRequest request) {
        Schema schema = Schema.queryByID(request.getSchemaID());
        Entity entity = schema.addEntity(request.getName());
        entity.updateLayoutInfo(request.getLayoutInfo().getLayoutX(), request.getLayoutInfo().getLayoutY());
        return new CreateStrongEntityResponse(entity.getID());
    }

    @PostMapping("/create_subset")
    public CreateSubsetResponse createSubset(@RequestBody @Valid CreateSubsetRequest request) {
        Schema schema = Schema.queryByID(request.getSchemaID());
        Entity strongEntity = Entity.queryByID(request.getBelongStrongEntityID());
        Entity entity = schema.addSubset(request.getSubsetName(), strongEntity);
        entity.updateLayoutInfo(request.getLayoutInfo().getLayoutX(), request.getLayoutInfo().getLayoutY());
        entity.updateAimPort(request.getAimPort());
        return new CreateSubsetResponse(entity.getID());
    }

    @PostMapping("/create_generalisation")
    public CreateGeneralisationResponse createGeneralisation(@RequestBody @Valid CreateGeneralisationRequest request) {
        Schema schema = Schema.queryByID(request.getSchemaID());
        Entity strongEntity = Entity.queryByID(request.getBelongStrongEntityID());
        Entity entity = schema.addGeneralisation(request.getSubsetName(), strongEntity);
        entity.updateLayoutInfo(request.getLayoutInfo().getLayoutX(), request.getLayoutInfo().getLayoutY());
        entity.updateAimPort(request.getAimPort());
        return new CreateGeneralisationResponse(entity.getID());
    }

    @PostMapping("/create_weak_entity")
    public CreateWeakEntityResponse createWeakEntity(@RequestBody @Valid CreateWeakEntityRequest request) {
        Schema schema = Schema.queryByID(request.getSchemaID());
        Entity strongEntity = Entity.queryByID(request.getStrongEntityID());
        ImmutablePair<Entity, Relationship> pair = schema.addWeakEntity(request.getWeakEntityName(), strongEntity, request.getRelationshipName(), request.getWeakEntityCardinality(), request.getStrongEntityCardinality());
        Entity weakEntity = pair.getLeft();
        Relationship relationship = pair.getRight();
        weakEntity.updateLayoutInfo(request.getWeakEntityLayoutInfo().getLayoutX(), request.getWeakEntityLayoutInfo().getLayoutY());
        return new CreateWeakEntityResponse(weakEntity.getID(), relationship.getID(), Trans.TransRelationshipEdgeList(relationship.getEdgeList()));
    }

    @PostMapping("/create_empty")
    public CreateEmptyEntityResponse createEmptyEntity(@RequestBody @Valid CreateEmptyEntityRequest request) {
        Schema schema = Schema.queryByID(request.getSchemaID());
        Entity entity = schema.addEntity(request.getName(), request.getEntityType());
        entity.updateLayoutInfo(request.getLayoutInfo().getLayoutX(), request.getLayoutInfo().getLayoutY());
        return new CreateEmptyEntityResponse(entity.getID());
    }

    @PostMapping("/update")
    public UpdateEntityResponse updateEntity(@RequestBody @Valid UpdateEntityRequest request) {
        Entity entity = Entity.queryByID(request.getEntityID());
        Entity belongStrongEntity = null;
        if (request.getBelongStrongEntity() != null) {
            belongStrongEntity = Entity.queryByID(request.getBelongStrongEntity());
        }
        entity.updateInfo(request.getName(), request.getEntityType(), belongStrongEntity);
        if (request.getLayoutInfo() != null) {
            entity.updateLayoutInfo(request.getLayoutInfo().getLayoutX(), request.getLayoutInfo().getLayoutY());
        }
        if (request.getAimPort() != null) {
            entity.updateAimPort(request.getAimPort());
        }
        return new UpdateEntityResponse();
    }

    @PostMapping("/delete_subset_belong")
    public DeleteSubsetBelongResponse deleteSubsetBelong(@RequestBody @Valid DeleteSubsetBelongRequest request) {
        Entity entity = Entity.queryByID(request.getID());
        entity.removeBelongStrongEntity();
        return new DeleteSubsetBelongResponse();
    }

    @PostMapping("/delete")
    public DeleteEntityResponse deleteEntity(@RequestBody @Valid DeleteEntityRequest request) {
        Entity entity;
        try {
            entity = Entity.queryByID(request.getID());
        } catch (ERException ex) {
            return new DeleteEntityResponse();
        }
        Schema schema = Schema.queryByID(entity.getSchemaID());
        schema.deleteEntity(entity);
        return new DeleteEntityResponse();
    }

    @GetMapping("/get_by_id")
    public GetEntityByIDResponse getByID(GetEntityByIDRequest request) {
        Entity entity = Entity.queryByID(request.getID());
        return new GetEntityByIDResponse(Trans.Transform(entity));
    }
}