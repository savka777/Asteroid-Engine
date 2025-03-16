package SpaceSurvivorGame.HighScore;

public class HighScore implements Comparable<HighScore> {
    public String username;
    public int score;

    public HighScore(String name, int score) {
        this.username = name;
        this.score = score;
    }

    @Override
    public int compareTo(HighScore other) {
        return other.score - this.score;
    }

    @Override
    public String toString() {
        return username + "   " + score;
    }
}
