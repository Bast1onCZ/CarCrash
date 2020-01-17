package main.game;

import main.game.alert.LostHearthPanel;
import main.game.components.Lane;
import main.game.components.PlayerCar;
import main.game.components.Car;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import main.AutoCrash;
import main.Difficulty;
import main.game.alert.EndGamePanel;

public class GameComponent extends JLayeredPane implements Switchable {
    
    public static final int LEFT_MARGIN = 200;
    
    public static final int LOST_HEARTH_PANEL_WIDTH = 300;
    public static final int LOST_HEARTH_PANEL_HEIGHT = 200;
    
    public static final int END_GAME_PANEL_WIDTH = 450;
    public static final int END_GAME_PANEL_HEIGHT = 500;
    
    public static final int STRIP_LENGTH = 50;
    public static final int SPACE_BETWEEN_STRIPS = 40;
    public static final int STRIP_PERIOD = STRIP_LENGTH + SPACE_BETWEEN_STRIPS;
    public static final int STRIP_WIDTH = 4;
    
    public static final int BARRIER_SIDE = 30;
    public static final int SPACE_BETWEEN_BARRIERS = 10;
    public static final int BARRIER_PERIOD = BARRIER_SIDE + SPACE_BETWEEN_BARRIERS;
    
    private AutoCrash program;
    private GameComponent game;
    private Difficulty difficulty;
    
    private JPanel gamePanel;
    private LostHearthPanel hearthLostPanel;
    private EndGamePanel endGamePanel;
    
    private Image barrier;
    
    private State state = State.OFF;
    private HearthCounter hearthCounter;
    private List<Lane> lanes = new ArrayList<>();
    private PlayerCar player;
    
    private Timer moveTimer;
    private Timer spawnTimer;
    
