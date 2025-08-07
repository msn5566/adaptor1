package adaptor.service;

import java.io.IOException;
import java.io.InputStream;

public interface MappingService {
    String generateMappedXml(InputStream sourceXml, InputStream mappingJson) throws IOException;
}