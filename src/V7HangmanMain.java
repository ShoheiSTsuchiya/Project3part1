import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class V7HangmanMain {

    private String phrase;
    private StringBuilder hiddenPhrase;
    private StringBuilder previousGuesses;
    private int score; //score
    private int consecutiveScore; // consecutive correct scores

    public V7HangmanMain() {
        List<String> phraseList = null;
        try {
            phraseList = Files.readAllLines(Paths.get("phrases.txt"));
        } catch (IOException e) {
            System.out.println(e);
        }

        this.phrase = randomPhrase(phraseList);
        this.hiddenPhrase = createHiddenPhrase(this.phrase);
        this.previousGuesses = new StringBuilder();
        this.score = 0;                     //score initialization
        this.consecutiveScore = 0;          // initialization of consecutive correct number
    }

    public static void main(String[] args) {

        System.out.println(" *** Welcome to the Wheel of Fortune V3 *** \r\n" +
                "Follow the rules: \r\n" +
                "1. Guess a letter you guess in the hidden phrase and press Enter.\r\n" +
                "2. The game calculates your guess. And get to you know what next step.\r\n" +
                "3. Keep playing to win(open every single hidden character) this game.\r\n" +
                "4. In the end, you will get your score. If you answer correct consecutively, your score will increase.\r\n");
        V7HangmanMain hangman = new V7HangmanMain();

        System.out.println("Here is a random phrase.");
        System.out.println("Hidden code is " + hangman.hiddenPhrase);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of chances you want to play with:");
        int nChance = scanner.nextInt();

        while (nChance > 0) {
            System.out.println(nChance + " chances left :");
            System.out.println("Guessed code: " + hangman.hiddenPhrase);

            char guessChar = hangman.getGuess(scanner);
            int matchCount = hangman.processGuess(guessChar);

            //new method called and update score here
            hangman.updateScore(matchCount);

            if (matchCount == 0) {
                nChance--;
                hangman.consecutiveScore = 0; // reset num of consecutive correct here
                hangman.previousGuesses.append(guessChar).append(" ");
                System.out.println("INCORRECT Guess! " + nChance + " chances left!");
            }
            else {
                System.out.println("CORRECT guess. current score: " + hangman.score);
            }

            if (hangman.previousGuesses.length() > 0) {
                System.out.println("Incorrect guesses: " + hangman.previousGuesses.toString());
            }

            if (!hangman.hiddenPhrase.toString().contains("*")) {
                System.out.println("Congratulations! You guessed the hidden sentence: " + hangman.hiddenPhrase);
                break;
            }
        }

        // declare the final score here
        System.out.println("Your final score is: " + hangman.score);

        if (nChance == 0) {
            System.out.println("Out of chances. The hidden sentence was: " + hangman.phrase);

        }

    }

    public String randomPhrase(List<String> phraseList) {
        Random rand = new Random();
        int r = rand.nextInt(phraseList.size());
        return phraseList.get(r);
    }

    public StringBuilder createHiddenPhrase(String phrase) {
        StringBuilder hiddenCode = new StringBuilder();
        for (int i = 0; i < phrase.length(); i++) {
            if (phrase.charAt(i) != ' ') {
                hiddenCode.append('*');
            } else {
                hiddenCode.append(' ');
            }
        }
        return hiddenCode;
    }

    public char getGuess(Scanner scanner) {
        System.out.println("Guess a character you think in hidden sentence :");
        String ch = scanner.next().toLowerCase();
        if (ch.length() == 1) {
            char guess = ch.charAt(0);
            if (Character.isLetter(guess)) {
                return guess;
            }
            else {
                System.out.println("Invalid input. Please enter a letter from a-z or A-Z.");
            }
        }
        else {
            System.out.println("Invalid input. Please enter a single character.");
        }
        return getGuess(scanner);
    }

    public int processGuess(char guessChar) {
        int matchCount = 0;
        for (int i = 0; i < phrase.length(); i++) {
            char strChar = phrase.charAt(i);
            if (Character.toLowerCase(guessChar) == Character.toLowerCase(strChar)) {
                matchCount++;
                hiddenPhrase.setCharAt(i, strChar);
            }
        }
        return matchCount;
    }

    private void updateScore(int matchCount) {
        if (matchCount == 0) {
            consecutiveScore = 0; // reset num of consecutive correct
            score--; // Decrease score for incorrect guess if desired
        } else {
            if (consecutiveScore > 1) {
                score *= (int)Math.pow(2, consecutiveScore - 1);
                consecutiveScore++;
            } else {
                score++;
                consecutiveScore = 1;
            }
        }
    }
}