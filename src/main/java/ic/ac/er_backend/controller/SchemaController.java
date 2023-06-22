package ic.ac.er_backend.controller;

import ic.ac.er_backend.dto.*;
import io.github.MigadaTang.Attribute;
import io.github.MigadaTang.ER;
import io.github.MigadaTang.Entity;
import io.github.MigadaTang.Relationship;
import io.github.MigadaTang.Schema;
import io.github.MigadaTang.common.RDBMSType;
import io.github.MigadaTang.exception.DBConnectionException;
import io.github.MigadaTang.exception.ERException;
import io.github.MigadaTang.exception.ParseException;
import io.github.MigadaTang.transform.ParserUtil;
import io.github.MigadaTang.transform.Reverse;
import io.github.MigadaTang.transform.Table;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/er/schema")
public class SchemaController {

    private List<Table> oldTables = new ArrayList<>();

    @PostMapping("/create")
    public CreateSchemaResponse createSchema(@RequestBody @Valid CreateSchemaRequest request) {
        Schema schema = ER.createSchema(request.getName());
        oldTables = new ArrayList<>();
        return new CreateSchemaResponse(schema.getID());
    }

    @PostMapping("/update")
    public UpdateSchemaResponse updateSchema(@RequestBody @Valid UpdateSchemaRequest request) {
        Schema schema = Schema.queryByID(request.getSchemaID());
        schema.updateInfo(request.getName());
        return new UpdateSchemaResponse();
    }

    @PostMapping("/delete")
    public DeleteSchemaResponse deleteSchema(@RequestBody @Valid DeleteSchemaRequest request) {
        Schema schema;
        try {
            schema = Schema.queryByID(request.getID());
        } catch (ERException ex) {
            return new DeleteSchemaResponse();
        }
        ER.deleteSchema(schema);
        return new DeleteSchemaResponse();
    }

    @PostMapping("/get_by_id")
    public GetSchemaByIDResponse getByID(@RequestBody @Valid GetSchemaByIDRequest request) {
        Schema schema = Schema.queryByID(request.getID());
        return new GetSchemaByIDResponse(Trans.Transform(schema));
    }

    @PostMapping("/get_json_by_id")
    public GetSchemaJsonByIDResponse getJsonByID(@RequestBody @Valid GetSchemaJsonByIDRequest request) {
        Schema schema;
        try {
            System.out.println("ID: " + request.getID());
            schema = Schema.queryByID(request.getID());
        } catch (ERException e) {
            return new GetSchemaJsonByIDResponse(false, "Fail to get the schema JSON by ID", "");
        }
        String JSON = schema.toRenderJSON();
        return new GetSchemaJsonByIDResponse(true, "successfully to get the schema JSON by ID", JSON);
    }

    @GetMapping("/query_all_schemas")
    public QueryAllSchemasResponse queryAllSchemas(QueryAllSchemasRequest request) {
        List<Schema> schemaList = Schema.queryAll(false);
        return new QueryAllSchemasResponse(Trans.TransSchemaList(schemaList));
    }

    @GetMapping("/delete_all_schemas")
    public DeleteAllSchemasResponse deleteAllSchemas(DeleteAllSchemasRequest request) {
//        ER.deleteAllSchema();
//        for (int i = 1136; i <= 1768; i++) {
        for (int i = 2131; i <= 2153; i++) {
            Long id = (long) i;
            Schema schema = Schema.queryByID(id);
            ER.deleteSchema(schema);
        }
        return new DeleteAllSchemasResponse();
    }

    @PostMapping("/export_schema_to_json")
    public ExportSchemaToJSONResponse exportSchemaToJSON(@RequestBody @Valid ExportSchemaToJSONRequest request) {
        System.out.println("Request: " + request);
        System.out.println("Request.ID: " + request.getID());
        Schema schema = Schema.queryByID(request.getID());
//        Schema schema = Schema.queryByID((Long) "1168");
//        String schemaJSON = schema.toJSON();
        String schemaJSON = schema.toRenderJSON();
        return new ExportSchemaToJSONResponse(schemaJSON);
    }

