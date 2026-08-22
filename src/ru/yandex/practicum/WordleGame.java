package ru.yandex.practicum;

import log.LogFile;

import java.util.*;


public class WordleGame {

    private static final int MAX_STEPS = 6;

    private static final int WORD_LENGTH = 5;

    private final String answer;

    private boolean gameOwer = false; //Проверка на конец игры

    private int steps; // Ходы пользователя

    private final StringBuilder lastResult = new StringBuilder();

    private final List<String> userString = new ArrayList<>(); //Введённые слова пользователям

    private final WordleDictionary dictionary;

    private final HintState hintState;

    public WordleGame(WordleDictionary dictionary) {
        if (dictionary == null || dictionary.getWords().isEmpty()) {
            throw new WordNotFoundInDictionary("Словарь пустой!");
        }

        this.dictionary = dictionary;
        this.steps = 0;
        this.answer = dictionary.randomWordle();
        this.hintState = new HintState(dictionary.getWords());
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isGameOwer() {
        return gameOwer;
    }


    public void analyzeString(final String input) {

        if (input.isBlank()) {
            hint(input);
            return;
        }
        if (input.length() != WORD_LENGTH) {
            System.out.println("Слова должно быть из 5 букв!");
            throw new WordNotFoundInDictionary("Слова должно быть из 5 букв!");
        }

        if (!dictionary.getWords().contains(input)) {
            System.out.println("Такого слова в словаре нету!");
            throw new WordNotFoundInDictionary("Такого слова в словаре нету!");
        }

        guessResult(input);
    }

    private void guessResult(final String input) {

        LogFile.record("Пользователь ввёл: " + input);
        if (input.equals(answer)) {
            System.out.println("Вы угадали слова, вы выйграли !");
            gameOwer = true;
            System.exit(0);
        }

        steps++;
        LogFile.record("У вас осталось попыток: " + (MAX_STEPS - steps));

        System.out.println("У вас осталось попыток: " + (MAX_STEPS - steps));


        if (steps == MAX_STEPS) {
            gameOwer = true;
            System.out.println("Вы не отгадали слова: " + answer);

            LogFile.record("Вы не отгадали слова: " + answer);
            throw new WordNotFoundInDictionary("У вас закончились ходы!");
        }

        lastResult.setLength(0);

        lastResult.append(dictionary.comparesWords(input, answer));

        userString.add(input);

        System.out.println("> " + lastResult);

    }

    private void hint(String input) {

        String hint = dictionary.randomWordle();

        if (!userString.isEmpty()) {
            String hintWords = hintState.characterSelection(userString, answer);
            LogFile.record("Подсказка: " + hintWords);

            System.out.println("Подсказка: " + hintWords);

            userString.add(hintState.characterSelection(userString, answer));
            guessResult(userString.getLast());
        } else {
            LogFile.record("Подсказка: " + hint);
            System.out.println("Подсказка: " + hint);

            userString.add(hint);
            guessResult(userString.getLast());
        }
    }
}
