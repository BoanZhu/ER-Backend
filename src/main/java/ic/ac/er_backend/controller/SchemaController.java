package ic.ac.er_backend.controller;

import ic.ac.er_backend.dto.*;
import io.github.MigadaTang.ER;
import io.github.MigadaTang.Entity;
import io.github.MigadaTang.Schema;
import io.github.MigadaTang.common.RDBMSType;
import io.github.MigadaTang.exception.DBConnectionException;
import io.github.MigadaTang.exception.ERException;
import io.github.MigadaTang.exception.ParseException;
import io.github.MigadaTang.transform.ParserUtil;
import io.github.MigadaTang.transform.Reverse;
import io.github.MigadaTang.transform.Table;
import java.util.ArrayList;
import java.util.Map;
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
//        this.oldTables = new ArrayList<>();
        System.out.println(this.oldTables.size());
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

    @GetMapping("/query_all_schemas")
    public QueryAllSchemasResponse queryAllSchemas(QueryAllSchemasRequest request) {
        List<Schema> schemaList = Schema.queryAll(false);
        return new QueryAllSchemasResponse(Trans.TransSchemaList(schemaList));
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
        System.out.println(this.oldTables);
        for (Table table: this.oldTables) {
            System.out.println("Table: " + table);
        }

        schema.setOldTables(this.oldTables);
        String ddl = schema.generateSqlStatement();
        for (Entity entity: schema.getEntityList()) {
            System.out.println("ENTITY ID: " + entity.getName() + " " + entity.getID());
        }

        Map<Long, Table> tableDTOList = ParserUtil.parseRelationshipsToAttribute(schema.getEntityList(), schema.getRelationshipList());


        for (Table table: tableDTOList.values()) {
            System.out.println("123: " + table.getName() + " " + table.getId());
        }
        this.oldTables = new ArrayList<>();
        this.oldTables.addAll(tableDTOList.values());
//        String ddl = schema.generateSqlStatement();
        System.out.println("ddl: " + ddl);
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
        throws DBConnectionException, ParseException {

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

        Schema schema = reverse.relationSchemasToERModel(databaseType, request.getHostname(),
            request.getPortNumber(), request.getDatabaseName(), request.getUsername(), request.getPassword());

        Map<Long, Table> tableDTOList = ParserUtil.parseRelationshipsToAttribute(schema.getEntityList(), schema.getRelationshipList());
        this.oldTables = new ArrayList<>();
        this.oldTables.addAll(tableDTOList.values());
        for (Table table: tableDTOList.values()) {
            System.out.println("TABLE ID: " + table.getName() + " " + table.getId());
        }

        String JSON = schema.toRenderJSON();
//        System.out.println("JSON: " + JSON);
        return new reverseEngineerResponse(true, "Reverse engineer success!", JSON);
    }
}
