package adaptor.controller;

import adaptor.model.target.PurchaseOrder;
import adaptor.service.JsonDataTransformerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import javax.xml.bind.JAXBException;


@RestController
@RequestMapping("/api/transform")
@RequiredArgsConstructor
public class DataTransformationController {

    private final JsonDataTransformerService jsonDataTransformerService;

    @PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = MediaType.APPLICATION_XML_VALUE)
    @Operation(summary = "Transform XML data based on JSON mapping")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transformation successful",
                    content = {@Content(mediaType = MediaType.APPLICATION_XML_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<String> transformData(@RequestBody TransformationRequest request) throws JAXBException, IOException {

        String transformedXml = jsonDataTransformerService.transform(request.getSourceXml(), request.getMappingJson());
       return ResponseEntity.ok(transformedXml);

    }



    public record TransformationRequest(String sourceXml, String mappingJson) { }

}