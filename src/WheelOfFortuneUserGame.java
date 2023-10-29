import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WheelOfFortuneUserGame extends WheelOfFortune {

    private Scanner scanner; // Scanner to read input from the user
    private static final int MAX_WRONG_GUESSES = 5;
    private List<String> usedPhrases;// List to store phrases that have already been used in the game
    private int score;

    // Constructor
    public WheelOfFortuneUserGame(WheelOfFortunePlayer player) {
        super(player);
        this.scanner = new Scanner(System.in);
        this.usedPhrases = new ArrayList<>();
        // Check if there are any phrases available to play
        if (phrases.isEmpty()) {
            System.out.println("Error: No phrases are available to play the game.");
            System.exit(1);
        }
        selectRandomPhrase();// Select a random phrase for the game
        this.score = 0;

    }
    // Method to get the player's guess
    @Override
    protected char getGuess(String previousGuesses) {
        char guess = ' ';
        boolean isValidGuess = false;

        // Loop until a valid guess is entered
        while (!isValidGuess) {
            System.out.println("Current hidden phrase: " + getHiddenPhrase());
            System.out.println("Previous guesses: " + previousGuesses);
            System.out.print("Enter your guess: ");
            String input = scanner.next();
            // Check if input is a single letter
            if (input.length() == 1 && Character.isLetter(input.charAt(0))) {
                guess = Character.toUpperCase(input.charAt(0));

                // Check if the letter has not been guessed before
                if (previousGuesses.indexOf(guess) == -1) {
                    isValidGuess = true;
                } else {
                    System.out.println("You already guessed that letter. Please try a different one.");
                }
            } else {
                System.out.println("Invalid input. Please enter a single letter.");
            }
        }

        return guess;
    }
    // Method to play the game
    @Override
    public GameRecord play() {
        getPlayerId();  // Get the player's ID
        player.reset();  // Reset player's state
        this.score = 0;
        StringBuilder previousGuesses = new StringBuilder();
        int wrongGuesses = 0;

        while (hiddenPhrase.toString().contains("*") && wrongGuesses < MAX_WRONG_GUESSES) {
            char guess = getGuess(previousGuesses.toString());
            previousGuesses.append(guess);

            int matchCount = processGuess(guess);
            // Update score if there are matching letters
            if (matchCount > 0) {
                score += matchCount;
            } else {
                // Increment wrong guesses
                wrongGuesses++;
                System.out.println("Wrong guess! You have " + (MAX_WRONG_GUESSES - wrongGuesses) + " attempts left.");
            }
        }
        // Check the end game conditions
        if (wrongGuesses >= MAX_WRONG_GUESSES) {
            System.out.println("Game over! You've run out of attempts. The correct phrase was: " + phrase);
        } else {
            System.out.println("Congratulations! You've guessed the phrase: " + phrase);
        }

        return new GameRecord(player.playerId(), score);
    }


    // determine if the player wants to play again
    @Override
    protected boolean playNext() {
        if (usedPhrases.size() == phrases.size()) { // End game if all phrases have been used
            System.out.println("All phrases have been used. Game Over!");
            return false;
        }
        System.out.print("Do you want to play again? (yes/no): ");
        String response = scanner.next().toLowerCase();
        if ("yes".equals(response)) {
            getPlayerId();
            player.reset();  // Reset player's state
            selectRandomPhrase();
            return true;
        } else {
            return false;
        }
    }
    // Method to get the player's ID
    private void getPlayerId() {
        System.out.print("Enter your player ID: ");
        String newPlayerId = scanner.next();
        player = new WheelOfFortunePlayer() {
            @Override
            public char nextGuess() {
                return 'a';
            }
            @Override
            public String playerId() {
                return newPlayerId;
            }
            @Override
            public void reset() {}
        };

    }

    public static void main(String[] args) {

        WheelOfFortunePlayer player = new WheelOfFortunePlayer() {
            @Override
            public char nextGuess() {
                return 'a'; // This can be modified as needed
            }
            @Override
            public String playerId() {
                return null;
            }
            @Override
            public void reset() {}
        };

        WheelOfFortuneUserGame game = new WheelOfFortuneUserGame(player);
        AllGamesRecord allGamesRecord = game.playAll();

        // Print the average and top 3 scores at the end of the game
        System.out.println("Average score: " + allGamesRecord.average());
        System.out.println("Top 3 scores: " + allGamesRecord.highGameList(3));
    }
}
