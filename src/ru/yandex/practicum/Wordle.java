package ru.yandex.practicum;

import log.LogFile;

import java.util.Scanner;

public class Wordle {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {
            LogFile logFile = new LogFile();
            WordleDictionaryLoader wordleDictionaryLoader = new WordleDictionaryLoader();
            WordleDictionary wordleDictionary = wordleDictionaryLoader.loadFile("words_ru.txt");
            WordleGame wordleGame = new WordleGame(wordleDictionary);
            logFile.createFileLog();

            while (!wordleGame.isGameOwer()) {
                try {
                    String input;
                    System.out.println("Введите слова: ");
                    input = scanner.nextLine();
                    wordleGame.analyzeString(input);
                } catch (WordNotFoundInDictionary e) {
                    LogFile.record(e.getMessage());
                }
            }

        } catch (WordNotFoundInDictionary e) {
            LogFile.record(e.getMessage());
        }

    }

}
