public class GameRecord implements Comparable<GameRecord> {
    private final String playerId;
    private final int score;

    // Constructor to initialize a new game record
    public GameRecord(String playerId, int score) {
        this.playerId = playerId;
        this.score = score;
    }

    // Getter method of PlayerID
    public String getPlayerId() {
        return playerId;
    }
    // Getter method to score
    public int getScore() {

        return score;
    }

    // Method to compare two game records
    @Override
    public int compareTo(GameRecord other) {
        return Integer.compare(other.score, this.score);  // Sort in descending order
    }
    // Method to create a string
    @Override
    public String toString() {
        String playerDisplay = "Player ID: " + playerId;
        String scoreDisplay = "Score: " + score;
        String fullDisplay = playerDisplay + ", " + scoreDisplay;
        return fullDisplay;



    }
}