    public GameComponent(AutoCrash program, Difficulty difficulty) {
        this.program = program;
        this.game = this;
        this.difficulty = difficulty;
        
        ImageIcon barrierIcon = new ImageIcon(this.getClass().getResource("barrier.png"));
        this.barrier = barrierIcon.getImage();
        
        Dimension dim = new Dimension(LEFT_MARGIN + (difficulty.getLanes() * Lane.WIDTH) + BARRIER_PERIOD + 100, 800);
        this.setPreferredSize(dim);
        this.setBackground(Color.BLACK);
        
        // Panels
        this.gamePanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Painting score
                Font f = new Font(Font.SANS_SERIF, Font.BOLD, 25);
                g.setFont(f);
                g.setColor(Color.RED);
                
                g.drawString("SKÓRE: "+ getPlayerScore(), 10, 35);
                
                // Painting GUI
                hearthCounter.paint(g, this);

                // Painting road
                paintRoad(g);

                // Painting cars
                for(Lane l : lanes) {
                    l.paint(g, this);
                }
            }
        };
        gamePanel.setBounds(0, 0, (int) dim.getWidth(), (int) dim.getHeight());
        gamePanel.setBackground(Color.BLACK);
        
        this.hearthLostPanel = new LostHearthPanel(this, new Dimension(LOST_HEARTH_PANEL_WIDTH, LOST_HEARTH_PANEL_HEIGHT));
        this.endGamePanel = new EndGamePanel(program, this, new Dimension(END_GAME_PANEL_WIDTH, END_GAME_PANEL_HEIGHT));
        
        this.add(gamePanel, 1, 0);
        this.add(hearthLostPanel, 2, 0);
        this.add(endGamePanel, 2, 0);
        
        // HearthCounter
        this.hearthCounter = new HearthCounter(difficulty.getStartHealth());
        
        // Lanes
        for(int i = 0; i < difficulty.getLanes(); i++) {
            Lane lane = new Lane(this, i);
            
            lanes.add(lane);
        }
        
        // Player
        int middleLaneOrder = Math.round(lanes.size() / 2);
        this.player = new PlayerCar(lanes.get(middleLaneOrder), difficulty.getBaseSpeed(), this);
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                player.keyPressed(e);
                hearthLostPanel.keyPressed(e);
                endGamePanel.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        // moving cars
        this.moveTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double carBoost = getCarBoost();
                
                for(Lane l : lanes) {
                    l.move(carBoost);
                    l.checkForDespawn();
                }
                
                Car c;
                if((c = player.hasCrashed()) != null) {
                        hearthCounter.removeHearth();
                        if(!hearthCounter.isDead())
                            hearthLostPanel.setState(State.ON);
                        else
                            endGamePanel.setState(State.ON);
                            
                }
                repaint();
            }
        });
        
        this.spawnTimer = new Timer(100, new ActionListener() {
            private Random r = new Random();
            private int lastDistance = -300;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                int diff = player.getTotalDistance() - lastDistance;
                
                if(diff > 250) {
                    lastDistance = player.getTotalDistance();
                    
                    List<Integer> emptyLanes = new ArrayList<>();
                    for(int i = 0; i < difficulty.getMaxEmptyLanes(); i++)
                        emptyLanes.add(r.nextInt(lanes.size()));
                    
                    for(Lane l : lanes) {
                        if(!emptyLanes.contains(l.getOrder())) {
                            Car c = new Car(l, difficulty.getBaseSpeed());
                            l.addCar(c);
                        }
                    }
                }
            }
        });
        spawnTimer.getActionListeners()[0].actionPerformed(null);
    }
    
    public PlayerCar getPlayer() {
        return player;
    }
    
    public int getPlayerScore() {
        return (player.getTotalDistance() / 200);
    }
    
    public List<Lane> getLanes() {
        return Collections.unmodifiableList(lanes);
    }
    
    /**
     * Clears all cars and sets player's boost to 0
     */
    public void respawnPlayer() {
        for(Lane l : lanes)
            l.removeAllCars();
        
        player.setBoost(0);
    }
    
    public int getHealth() {
        return hearthCounter.getHealth();
    }
    
    private double getCarBoost() {
        int playerBoost = player.getBoost();
        double distanceBoost = (player.getTotalDistance() / 100d) / 1.25d;
        
        return playerBoost + distanceBoost;
    }
    
    private void paintRoad(Graphics g) {
        g.setColor(Color.WHITE);
        
        int stripStartY = getStartY(g, STRIP_PERIOD);
        for(int i = 1; i < lanes.size(); i++) {
            int x = lanes.get(i).getXCoord() - (STRIP_WIDTH / 2);
            
            for(int y = stripStartY; y < this.getHeight(); y += STRIP_PERIOD) {
                g.fillRect(x, y, STRIP_WIDTH, STRIP_LENGTH);
            }
        }
        
        int barrierStartY = getStartY(g, BARRIER_PERIOD);
        for(int y = barrierStartY; y < this.getHeight(); y += BARRIER_PERIOD) {
            g.drawImage(barrier, LEFT_MARGIN - BARRIER_PERIOD, y, this);
            g.drawImage(barrier, LEFT_MARGIN + lanes.size() * Lane.WIDTH + SPACE_BETWEEN_BARRIERS, y, this);
        }
    }
    
    private int getStartY(Graphics g, int objectRepeatPeriod) {
        int distance = player.getTotalDistance();
        int currentDisplay = distance / this.getHeight();
        int periodOver = (currentDisplay * this.getHeight()) % (objectRepeatPeriod);
        int diffY = distance % this.getHeight() + periodOver;
        int startY = diffY - (this.getHeight());
        
        int repeat = startY / (-objectRepeatPeriod);
        startY += repeat * objectRepeatPeriod;
        
        return startY;
    }
    
    @Override
    public State getState() {
        return state;
    }
    
    @Override
    public void setState(State state) {
        if(this.state == state)
            return;
        
        switch(state) {
            case ON:
                moveTimer.start();
                spawnTimer.start();
                break;
            case OFF:
                moveTimer.stop();
                spawnTimer.stop();
                break;
            default:
                throw new NullPointerException("State can't be null");
        }
        
        this.state = state;
    }
    
}
