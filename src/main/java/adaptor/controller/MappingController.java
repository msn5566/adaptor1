
package adaptor.controller;

import adaptor.service.MappingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/mapping")
public class MappingController {

    private final MappingService mappingService;

    public MappingController(MappingService mappingService) {
        this.mappingService = mappingService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    @Operation(summary = "Generate and save mapped XML")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Mapping successful", content = @Content(mediaType = MediaType.APPLICATION_XML_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<String> generateMappedXml(
            @RequestPart("sourceXml") MultipartFile sourceXml,
            @RequestPart("mappingJson") MultipartFile mappingJson) throws IOException {

        String result = mappingService.generateMappedXml(sourceXml.getInputStream(), mappingJson.getInputStream());

        return ResponseEntity.status(HttpStatus.CREATED).body(result);


    }
}