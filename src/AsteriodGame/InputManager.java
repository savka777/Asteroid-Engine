package AsteriodGame;

import java.awt.event.*;

public class InputManager extends KeyAdapter implements Controller {

    Action action;

    public InputManager() {
        action = new Action();
    }

    @Override
    public Action action() {
        return action;
    }

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
