package AsteriodGame;
import java.awt.event.*;

public class BasicKeys extends KeyAdapter implements BasicController {

    Action action;

    public BasicKeys() {
        action = new Action();
    }

    @Override
    public Action action() {
        return action;
    }

    @Override
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_UP) {
            this.action.thrust = 1;
        }
        if(key == KeyEvent.VK_LEFT) {
            this.action.turn = -1;
        }
        if(key == KeyEvent.VK_RIGHT) {
            this.action.turn = +1;
        }
        if(key == KeyEvent.VK_SPACE) {
            this.action.shoot = true;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            this.action.thrust = 0;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            this.action.turn = 0;  // Changed from 1 to 0
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){

            this.action.turn = 0;  // Changed from -1 to 0
        }

        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            this.action.shoot = false;
        }
    }
}
