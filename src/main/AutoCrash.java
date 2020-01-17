package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JComponent;
import main.game.GameComponent;
import javax.swing.JFrame;
import main.game.Switchable;

public class AutoCrash extends JFrame {
    
    private JComponent panel;
    
    public AutoCrash() {
        this.setTitle("AutoCrash");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setFocusable(true);
        this.setResizable(false);
        
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
               for(KeyListener l : panel.getKeyListeners()) {
                   l.keyTyped(e);
               }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                for(KeyListener l : panel.getKeyListeners()) {
                   l.keyPressed(e);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                for(KeyListener l : panel.getKeyListeners()) {
                   l.keyReleased(e);
               }
            }
        });
        
        setDown();
    }
    
    public void setUp() {
        Difficulty difficulty = ((MenuPanel) panel).getDifficulty();
        clearPanel();
        
        panel = new GameComponent(this, difficulty);
        this.add(panel);;
        this.pack();
        
        // Starting game
        ((GameComponent)panel).setState(Switchable.State.ON);
    }
    
    /**
     * Sets up the game
     */
    public void setDown() {
        clearPanel();
        
        panel = new MenuPanel(this);
        this.add(panel);
        this.pack();
    }
    
    private void clearPanel() {
        if(panel != null) {
            this.getContentPane().removeAll();
        }
    }
    
    public static void main(String[] args) {
        new AutoCrash().setVisible(true);
    }
    
}