    @PostMapping("/export_schema_to_ddl")
    public ExportSchemaToDDLResponse exportSchemaToDDL(@RequestBody @Valid ExportSchemaToDDLRequest request) throws ParseException {
        Schema schema = Schema.queryByID(request.getID());

        schema.setOldTables(this.oldTables);

        System.out.println("Size: " + this.oldTables.size());

        String ddl = schema.generateSqlStatement();
        System.out.println(schema.getEntityList());
        for (Entity entity: schema.getEntityList()) {
            System.out.println("Entity: " + entity);
        }

        Map<Long, Table> tableDTOList = ParserUtil.parseRelationshipsToAttribute(schema.getEntityList(), schema.getRelationshipList());
        for (Table table: tableDTOList.values()) {
            System.out.println("Table: " + table);
        }

        this.oldTables = new ArrayList<>();
        this.oldTables.addAll(tableDTOList.values());

        return new ExportSchemaToDDLResponse(ddl);
    }

    @PostMapping("/load_schema_from_json")
    public LoadSchemaFromJSONResponse loadSchemaFromJSON(@RequestBody @Valid LoadSchemaFromJSONRequest request) {
        Schema schema = ER.loadFromJSON(request.getSchemaJSON());
        return new LoadSchemaFromJSONResponse(Trans.Transform(schema));
    }

    @PostMapping("/connect_database_and_execute_ddl")
    public connectDatabaseAndExecuteDDLResponse connectDatabaseAndExecuteDDL(@RequestBody @Valid connectDatabaseAndExecuteDDLRequest request)
        throws DBConnectionException, ParseException {
        Boolean result;
        String response = "Success!";
        try {
            result = ER.connectToDatabaseAndExecuteSql(request.getDatabaseType(), request.getHostname(), request.getPortNumber(), request.getDatabaseName(),
                request.getUsername(), request.getPassword(), request.getDdl());
        } catch (Exception e) {
            response = e.getMessage();
            result = false;
        }

        return new connectDatabaseAndExecuteDDLResponse(result, response);
    }

    @PostMapping("/validate_schema")
    public validateSchemaResponse validateSchema(@RequestBody @Valid validateSchemaRequest request) {
        Schema schema = Schema.queryByID(request.getSchemaID());
        try {
            schema.comprehensiveCheck();
        } catch (Exception e) {
            return new validateSchemaResponse(e.getMessage(), false);
        }
        return new validateSchemaResponse("Current schema is legal", true);
    }

    @PostMapping("/render_schema_as_image")
    public renderSchemaAsImageResponse renderSchemaAsImage(@RequestBody @Valid renderSchemaAsImageRequest request)
        throws ParseException {
        Schema schema = Schema.queryByID(request.getSchemaID());
        schema.comprehensiveCheck();
        try {
            String filePath = request.getFilePath();
            System.out.println("FIlePath: " + filePath);
            schema.renderAsImage("/Users/boanzhu/Desktop/" + filePath);
        } catch (Exception e) {
            return new renderSchemaAsImageResponse("Fail to render the image!", false);
        }

        return new renderSchemaAsImageResponse("Success to render as an image!", true);
    }

