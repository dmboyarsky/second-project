package com.dmboyarsky.formatters;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Stylish {
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
                            return "  + " + node.get("fieldName") + ": "
                                    + node.get("value2");
                        }
                        case "REMOVED" -> {
                            return "  - " + node.get("fieldName") + ": "
                                    + node.get("value1");
                        }
                        case "CHANGED" -> {
                            return "  - " + node.get("fieldName") + ": "
                                    + node.get("value1") + "\n"
                                    + "  + " + node.get("fieldName") + ": "
                                    + node.get("value2");
                        }
                        case "UNCHANGED" -> {
                            return "    " + node.get("fieldName") + ": "
                                    + node.get("value1");
                        }
                        default -> {
                            return null;
                        }
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.joining("\n", "{\n", "\n}"));
    }

}
