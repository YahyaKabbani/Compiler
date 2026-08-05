package output;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public final class OutputWriter {
    private static final Path OUTPUT_DIR = Path.of("output");

    private OutputWriter() { }

    public static void clean() throws IOException {
        if (Files.isDirectory(OUTPUT_DIR)) {
            try (var entries = Files.list(OUTPUT_DIR)) {
                for (Path entry : entries.toList()) Files.delete(entry);
            }
        }
        Files.createDirectories(OUTPUT_DIR);
    }

    public static void writePage(String templateName, String html) throws IOException {
        Files.createDirectories(OUTPUT_DIR);
        String page = html.contains("<!DOCTYPE") ? html : "<!DOCTYPE html>\n" + html;
        Files.writeString(OUTPUT_DIR.resolve(templateName.replace(".jinja", ".html")), page);
    }

    public static void copySupportFiles() throws IOException {
        copy(Path.of("app.py"), "app.py");
        copy(Path.of("static", "style.css"), "style.css");
        copy(Path.of("static", "script.js"), "script.js");
    }

    private static void copy(Path source, String targetName) throws IOException {
        if (!Files.exists(source)) return;
        Files.copy(source, OUTPUT_DIR.resolve(targetName), StandardCopyOption.REPLACE_EXISTING);
    }
}
