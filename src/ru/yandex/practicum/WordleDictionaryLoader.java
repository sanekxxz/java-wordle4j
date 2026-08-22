package ru.yandex.practicum;

import log.LogFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WordleDictionaryLoader {

    private final List<String> words = new ArrayList<>();

    public WordleDictionary loadFile(String filename) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(Path.of(filename).toFile(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                line = translation(line);

                if (!line.isBlank() && line.length() == 5) {
                    words.add(line);
                }
            }
        } catch (Exception e) {
            LogFile.record("Не удалось загрузить файл");
            throw new RuntimeException(filename, e);
        }

        return new WordleDictionary(words);
    }

    private String translation(final String string) {
        return string.trim().toLowerCase(Locale.ROOT).replace('ё', 'е');
    }
}
