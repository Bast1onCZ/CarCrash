package main.game;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import utility.GraphicsUtility;
import utility.intervals.IntervalChangeableInteger;

public class HearthCounter implements Paintable {

    public static final int LEFT_MARGIN = 30;
    public static final int UP_MARGIN = 60;
    
    public static final int HEARTH_WIDTH = 50;
    public static final int HEARTH_HEIGHT = 55;
    public static final int SPACE_BETWEEN_HEARTHS = 8;
    
    private Image hearth;
    private IntervalChangeableInteger health;
    
    public HearthCounter(int maxHealth) {
        this.hearth = new ImageIcon(getClass().getResource("hearth.png")).getImage();
        this.health = new IntervalChangeableInteger(0, maxHealth, maxHealth);
    }
    
    @Override
    public void paint(Graphics g, JComponent panel) {
        int x = GraphicsUtility.getPosForMiddleObject(200 - GameComponent.BARRIER_PERIOD, 50);
        for(int i = 0; i < health.getValue(); i++) {
            int y = UP_MARGIN + i*(HEARTH_HEIGHT + SPACE_BETWEEN_HEARTHS);
            
            g.drawImage(hearth, x, y, panel);
        }
    }
    
    public int getHealth() {
        return health.getValue();
    }
    
    /**
     * Removes 1 hearth
     */
    public void removeHearth() {
        health.remove(1);
    }
    
    public boolean isDead() {
        return health.getValue() == 0;
    }
    
}
