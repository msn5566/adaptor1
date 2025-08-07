package adaptor.controller;

import adaptor.service.JsonDataTransformerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/transform")
@RequiredArgsConstructor
public class XmlDataTransformerController {

    private final JsonDataTransformerService jsonDataTransformerService;

    @PostMapping
    @Operation(summary = "Transform XML data based on JSON mapping")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transformation successful", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    public ResponseEntity<String> transformXml(
            @RequestParam("sourceXml") MultipartFile sourceXmlFile,
            @RequestParam("mappingJson") MultipartFile mappingJsonFile
    ) {
        try {
            String sourceXml = new String(sourceXmlFile.getBytes());
            String mappingJson = new String(mappingJsonFile.getBytes());
            String result = jsonDataTransformerService.transform(sourceXml, mappingJson);

            return new ResponseEntity<>(result, HttpStatus.CREATED);


        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return new ResponseEntity<>("Error processing files: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}