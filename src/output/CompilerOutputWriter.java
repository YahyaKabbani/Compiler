package output;

import ast.ASTNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public final class CompilerOutputWriter {
    private static final Path DIR = Path.of("compiler_output");

    private CompilerOutputWriter() { }

    public static void writeAstJson(String fileName, ASTNode root) throws IOException {
        Files.createDirectories(DIR);
        Files.writeString(DIR.resolve(fileName), root.toJson());
    }

    public static void writeAstJson(String fileName, Map<String, ASTNode> trees) throws IOException {
        Files.createDirectories(DIR);
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (Map.Entry<String, ASTNode> entry : trees.entrySet()) {
            if (!first) sb.append(',');
            first = false;
            sb.append('"').append(entry.getKey()).append("\":").append(entry.getValue().toJson());
        }
        sb.append('}');
        Files.writeString(DIR.resolve(fileName), sb.toString());
    }

    public static void writeGenerationLog(List<String> lines) throws IOException {
        Files.createDirectories(DIR);
        Files.writeString(DIR.resolve("generation_log.txt"), String.join("\n", lines) + "\n");
    }
}
