package com.dmboyarsky;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.util.concurrent.Callable;

@Command (
        mixinStandardHelpOptions = true,
        name = "gendiff",
        description =  "Compare two configuration files and shows a difference."
)
public class Main implements Callable<Integer> {
    /**
     *
     */
    @Option(names = {"-f", "--format"}, defaultValue = "stylish",
            description = "output format [default: ${DEFAULT-VALUE}]")
    private String formatName;

    /**
     *
     */
    @Parameters(paramLabel = "filepath1", description = "path to first file")
    private String filepath1;

    /**
     *
     */
    @Parameters(paramLabel = "filepath2", description = "path to second file")
    private String filepath2;

    @Override
    public final Integer call() throws IOException {
        System.out.println(Differ.generate(filepath1, filepath2, formatName));
        return 0;
    }

    /**
     *
     * @param args
     */
    public static void main(final String[] args) {
        new CommandLine(new Main()).execute(args);
    }
}
