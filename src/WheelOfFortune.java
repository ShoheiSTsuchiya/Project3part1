import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

// Abstract class representing the game "Wheel of Fortune"
public abstract class WheelOfFortune extends Game {
    protected String phrase;
    protected StringBuilder hiddenPhrase;  // The hidden version of the phrase
    protected List<String> phrases;  // List of potential phrases to be guessed
    protected WheelOfFortunePlayer player;  // The player participating in the game

    // Constructor to initialize the game with a player
    public WheelOfFortune(WheelOfFortunePlayer player) {
        this.player = player;
        try {
            // Read a list of phrases from a file
            this.phrases = Files.readAllLines(Paths.get("phrases.txt"));
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, e.g., provide a default phrase in case the file is not found
            this.phrases = List.of("Empty(need to add phrases)");
        }
    }

    // Method to select a random phrase from the list and initialize the hidden phrase
    protected void selectRandomPhrase() {
        phrase = randomPhrase(phrases);
        hiddenPhrase = createHiddenPhrase(phrase);
    }

    // Abstract method to get a guess from the player, to be implemented by subclasses
    protected abstract char getGuess(String previousGuesses);

    // Main game loop, returns a record of the game after it's played
    @Override
    public GameRecord play() {
        int score = 0;  // Initialize score
        StringBuilder previousGuesses = new StringBuilder();  // Keep track of previous guesses

        // Continue playing while the phrase is not fully guessed
        while (hiddenPhrase.toString().contains("*")) {
            char guess = getGuess(previousGuesses.toString());  // Get a guess from the player
            previousGuesses.append(guess);  // Add the guess to the list of previous guesses

            int matchCount = processGuess(guess);  // Check how many times the guessed letter appears in the phrase
            if (matchCount > 0) {
                score += matchCount;  // Update the score based on the number of matches
            }
        }

        // Create and return a game record with the player's ID and final score
        GameRecord gameRecord = new GameRecord(player.playerId(), score);
        return gameRecord;
    }

    // Method to select a random phrase from the list and remove it to prevent repetition
    private String randomPhrase(List<String> phraseList) {
        Random rand = new Random();
        int index = rand.nextInt(phraseList.size());  // Select a random index
        String selectedPhrase = phraseList.get(index);  // Get the phrase at the random index
        phraseList.remove(index);  // Remove the selected phrase from the list
        return selectedPhrase;
    }

    // Method to create a hidden version of the phrase
    private StringBuilder createHiddenPhrase(String phrase) {
        StringBuilder hidden = new StringBuilder();
        char[] chars = phrase.toCharArray();  // Convert the phrase to a char array

        for (char c : chars) {
            if (Character.isLetter(c)) {
                hidden.append('*');  // Replace letters with '*'
            } else if (c == ' ' || c == '\'' || c == '’') {
                hidden.append(c);  // Keep spaces and apostrophes as they are
            } else {
                hidden.append('*');  // Replace other characters with '*'
            }
        }

        return hidden;
    }

    // Method to process a player's guess and update the hidden phrase and score
    protected int processGuess(char guess) {
        int matchCount = 0;
        for (int i = 0; i < phrase.length(); i++) {
            // Check each letter in the phrase
            if (Character.toLowerCase(guess) == Character.toLowerCase(phrase.charAt(i))) {
                hiddenPhrase.setCharAt(i, phrase.charAt(i));
                matchCount++;  // Increment the match count
            }
        }
        return matchCount;
    }

    // Getter method to retrieve the current state of the hidden phrase
    public String getHiddenPhrase() {
        return hiddenPhrase.toString();
    }

    // Setter method to manually set the phrase for the game (for testing or special cases)
    protected void setPhrase(String newPhrase) {
        phrase = newPhrase;
        hiddenPhrase = createHiddenPhrase(newPhrase);
    }
}
