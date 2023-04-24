package ic.ac.er_backend.controller;

import ic.ac.er_backend.dto.*;
import io.github.MigadaTang.ER;
import io.github.MigadaTang.Schema;
import io.github.MigadaTang.exception.DBConnectionException;
import io.github.MigadaTang.exception.ERException;
import io.github.MigadaTang.exception.ParseException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/er/schema")
public class SchemaController {

    @PostMapping("/create")
    public CreateSchemaResponse createSchema(@RequestBody @Valid CreateSchemaRequest request) {
        Schema schema = ER.createSchema(request.getName());
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

    @GetMapping("/export_schema_to_json")
    public ExportSchemaToJSONResponse exportSchemaToJSON(ExportSchemaToJSONRequest request) {
        Schema schema = Schema.queryByID(request.getID());
        return new ExportSchemaToJSONResponse(schema.toJSON());
    }

    @PostMapping("/export_schema_to_ddl")
    public ExportSchemaToDDLResponse exportSchemaToDDL(@RequestBody @Valid ExportSchemaToDDLRequest request) throws ParseException {
        Schema schema = Schema.queryByID(request.getID());
        return new ExportSchemaToDDLResponse(schema.generateSqlStatement());
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
}
