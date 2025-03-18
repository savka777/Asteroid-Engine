package SpaceSurvivorGame.Controllers;

/**
 * Interface that defines the control mechanics for the game objects that are controlled.
 * Implementing classes must define their own implementation of the action method.
 */
public interface Controller {
    public Action action();
}
