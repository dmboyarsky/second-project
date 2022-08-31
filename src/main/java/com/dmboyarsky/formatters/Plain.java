package com.dmboyarsky.formatters;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Plain {
    /**
     *
     * @param diff
     * @return String
     */
    public static String format(final List<Map<String, Object>> diff) {

        return diff.stream()
                .map((node) -> {
                    switch (node.get("status").toString()) {
                        case "ADDED" -> {
                            return "Property '" + node.get("fieldName")
                                    + "' was added with value: "
                                    + convert(node.get("value2"));
                        }
                        case "REMOVED" -> {
                            return "Property '" + node.get("fieldName")
                                    + "' was removed";
                        }
                        case "CHANGED" -> {
                            return "Property '" + node.get("fieldName")
                                    + "' was updated. From"
                                    + convert(node.get("value1")) + " to "
                                    + convert(node.get("value2"));
                        }
                        default -> {
                            return null;
                        }
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.joining("\n"));
    }

    private static String convert(final Object element) {
        if (element instanceof String) {
            return "'" + element + "'";
        }
        if (element instanceof Collection || element instanceof Map) {
            return "[complex value]";
        }
        return Objects.toString(element);
    }

}
