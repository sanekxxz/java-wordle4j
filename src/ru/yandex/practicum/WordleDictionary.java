package ru.yandex.practicum;

import java.util.List;
import java.util.Random;


public class WordleDictionary {

    private final List<String> words;


    public WordleDictionary(List<String> words) {
        this.words = List.copyOf(words);
    }

    public List<String> getWords() {
        return words;
    }




    public String comparesWords(final String string, final String answer) {

        StringBuilder result = new StringBuilder("-----");
        StringBuilder answerCopy = new StringBuilder(answer);

        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == answer.charAt(i)) {
                result.setCharAt(i, '+');
                answerCopy.setCharAt(i, '*');
            }
        }

        for (int i = 0; i < string.length(); i++) {

            if (result.charAt(i) == '+') {
                continue;
            }

            char symbol = string.charAt(i);
            int index = answerCopy.indexOf(String.valueOf(symbol));

            if (index != -1) {
                result.setCharAt(i, '^');
                answerCopy.setCharAt(index, '*');
            }
        }

        return result.toString();
    }

    public String randomWordle() {
        Random random = new Random();
        int randomInt = random.nextInt(words.size());
        return words.get(randomInt);
    }
}
