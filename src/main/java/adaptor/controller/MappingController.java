package adaptor.controller;

import adaptor.model.source.Order;
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

@RestController
@RequestMapping("/api/mapping")
@RequiredArgsConstructor
public class MappingController {

    private final JsonDataTransformerService jsonDataTransformerService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    @Operation(summary = "Transform source Order to target PurchaseOrder XML")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully transformed to XML", content = @Content(mediaType = MediaType.APPLICATION_XML_VALUE)),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<String> transformOrderToXml(@RequestBody Order sourceOrder) {
        try {
            PurchaseOrder purchaseOrder = jsonDataTransformerService.transformPOJO(sourceOrder, "{}"); //Hardcoded empty json
            String xmlOutput = jsonDataTransformerService.marshalTarget(purchaseOrder);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_XML)
                    .body(xmlOutput);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Transformation failed: " + e.getMessage());
        }
    }


}