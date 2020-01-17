package main.game;

/**
 * Object can be turned on or off
 * @author Miroslav
 */
public interface Switchable {
    
    public State getState();
    
    public void setState(State state);
    
    public enum State {
        ON,
        OFF;
    }
}
