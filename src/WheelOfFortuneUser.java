public class WheelOfFortuneUser extends WheelOfFortune{
    private Scanner scanner = new Scanner(System.in);

    @Override
    public GameRecord play() {
        return new GameRecord(playerId(), score);
    }


    @Override
    public boolean playAgain() {
        System.out.println("Do you want to play again? (yes/no)");
        String response = scanner.next().toLowerCase();
        return response.equals("yes");
    }

    public static void main(String[] args) {
        WheelOfFortuneUserGame userGame = new WheelOfFortuneUserGame();

        do {
            GameRecord gameRecord = userGame.play();
            userGame.allGamesRecord.add(gameRecord);
        } while (userGame.playAgain());

        userGame.displayResults();
    }
}
