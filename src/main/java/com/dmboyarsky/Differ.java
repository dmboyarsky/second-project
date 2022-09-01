package com.dmboyarsky;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Differ {
    /**
     *
     * @param path1
     * @param path2
     * @param outputFormat
     * @return String
     * @throws IOException
     */
    public static String generate(final String path1,
                                  final String path2,
                                  final String outputFormat)
                                            throws IOException {
        Map<String, Object> fileContent1 = Parser.parse(Files.readString(
                Path.of(castAbsolutePath(path1))).trim(),
                getFileFormat(path1));
        Map<String, Object> fileContent2 = Parser.parse(Files.readString(
                Path.of(castAbsolutePath(path2))).trim(),
                getFileFormat(path2));

        return Formatter.output(generateDiff(fileContent1,
                                                fileContent2),
                                                outputFormat);
    }

    /***
     *
     * @param path1
     * @param path2
     * @return String
     * @throws IOException
     */
    public static String generate(final String path1,
                                  final String path2) throws IOException {
        final String defaultFormat = "stylish";
        return generate(path1, path2, defaultFormat);
    }

    private static List<Map<String, Object>> generateDiff(
                                        final Map<String, Object> content1,
                                        final Map<String, Object> content2) {
        TreeSet<String> mergingKeyContent = new TreeSet<>(content1.keySet());
        mergingKeyContent.addAll(content2.keySet());
        return mergingKeyContent.stream()
                .map((k) -> {
                    Map<String, Object> valueMap = new HashMap<>();
                    valueMap.put("fieldName", k);
                    valueMap.put("value1", content1.getOrDefault(k, null));
                    valueMap.put("value2", content2.getOrDefault(k, null));
                    if (!content1.containsKey(k)) {
                        valueMap.put("status", "ADDED");
                    } else if (!content2.containsKey(k)) {
                        valueMap.put("status", "REMOVED");
                    } else if (!Objects.equals(content1.get(k),
                                                content2.get(k))) {
                        valueMap.put("status", "CHANGED");
                    } else {
                        valueMap.put("status", "UNCHANGED");
                    }
                    return valueMap;
                })
                .collect(Collectors.toList());
    }

    private static String getFileFormat(final String path) {
        return path.substring(path.lastIndexOf("."));
    }

    private static String castAbsolutePath(final String path) {
        if (Paths.get(path).isAbsolute()) {
            return path;
        }
        return Paths.get(path).toAbsolutePath().toString();
    }

}
