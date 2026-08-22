package ru.yandex.practicum;

import java.util.*;

public class HintState {

    private final Set<Character> absentLetters = new HashSet<>(); // Символы которых нет
    private final Set<Character> presentLetters = new HashSet<>(); // Символы, которые есть
    private final Map<Integer, Character> correctPositions = new HashMap<>(); // Позиция символы правильная
    private final List<String> words;

    public HintState(final List<String> words) {
        this.words = new ArrayList<>(words);
    }

    public String characterSelection(final List<String> string, final String answer) {

        for (String a : string) {
            for (int i = 0; i < a.length(); i++) {
                char number = a.charAt(i);
                char numberAnswer = answer.charAt(i);
                int symbol = answer.indexOf(number);
                if (symbol != -1) {
                    if (a.indexOf(number) == symbol && number == numberAnswer) {
                        correctPositions.put(i, a.charAt(i));
                        presentLetters.add(number);
                    } else {
                        presentLetters.add(number);
                    }
                } else {
                    absentLetters.add(number);
                }
            }
        }

        if (!absentLetters.isEmpty() || !correctPositions.isEmpty() || !presentLetters.isEmpty()) {
            words.remove(string);
            wordSearch();
        }

        return randomWordle();
    }

    private void wordSearch() {

        List<String> wordsOther = new ArrayList<>(words);
        Set<String> resultSort = new HashSet<>();

        if (!absentLetters.isEmpty()) {
            for (char a : absentLetters) {
                for (String word : words) {
                    if (word.indexOf(a) != -1) {
                        wordsOther.remove(word);
                    }
                }
            }
            resultSort.addAll(wordsOther);
        }

        if (!presentLetters.isEmpty()) {
            for (char a : presentLetters) {
                for (String word : wordsOther) {
                    if (word.indexOf(a) >= 0) {
                        resultSort.add(word);
                    } else {
                        resultSort.remove(word);
                    }
                }
                wordsOther.clear();
                wordsOther.addAll(resultSort);
            }
        }

        if (!correctPositions.isEmpty()) {
            for (Map.Entry<Integer, Character> entry :correctPositions.entrySet()) {

                for (String word : wordsOther) {

                    int position = entry.getKey();
                    char expectedChar = entry.getValue();

                    if (word.charAt(position) == expectedChar) {
                        resultSort.add(word);
                    } else {
                        resultSort.remove(word);
                    }
                }
                wordsOther.clear();
                wordsOther.addAll(resultSort);
            }
        }

        words.clear();
        words.addAll(resultSort);
    }

    private String randomWordle() {
        Random random = new Random();
        int randomInt = random.nextInt(words.size());
        return words.get(randomInt);
    }
}
