
package com.generated.microservice.controller;

import com.generated.microservice.service.XmlTransformationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class XmlTransformationControllerTest {

    @Mock
    private XmlTransformationService xmlTransformationService;

    @InjectMocks
    private XmlTransformationController xmlTransformationController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(xmlTransformationController).build();
    }


    @Test
    void shouldTransformXml_whenValidInput() throws Exception {
        String sourceXml = "<root><element1>value1</element1></root>";
        String mappingJson = "{\"target\":{\"root\":\"newRoot\"},\"mapping\":[{\"source\":\"element1\",\"target\":\"newElement1\"}]}";
        String expectedXml = "<newRoot><newElement1>value1</newElement1></newRoot>";

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("sourceXml", sourceXml);
        requestBody.put("mappingJson", mappingJson);


        when(xmlTransformationService.transformXml(anyString(), anyString())).thenReturn(expectedXml);

        mockMvc.perform(post("/transform")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals(expectedXml, result.getResponse().getContentAsString()));


    }



    @Test
    void shouldReturnBadRequest_whenInputInvalid() throws Exception {

        mockMvc.perform(post("/transform")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());


        mockMvc.perform(post("/transform")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"sourceXml\":\"<root></root>\"}"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/transform")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"mappingJson\":\"{}\"}"))
                .andExpect(status().isBadRequest());

    }

}