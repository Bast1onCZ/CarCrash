package main.game.alert;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import main.AutoCrash;
import main.game.GameComponent;
import utility.GraphicsUtility;

public class EndGamePanel extends AlertPanel implements KeyListener {

    private AutoCrash program;
    
    public EndGamePanel(AutoCrash program, GameComponent game, Dimension size) {
        super(game, size, BorderFactory.createLineBorder(Color.WHITE, 5));
        
        this.program = program;
        
        this.setBackground(Color.BLACK);
    }
    
    @Override
    public void setState(State state) {
        super.setState(state);
        
        if(state == State.ON) {
            game.setState(State.OFF);
        } else {
            // game ends ...
        }
    }
    
    private void endGame() {
        program.setDown();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(getState() == State.OFF)
            return;
        
        Font f = new Font(Font.SANS_SERIF, Font.BOLD, 20);
        FontMetrics fm = g.getFontMetrics(f);
        g.setFont(f);
        g.setColor(Color.RED);
        
        String s1 = "PROHRAL JSI!";
        g.drawString(s1, GraphicsUtility.getPosForMiddleObject(this.getWidth(), fm.stringWidth(s1)), 100);
        
        String s2 = "Dosahl jsi skore: "+ game.getPlayerScore();
        g.drawString(s2, GraphicsUtility.getPosForMiddleObject(this.getWidth(), fm.stringWidth(s2)), 130);
        
        String s3 = "ENTER - Ukonceni hry";
        g.drawString(s3, GraphicsUtility.getPosForMiddleObject(this.getWidth(), fm.stringWidth(s3)), 400);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(this.getState() == State.OFF)
            return;
        
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
            endGame();
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    
}
