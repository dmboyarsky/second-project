package com.dmboyarsky;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.util.Map;

public class Parser {
    /**
     *
     * @param content
     * @param fileFormat
     * @return Map<String, Object>
     * @throws IOException
     */
    public static Map<String, Object> parse(final String content,
                                            final String fileFormat)
                                            throws IOException {
        return setObjectMapper(fileFormat).readValue(content,
                                            new TypeReference<>() { });
    }

    private static ObjectMapper setObjectMapper(final String fileFormat) {
        return switch (fileFormat) {
            case ".json" -> new ObjectMapper();
            case ".yml" -> new ObjectMapper(new YAMLFactory())
                    .findAndRegisterModules();
            default -> throw new RuntimeException("Unsupported file format: "
                    + fileFormat);
        };
    }

}
