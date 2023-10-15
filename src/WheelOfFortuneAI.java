public class WheelOfFortuneAI {
    private WheelOfFortunePlans aiPlan;

    public WheelOfFortuneAI(WheelOfFortunePlans aiPlan) {
        this.aiPlan = aiPlan;
    }

    @Override
    public char getGuess(String previousGuesses) {
        return aiPlan.nextGuess();
    }




    @Override
    public boolean playAgain() {
        return false;
    }
}
