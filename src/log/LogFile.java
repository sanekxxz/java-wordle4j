package log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class LogFile {

    private static final Path path = Paths.get("log.txt");

    public void createFileLog() {
        try {
            Files.createFile(path);
            record("Лог-файл успешно создан!");
        } catch (FileAlreadyExistsException e) {
            clearLog();
            record("Лог-файл уже существует!");
        } catch (IOException e) {
            record("Не удалось создать Лог-файл!");
            throw new RuntimeException("Ошибка при создании лог-файла: " + path, e);
        }
    }

    public static void clearLog() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.toFile(), StandardCharsets.UTF_8, false))) {
            bufferedWriter.write("");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void record(String log) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.toFile(), StandardCharsets.UTF_8, true))) {
            bufferedWriter.write(log);
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Не удалось записать в файл: " + path, e);
        }
    }
}
