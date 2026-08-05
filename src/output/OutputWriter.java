package output;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;

public final class OutputWriter {
    private static final Path OUTPUT_DIR = Path.of("output");

    private OutputWriter() { }

    public static void clean() throws IOException {
        if (Files.isDirectory(OUTPUT_DIR)) {
            try (var entries = Files.walk(OUTPUT_DIR)) {
                for (Path entry : entries.sorted(Comparator.reverseOrder()).toList()) {
                    if (!entry.equals(OUTPUT_DIR)) Files.delete(entry);
                }
            }
        }
        Files.createDirectories(OUTPUT_DIR);
    }

    public static void writePage(String templateName, String html) throws IOException {
        writeFile(templateName.replace(".jinja", ".html"), html);
    }

    public static void writeFile(String fileName, String html) throws IOException {
        Files.createDirectories(OUTPUT_DIR);
        String page = html.contains("<!DOCTYPE") ? html : "<!DOCTYPE html>\n" + html;
        Files.writeString(OUTPUT_DIR.resolve(fileName), page);
    }

    public static void copySupportFiles() throws IOException {
        copy(Path.of("app.py"), OUTPUT_DIR.resolve("app.py"));
        copy(Path.of("static", "style.css"), OUTPUT_DIR.resolve("style.css"));
        copy(Path.of("static", "script.js"), OUTPUT_DIR.resolve("script.js"));

        copyTree(Path.of("static"), OUTPUT_DIR.resolve("static"));
    }

    private static void copy(Path source, Path target) throws IOException {
        if (!Files.exists(source)) return;
        Files.createDirectories(target.getParent());
        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
    }

    private static void copyTree(Path source, Path target) throws IOException {
        if (!Files.isDirectory(source)) return;
        try (var entries = Files.walk(source)) {
            for (Path entry : entries.toList()) {
                Path destination = target.resolve(source.relativize(entry).toString());
                if (Files.isDirectory(entry)) Files.createDirectories(destination);
                else copy(entry, destination);
            }
        }
    }
}
