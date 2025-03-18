package SpaceSurvivorGame.HighScore;

/**
 * High score class represents the high score of a player in the game.
 */
public class HighScore implements Comparable<HighScore> {
    public String username;
    public int score;

    /**
     * Constructs a HighScore with a username and score.
     *
     * @param name  Username of the player.
     * @param score The score achieved by player.
     */
    public HighScore(String name, int score) {
        this.username = name;
        this.score = score;
    }

    /**
     * Compares this high score entry with another.
     *
     * @param other Another HighScore to compare with.
     * @return Negative, zero, or positive if this score is greater than,
     * equal to, or less than the other.
     */
    @Override
    public int compareTo(HighScore other) {
        return other.score - this.score;
    }

    @Override
    public String toString() {
        return username + "   " + score;
    }
}
