
package adaptor.service;

public interface JsonDataTransformerService {
    String transform(String sourceXml, String mappingJson) throws Exception;
}