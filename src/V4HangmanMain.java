import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class V4HangmanMain {

    private String phrase;
    private StringBuilder hiddenPhrase;
    private StringBuilder previousGuesses;

    public V4HangmanMain() {
        List<String> phraseList = null;
        try {
            phraseList = Files.readAllLines(Paths.get("phrases.txt"));
        } catch (IOException e) {
            System.out.println(e);
        }

        this.phrase = randomPhrase(phraseList);
        this.hiddenPhrase = createHiddenPhrase(this.phrase);
        this.previousGuesses = new StringBuilder();
    }

    public static void main(String[] args) {

        System.out.println(" *** Welcome to the Wheel of Fortune V4 *** \r\n" +
                "Follow the rules: \r\n" +
                "1. Guess a letter you guess in the hidden phrase and press Enter.\r\n" +
                "2. The game calculates your guess. And get to you know what next step.\r\n" +
                "3. Keep playing to win(open every single hidden character) this game.\r\n");

        V4HangmanMain hangman = new V4HangmanMain();
        BotPlayer bot = new BotPlayer();


        System.out.println("Here is a random phrase.");
        System.out.println("Hidden code: " + hangman.hiddenPhrase);

        int nChance = 10;  // default number of chances

        while (nChance > 0) {
            System.out.println(nChance + " chances left :");
            System.out.println("Guessed code: " + hangman.hiddenPhrase);

            char guessChar = bot.getGuess();

           int matchCount = hangman.processGuess(guessChar);
            if (matchCount == 0) {
                nChance--;
                hangman.previousGuesses.append(guessChar).append(" ");
                System.out.println("INCORRECT Guess by Bot. " + nChance + " chances left.");
            }
            else {
                System.out.println("CORRECT guess by Bot.");
            }

            if (hangman.previousGuesses.length() > 0) {
                System.out.println("Incorrect guesses by Bot: " + hangman.previousGuesses.toString());
            }

            if (!hangman.hiddenPhrase.toString().contains("*")) {
                System.out.println("Congratulations. Bot guessed the hidden sentence: " + hangman.hiddenPhrase);
                break;
            }
        }

        if (nChance == 0) {
            System.out.println("Bot failed. The hidden phrase was: " + hangman.phrase);
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
            }
            else {
                hiddenCode.append(' ');
            }
        }
        return hiddenCode;
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


    //guess A to Z bot
    static class BotPlayer {
        private char currentGuess = 'a'; // start from 'a'

        public char getGuess() {
            return currentGuess++;
        }
    }
}