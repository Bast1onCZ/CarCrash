package main.game.alert;

import main.game.alert.AlertPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import main.game.GameComponent;
import utility.GraphicsUtility;
import utility.Switcher;
import utility.intervals.IntervalChangeableInteger;

public class LostHearthPanel extends AlertPanel implements KeyListener {
    
    private static final int MARGIN_UP = 60;
    private static final int FONT_HEIGHT = 24;
    private static final int SPACE_BETWEEN_HEARTHS = 15;
    
    private Image hearthImg;
    private int imageHeight,
                imageWidth;
    
    private Timer timer;
    private IntervalChangeableInteger animationStateLength = new IntervalChangeableInteger(50, 1000);
    private Switcher animationSwitcher = new Switcher();
    private long lastAnimationChange;
    
    public LostHearthPanel(GameComponent game, Dimension size) {
        super(game, size, BorderFactory.createLineBorder(Color.WHITE, 5));
        
        this.hearthImg = new ImageIcon(getClass().getResource("hearth.png")).getImage();
        this.imageHeight = hearthImg.getHeight(game);
        this.imageWidth = hearthImg.getWidth(game);
        
        this.timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long now = System.currentTimeMillis();
                if(now - lastAnimationChange >= animationStateLength.getValue()) {
                    animationSwitcher.switchState();
                    lastAnimationChange = now;
                    
                    if(animationSwitcher.getState() == true) {
                        animationStateLength.setValue((int) (animationStateLength.getValue() / 2d));
                        
                        // Ending alert panel
                        if(animationStateLength.getValue() == 50) {
                            restartGame();
                        }
                    }
                    
                    repaint();
                }
            }
        });
        
        this.setBackground(Color.BLACK);
    }
    
    private void restartGame() {
        this.setState(State.OFF);
                            
        // Switching on the game
        game.respawnPlayer();
        game.setState(State.ON);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(this.getState() == State.OFF)
            return;
        
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, FONT_HEIGHT);
        g.setFont(font);
        g.setColor(Color.RED);
        
        FontMetrics fm = g.getFontMetrics();
        
        String s1 = "NABOURAL JSI!";
        g.drawString(s1, GraphicsUtility.getPosForMiddleObject(this.getWidth(), fm.stringWidth(s1)), MARGIN_UP);
        
        int health = game.getHealth();
        int imageStartX = GraphicsUtility.getPosForMiddleObject(this.getWidth(), (imageWidth + SPACE_BETWEEN_HEARTHS) * health + imageWidth);
        int imageY = MARGIN_UP + GraphicsUtility.getPosForMiddleObject(this.getHeight() - MARGIN_UP - fm.getHeight(), imageHeight / 2);
        
        for(int i = 0; i < health; i++) {
            int x = imageStartX + (i * (imageWidth + SPACE_BETWEEN_HEARTHS));
            g.drawImage(hearthImg, x, imageY, this);
        }
        
        if(animationSwitcher.getState() == true)
            g.drawImage(hearthImg, imageStartX + (imageWidth + SPACE_BETWEEN_HEARTHS) * health, imageY, this);
        
    }

    @Override
    public void setState(State state) {
        super.setState(state);
        
        if(state == State.ON) {
            game.setState(State.OFF);
            
            this.animationStateLength.setValue(Integer.MAX_VALUE);
            this.animationSwitcher.setState(true);
            this.lastAnimationChange = System.currentTimeMillis();
            this.timer.start();
        } else {
            this.timer.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(this.getState() == State.OFF)
            return;
        
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            restartGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    
}
