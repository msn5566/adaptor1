
package com.generated.microservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import jakarta.xml.parsers.DocumentBuilder;
import jakarta.xml.parsers.DocumentBuilderFactory;
import jakarta.xml.parsers.ParserConfigurationException;
import jakarta.xml.transform.OutputKeys;
import jakarta.xml.transform.Transformer;
import jakarta.xml.transform.TransformerException;
import jakarta.xml.transform.TransformerFactory;
import jakarta.xml.transform.dom.DOMSource;
import jakarta.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;

@Service
public class XmlTransformationService {

    private final ObjectMapper objectMapper;
    private final XmlMapper xmlMapper;

    public XmlTransformationService(ObjectMapper objectMapper, XmlMapper xmlMapper) {
        this.objectMapper = objectMapper;
        this.xmlMapper = xmlMapper;
    }

    public String transformXml(String sourceXml, String mappingJson) throws Exception {
        JsonNode mapping = null;
        if (mappingJson != null && !mappingJson.isEmpty()) {
            mapping = objectMapper.readTree(mappingJson);
        }

        Document sourceDoc = parseXml(sourceXml);

        Document targetDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

        Element rootElement = targetDoc.createElement("newRoot");
        if (mapping != null && mapping.has("target") && mapping.get("target").has("root")) {
            rootElement = targetDoc.createElement(mapping.get("target").get("root").asText());
        }
        targetDoc.appendChild(rootElement);

        if (mapping != null && mapping.has("mapping")) {
            transform(sourceDoc.getDocumentElement(), rootElement, mapping.get("mapping"), targetDoc);
        }


        return xmlToString(targetDoc);
    }

    private Document parseXml(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }

    private String xmlToString(Document doc) throws Exception {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "no");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        return writer.getBuffer().toString();
    }

    private void transform(Node sourceNode, Element targetElement, JsonNode mapping, Document targetDoc) {
        if (sourceNode.getNodeType() == Node.ELEMENT_NODE) {
            NodeList childNodes = sourceNode.getChildNodes();

            for (JsonNode map : mapping) {
                JsonNode sourceNodeName = map.get("source");
                JsonNode targetNodeName = map.get("target");

                if (sourceNodeName == null || targetNodeName == null) {
                    continue;
                }

                String source = sourceNodeName.asText();
                String target = targetNodeName.asText();

                for (int i = 0; i < childNodes.getLength(); i++) {
                    Node childNode = childNodes.item(i);

                    if (childNode.getNodeType() == Node.ELEMENT_NODE && childNode.getNodeName().equals(source)) {
                        Element newTargetElement = targetDoc.createElement(target);

                        NodeList grandChildNodes = childNode.getChildNodes();
                        for (int j = 0; j < grandChildNodes.getLength(); j++) {
                            Node grandChildNode = grandChildNodes.item(j);
                            if (grandChildNode.getNodeType() == Node.TEXT_NODE) {
                                Text textNode = targetDoc.createTextNode(grandChildNode.getTextContent());
                                newTargetElement.appendChild(textNode);
                            }
                        }

                        targetElement.appendChild(newTargetElement);
                    }
                }
            }
        }
    }
}