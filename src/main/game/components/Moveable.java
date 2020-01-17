package main.game.components;

public interface Moveable {
    
    /**
     * Causes object to move by its base speed + boost
     * @param boost int adding to objects base speed
     */
    public void move(double boost);
    
}
