package main.game.alert;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.border.Border;
import main.game.GameComponent;
import main.game.Switchable;
import utility.GraphicsUtility;

public abstract class AlertPanel extends JPanel implements Switchable {

    private State state = State.OFF;
    
    protected GameComponent game;
    
    private Border border;
    
    public AlertPanel(GameComponent game, Dimension size, Border border) {
        this.game = game;
        
        this.border = border;
        
        double width = size.getWidth();
        double height = size.getHeight();
        this.setBounds((int)GraphicsUtility.getPosForMiddleObject(game.getPreferredSize().getWidth(), width),
                        (int) GraphicsUtility.getPosForMiddleObject(game.getPreferredSize().getHeight(), height),
                            (int) width, (int) height);
        
        this.setOpaque(false);
    }
    
    @Override
    public State getState() {
        return state;
    }

    /**
     * Changes the panel visibility
     * @param state
     */
    @Override
    public void setState(State state) {
        this.state = state;
        if(state == State.ON) {
            this.setBorder(border);
            this.setOpaque(true);
        } else {
            this.setBorder(null);
            this.setOpaque(false);
        }
    }
    
}
