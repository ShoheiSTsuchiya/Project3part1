import java.util.HashSet;
import java.util.Random;
import java.util.Set;

interface WheelOfFortunePlayer {
    char nextGuess();
    String playerId();
    void reset();
}

class SequentialAIPlayer implements WheelOfFortunePlayer {
    private char currentGuess = 'a';  // Fixed initialization of currentGuess

    @Override
    public char nextGuess() {
        if (currentGuess > 'z') {  // Added check for exceeding 'z'
            System.err.println("All letters have been guessed");
            return 0;
        }
        return currentGuess++;  // Increment currentGuess after returning its value
    }

    @Override
    public String playerId() {
        return "Sequential AI Player";
    }

    @Override
    public void reset() {
        currentGuess = 'a';  // Reset currentGuess to 'a' when the player is reset
    }
}

class RandomAIPlayer implements WheelOfFortunePlayer {
    private final Random random = new Random();
    private final Set<Character> guessedChars = new HashSet<>();

    @Override
    public char nextGuess() {
        char guess;
        if (guessedChars.size() == 26) {  // Added check for all letters guessed
            System.err.println("All letters have been guessed");
            return 0;
        }
        do {
            guess = (char) (random.nextInt(26) + 'a');
        } while (guessedChars.contains(guess));
        guessedChars.add(guess);
        return guess;
    }

    @Override
    public String playerId() {
        return "Random AI Player";
    }

    @Override
    public void reset() {
        guessedChars.clear();  // Clear the guessed characters when the player is reset
    }
}

class MostCommonAIPlayer implements WheelOfFortunePlayer {
    private char[] commonLetters = {'e', 'a', 'r', 'i', 'o', 't', 'n', 's', 'l', 'c'};
    private int currentIdx = 0;

    @Override
    public char nextGuess() {
        if (currentIdx >= commonLetters.length) {  // Added check for exceeding common letters array length
            System.err.println("All common letters have been guessed");
            return 0;
        }
        return commonLetters[currentIdx++];
    }

    @Override
    public String playerId() {
        return "Most Common AI Player";
    }

    @Override
    public void reset() {
        currentIdx = 0;  // Reset the current index when the player is reset
    }
}
