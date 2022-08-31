package com.dmboyarsky.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Json {
    /**
     *
     * @param diff
     * @return String
     * @throws IOException
     */
    public static String format(final List<Map<String, Object>> diff)
                                                        throws IOException {
        Map<String, Object> formatting = new LinkedHashMap<>();
        for (Map<String, Object> node : diff) {
            switch (node.get("status").toString()) {
                case "ADDED" -> formatting.put("+ " + node.get("fieldName"),
                                Objects.toString(node.get("value2")));
                case "REMOVED" -> formatting.put("- " + node.get("fieldName"),
                                Objects.toString(node.get("value1")));
                case "CHANGED" -> {
                    formatting.put("- " + node.get("fieldName"),
                                Objects.toString(node.get("value1")));
                    formatting.put("+ " + node.get("fieldName"),
                                Objects.toString(node.get("value2")));
                }
                case "UNCHANGED" -> formatting.put(
                        Objects.toString(node.get("fieldName")),
                        Objects.toString(node.get("value1")));
                default -> { }
            }
        }
        return new ObjectMapper().writeValueAsString(formatting);
    }
}
