package SpaceSurvivorGame.Managers;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

/**
 * Sound manager class manages sounds in the game.
 */
public class SoundManager {
    private static final String filePath = "src/SpaceSurvivorGame/GameSounds/";
    private static Clip playerSootClip;
    private static Clip PlayerDeadClip;
    private static Clip asteroidExplosionClip;
    private static Clip mainMusicClip;

    // initializes sound clips at class loading time.
    static {
        playerSootClip = loadClipSound("ShootingSound");
        PlayerDeadClip = loadClipSound("PlayerDeadSound");
        asteroidExplosionClip = loadClipSound("AsteriodExplosition");
        mainMusicClip = loadClipSound("MainGameMusic");
    }

    /**
     * Loads a sound clip from a file.
     *
     * @param file The name of the sound file.
     * @return A Clip object
     */
    private static Clip loadClipSound(String file) {
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath + file + ".wav"));
            clip.open(audioInputStream);
            if (file.equalsIgnoreCase("ShootingSound")
                    || file.equalsIgnoreCase("PlayerDeadSound")
                    || file.equalsIgnoreCase("AsteriodExplosition")) {
                setVolume(clip, 6f);
            }

            if (file.equalsIgnoreCase("MainGameMusic")) {
                setVolume(clip, -6.0f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clip;
    }

    /**
     * Adjusts the volume of a sound clip.
     *
     * @param clip     The Clip object whose volume is to be adjusted.
     * @param decibels The wanted volume level.
     */
    public static void setVolume(Clip clip, float decibels) {
        if (clip == null) return;

        try {
            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            gainControl.setValue(decibels);
        } catch (IllegalArgumentException e) {
            System.err.println(e);
        }
    }

    /**
     * Plays a given sound clip once.
     *
     * @param clip The Clip to play.
     */
    private static void playClipSound(Clip clip) {
        if (clip == null) return;
        clip.setFramePosition(0);
        clip.start();
    }

    /**
     * Loops a given sound clip.
     *
     * @param clip The Clip to loop.
     */
    private static void loopClipSound(Clip clip) {
        if (clip == null) return;
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Stops a given sound clip.
     *
     * @param clip The Clip to stop.
     */
    private static void stopClipSound(Clip clip) {
        if (clip == null) return;
        clip.stop();
        clip.setFramePosition(0);
    }

    /**
     * Plays the shooting sound effect.
     */
    public static void playShootingSound() {
        playClipSound(playerSootClip);
    }

    /**
     * Plays the player/enemy death sound effect.
     */
    public static void playPlayerDeadSound() {
        playClipSound(PlayerDeadClip);
    }


    /**
     * Plays the asteroid explosion sound effect.
     */
    public static void playAsteroidExplosionSound() {
        playClipSound(asteroidExplosionClip);
    }

    /**
     * Start looping the main game music.
     */
    public static void startMainMusic() {
        loopClipSound(mainMusicClip);
    }

    /**
     * Stops the main game music.
     */
    public static void stopMainMusic() {
        stopClipSound(mainMusicClip);
    }
}