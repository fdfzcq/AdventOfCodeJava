package aoc;

import java.util.stream.Stream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class Input {
    public static String inputFromFile(final int year, final int day) {
        final String FILE_PATH = "/git/AdventOfCodeJava/src/main/java/aoc/y" + Integer.toString(year) + "/";
        final String path = System.getenv("HOME") + FILE_PATH + "Day" + Integer.toString(day) + ".txt";
        final StringBuilder stringBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(path), StandardCharsets.UTF_8)) {
            stream.forEach(s -> stringBuilder.append(s).append("\n"));
        } catch (final IOException e)
        {
            e.printStackTrace();
        }
        return stringBuilder.toString().trim();
    }
}
