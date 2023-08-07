package pikachu;

public class Player {
    private String playerName;
    private int score;

    public Player(String playerName) {
        this.playerName = playerName;
        this.score = 0;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerScore() {
        return score;
    }

    public void increasePlayerScore(int value) {
        this.score += value;
    }

    public void resetPlayerScore() {
        this.score = 0;
    }
}
