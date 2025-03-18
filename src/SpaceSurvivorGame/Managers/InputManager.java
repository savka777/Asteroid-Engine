package SpaceSurvivorGame.Managers;

import SpaceSurvivorGame.Controllers.Action;
import SpaceSurvivorGame.Controllers.Controller;

import java.awt.event.*;

/**
 * Handles keyboard input for controlling a ship.
 */
public class InputManager extends KeyAdapter implements Controller {
    Action action;
    public InputManager() {
        action = new Action();
    }

    /**
     * Returns the current action state based on input.
     */
    @Override
    public Action action() {
        return action;
    }

    /**
     * Handles key press events to update the action state.
     *
     * @param e the key event that been pressed.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP) {
            this.action.thrust = 1;
        }

        if (key == KeyEvent.VK_LEFT) {
            this.action.turn = -1;
        }

        if (key == KeyEvent.VK_RIGHT) {
            this.action.turn = +1;
        }

        if (key == KeyEvent.VK_DOWN) {
            this.action.thrust = -1;
        }

        if (key == KeyEvent.VK_SPACE) {
            this.action.shoot = true;

        }
    }

    /**
     * Handles key release events to reset action states when keys are released.
     *
     * @param e the key event containing the released key.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            this.action.thrust = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            this.action.turn = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

            this.action.turn = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            this.action.shoot = false;
        }
    }
}
