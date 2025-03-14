package AsteriodGame;

import java.io.*;
import java.util.*;

public class HighScoreManager {
    private static final String HIGH_SCORE_FILE = "highscores.txt";
    private static final int MAX_SCORES = 3;
    private List<HighScore> highScores;

    public HighScoreManager() {
        highScores = new ArrayList<>();
        loadScores();
    }

    public List<HighScore> getHighScores() {
        return highScores;
    }

    public boolean isNewHighScore(int score) {
        if (highScores.size() < MAX_SCORES) return true;
        return score > highScores.get(highScores.size()-1).score;
    }

    public void addHighScore(HighScore highScore) {
        highScores.add(highScore);
        Collections.sort(highScores);
        if (highScores.size() > MAX_SCORES) {
            highScores = highScores.subList(0, MAX_SCORES);
        }
        updateScores();
    }

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
            while((line = br.readLine()) != null) {
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
