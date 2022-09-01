package com.dmboyarsky;

import com.dmboyarsky.formatters.Json;
import com.dmboyarsky.formatters.Plain;
import com.dmboyarsky.formatters.Stylish;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Formatter {
    /**
     *
     * @param diff
     * @param outputFormat
     * @return String
     * @throws IOException
     */
    public static String output(final List<Map<String, Object>> diff,
                                final String outputFormat) throws IOException {
        return switch (outputFormat) {
            case "json" -> Json.format(diff);
            case "plain" -> Plain.format(diff);
            case "stylish" -> Stylish.format(diff);
            default -> throw new RuntimeException("Unsupported output format: "
                    + outputFormat);
        };
    }

}
