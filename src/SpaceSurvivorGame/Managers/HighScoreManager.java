package SpaceSurvivorGame.Managers;

import SpaceSurvivorGame.HighScore.HighScore;

import java.io.*;
import java.util.*;

/**
 * High score manager class to manage high scores in game.
 */
public class HighScoreManager {
    private static final String HIGH_SCORE_FILE = "highscores.txt";
    private static final int MAX_SCORES = 3;
    private List<HighScore> highScores;

    /**
     * Constructs a HighScoreManager and loads scores from a file.
     */
    public HighScoreManager() {
        highScores = new ArrayList<>();
        loadScores();
    }

    public List<HighScore> getHighScores() {
        return highScores;
    }

    /**
     * Checks if a given score is a new high score.
     *
     * @param score The score to check.
     * @return true if the score is as a new high score, false otherwise.
     */
    public boolean isNewHighScore(int score) {
        if (highScores.size() < MAX_SCORES) return true;
        return score > highScores.get(highScores.size() - 1).score;
    }

    /**
     * Adds a new high score and updates the stored scores.
     *
     * @param highScore The high score to add.
     */
    public void addHighScore(HighScore highScore) {
        highScores.add(highScore);
        Collections.sort(highScores);
        if (highScores.size() > MAX_SCORES) {
            highScores = highScores.subList(0, MAX_SCORES);
        }
        updateScores();
    }

    /**
     * Loads high scores from the file or initializes default scores if the file does not exist.
     */
    private void loadScores() {
        File file = new File(HIGH_SCORE_FILE);
        if (!file.exists()) {

            for (int i = 0; i < MAX_SCORES; i++) {
                highScores.add(new HighScore("XXX", 0));
            }
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length >= 2) {
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    highScores.add(new HighScore(name, score));
                }
            }
            Collections.sort(highScores);
            while (highScores.size() < MAX_SCORES) {
                highScores.add(new HighScore("---", 0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes the updated high scores to a file.
     */
    private void updateScores() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(HIGH_SCORE_FILE))) {
            for (HighScore hs : highScores) {
                bw.write(hs.username + " " + hs.score);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
