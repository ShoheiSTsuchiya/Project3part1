

public class VowelFirstGuessStrategy {

    private String playerId;
    private int nextVowelIndex = 0;
    private int nextConsonantIndex = 0;
    private char[] vowels = {'a', 'e', 'i', 'o', 'u'};


    @Override
    public char nextGuess() {
        if (nextVowelIndex < vowels.length) {
            return vowels[nextVowelIndex++];
        } else if (nextConsonantIndex < consonants.length) {
            return consonants[nextConsonantIndex++];
        } else {
            throw new IllegalStateException("All characters have been guessed");
        }
    }


    @Override
    public String playerId() {
        return playerId;
    }

    @Override
    public void reset() {
        nextVowelIndex = 0;
        nextConsonantIndex = 0;
}