    @PostMapping("/reverse_engineer")
    public reverseEngineerResponse reverseEngineer(@RequestBody @Valid reverseEngineerRequest request)
        throws DBConnectionException, ParseException, IOException {

        Reverse reverse = new Reverse();

        RDBMSType databaseType = null;
        String databaseString = request.getDatabaseType();
        if (databaseString.equals("POSTGRESQL") || databaseString.equals("postgresql")) {
            databaseType = RDBMSType.POSTGRESQL;
        } else if (databaseString.equals("H2") || databaseString.equals("h2")) {
            databaseType = RDBMSType.H2;
        } else if (databaseString.equals("SQLSERVER") || databaseString.equals("sqlserver")) {
            databaseType = RDBMSType.SQLSERVER;
        } else if (databaseString.equals("DB2") || databaseString.equals("db2")) {
            databaseType = RDBMSType.DB2;
        } else if (databaseString.equals("ORACLE") || databaseString.equals("oracle")) {
            databaseType = RDBMSType.ORACLE;
        } else if (databaseString.equals("MYSQL") || databaseString.equals("mysql")) {
            databaseType = RDBMSType.MYSQL;
        }

        Boolean requirement;
        if (request.getRequirement().equals("subset")) {
            requirement = true;
        } else {
            requirement = false;
        }

        Schema schema = reverse.relationSchemasToERModelWithLayoutInformation(databaseType, request.getHostname(),
            request.getPortNumber(), request.getDatabaseName(), request.getUsername(), request.getPassword(), requirement);

        String JSON = schema.toRenderJSON();

        Map<Long, Table> tableDTOList = ParserUtil.parseRelationshipsToAttribute(schema.getEntityList(), schema.getRelationshipList());
        this.oldTables = new ArrayList<>();
        this.oldTables.addAll(tableDTOList.values());

        return new reverseEngineerResponse(true, "Reverse engineer success!", JSON);
    }

    @PostMapping("/save")
    public saveResponse save(@RequestBody @Valid saveRequest request) {

        JSONObject jsonObject = new JSONObject(request.getJson());
//        System.out.println(jsonObject);

        // update the positions of all the entities and their attributes.
        JSONArray entities = (JSONArray) jsonObject.get("entityList");
        for (int i = 0; i < entities.length(); i++) {
            System.out.println(entities.get(i));
            JSONObject entityJson = (JSONObject) entities.get(i);
            Long id = (long) (int) entityJson.get("id");
            Entity entity = Entity.queryByID(id);
            JSONObject layoutJson = (JSONObject) entityJson.get("layoutInfo");
            Double x = (double) (int) layoutJson.get("layoutX");
            Double y = (double) (int) layoutJson.get("layoutY");
            entity.updateLayoutInfo(x, y);

            JSONArray attributes = (JSONArray) entityJson.get("attributeList");
            for (int j = 0; j < attributes.length(); j++) {
                JSONObject attributeJson = (JSONObject) attributes.get(j);
                Long idAttribute = (long) (int) attributeJson.get("id");
                Attribute attribute = Attribute.queryByID(idAttribute);
                JSONObject layoutJsonAttribute = (JSONObject) attributeJson.get("layoutInfo");
                attribute.updateLayoutInfo((double) (int) layoutJsonAttribute.get("layoutX"), (double) (int) layoutJsonAttribute.get("layoutY"));
            }
        }

        // update the positions of all the relationships and their attributes.
        JSONArray relationships = (JSONArray) jsonObject.get("relationshipList");
        for (int i = 0; i < relationships.length(); i++) {
            System.out.println(relationships.get(i));
            JSONObject relationshipJson = (JSONObject) relationships.get(i);
            Long id = (long) (int) relationshipJson.get("id");
            Relationship relationship = Relationship.queryByID(id);
            JSONObject layoutJson = (JSONObject) relationshipJson.get("layoutInfo");
            Double x = (double) (int) layoutJson.get("layoutX");
            Double y = (double) (int) layoutJson.get("layoutY");
            relationship.updateLayoutInfo(x, y);

            JSONArray attributes = (JSONArray) relationshipJson.get("attributeList");
            for (int j = 0; j < attributes.length(); j++) {
                JSONObject attributeJson = (JSONObject) attributes.get(j);
                Long idAttribute = (long) (int) attributeJson.get("id");
                Attribute attribute = Attribute.queryByID(idAttribute);
                JSONObject layoutJsonAttribute = (JSONObject) attributeJson.get("layoutInfo");
                attribute.updateLayoutInfo((double) (int) layoutJsonAttribute.get("layoutX"), (double) (int) layoutJsonAttribute.get("layoutY"));
            }
        }
        return new saveResponse("success to save the schema!");
    }
}
