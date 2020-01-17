package main.game.components;

import main.game.components.Car;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import main.game.GameComponent;
import utility.GraphicsUtility;
import utility.intervals.IntervalChangeableInteger;

public class PlayerCar extends Car implements KeyListener {
    
    private IntervalChangeableInteger percentualBoost = new IntervalChangeableInteger(0, 100, 0);
    private IntervalChangeableInteger laneOrder;
    
    private GameComponent panel;
    
    private int totalDistance = 0;
    
    public PlayerCar(Lane lane, int baseSpeed, GameComponent panel) {
        super(lane, baseSpeed);
        
        this.carImg = new ImageIcon(this.getClass().getResource("blue_car.png")).getImage();
        
        
        this.laneOrder = new IntervalChangeableInteger(0, panel.getLanes().size() - 1, lane.getOrder());
        this.panel = panel;
    }
    
    @Override
    public String toString() {
        return "PlayerCar";
    }
    
    /**
     * Asks if this car is colliding with any other car
     * @return Car - the colliding one; otherwise null
     */
    public Car hasCrashed() {
        for(Car c : lane.getCars()) {
            if(this.isCollidingWith(c))
                return c;
        }
        
        return null;
    }
    
    public int getTotalDistance() {
        return totalDistance;
    }
    
    /**
     * Gets percentual boost of the car
     * @return int, percentual boost (100%-200%)
     */
    public int getBoost() {
        return 100 + percentualBoost.getValue();
    }
    
    public void setLane(Lane lane) {
        this.lane.removeCar(this);
        
        this.lane = lane;
        lane.addCar(this);
    }
    
    /**
     * Sets boost between 100% and 200%
     * @param boost
     */
    public void setBoost(int boost) {
        boost -= 100;
        percentualBoost.setValue(boost);
    }
    
    @Override
    public void move(double boost) {
        // Counting total distance
        int instantSpeed = (int) (baseSpeed * (boost / 100d));
        totalDistance += instantSpeed;
        
        // Displaying
        int zeroY = panel.getHeight();
        
        y = zeroY - 200 - percentualBoost.getValue();
    }
    
    @Override
    public void paint(Graphics g, JComponent panel) {
        int xDiff = GraphicsUtility.getPosForMiddleObject(Lane.WIDTH, Car.WIDTH);
        int laneX = lane.getXCoord();
        int x = laneX + xDiff;
        
        g.drawImage(carImg, x, y, panel);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        switch(key) {
            case KeyEvent.VK_UP:
                percentualBoost.add(5);
                break;
            case KeyEvent.VK_DOWN:
                percentualBoost.remove(5);
                break;
            case KeyEvent.VK_LEFT:
                laneOrder.remove(1);
                setLane(panel.getLanes().get(laneOrder.getValue()));
                break;
            case KeyEvent.VK_RIGHT:
                laneOrder.add(1);
                setLane(panel.getLanes().get(laneOrder.getValue()));
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    
}
