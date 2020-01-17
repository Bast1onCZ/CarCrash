package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import utility.GraphicsUtility;

public class MenuPanel extends JPanel {
    
    private AutoCrash program;
    
    private Difficulty difficulty = Difficulty.NORMAL;
    
    public MenuPanel(AutoCrash program) {
        this.program = program;
        
        this.setPreferredSize(new Dimension(400, 600));
        this.setBackground(Color.BLACK);
        
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {
                    case KeyEvent.VK_ENTER:
                        program.setUp();
                        break;
                    case KeyEvent.VK_LEFT:
                        changeDifficulty(-1);
                        break;
                    case KeyEvent.VK_RIGHT:
                        changeDifficulty(1);
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }
    
    public Difficulty getDifficulty() {
        return difficulty;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        g.setColor(Color.RED);
        GraphicsUtility.printCenter(this, g, "ZAČÍT HRU", 50);
        GraphicsUtility.printCenter(this, g, "Enter", 75);
        
        GraphicsUtility.printCenter(this, g, "OBTÍŽNOST", 150);
        GraphicsUtility.printCenter(this, g, "<   "+ difficulty +"   >", 175);
    }
    
    private void changeDifficulty(int change) {
        Difficulty newDiff = Difficulty.getByValue(difficulty.getValue() + change);
        if(newDiff != null)
            difficulty = newDiff;
        
        repaint();
    }
    
}
