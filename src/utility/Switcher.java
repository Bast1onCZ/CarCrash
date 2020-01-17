package utility;

/**
 * Simple object that invokes between two methods after changing a state
 * @author Miroslav
 */
public class Switcher {
    
    protected boolean state;
    
    public Switcher() {
        this(true);
    }
    
    public Switcher(boolean state) {
        this.state = state;
    }
    
    @Override
    public String toString() {
        return "Switcher{actualState="+ state +"}";
    }
    
    public boolean getState() {
        return state;
    }
    
    public void setState(boolean state) {
        this.state = state;
    }
    
    public void switchState() {
        state = !state;
    }
    
}
