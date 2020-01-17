package main.game.components;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import main.game.Paintable;
import utility.GraphicsUtility;

public class Car implements Paintable, Moveable {
    
    public static final int WIDTH = 80;
    public static final int HEIGHT = 160;
    
    public static int newID = -1;
    
    private int id;
    protected Lane lane;
    protected int y = -HEIGHT;
    
    protected int baseSpeed;
    
    protected Image carImg;
    
    public Car(Lane lane, int baseSpeed) {
        this.id = (newID++);
        this.lane = lane;
        lane.addCar(this);
        
        this.baseSpeed = baseSpeed;
        
        this.carImg = new ImageIcon(this.getClass().getResource("red_car.png")).getImage();
    }
    
    @Override
    public String toString() {
        return "Car{id="+ id +"}";
    }
    
    public Lane getLane() {
        return lane;
    }
    
    protected int getId() {
        return id;
    }
    
    private Rectangle getOcuppiedArea() {
        return new Rectangle(lane.getXCoord(), y, WIDTH, HEIGHT);
    }
    
    /**
     * Checks if car collides with given car
     * @param car Car, given car to check collision
     * @return true if car collides with given car, false if not or given car is the same as this
     */
    public boolean isCollidingWith(Car car) {
        // Cars collides with itself
        if(this.getId() == car.getId())
            return false;
        
        Rectangle rect1 = getOcuppiedArea();
        Rectangle rect2 = car.getOcuppiedArea();
        
        if(rect1.intersects(rect2))
            return true;
        
        return false;
    }
    
    public int getY() {
        return y;
    }
    
    @Override
    public void paint(Graphics g, JComponent panel) {
        int xDiff = GraphicsUtility.getPosForMiddleObject(Lane.WIDTH, Car.WIDTH);
        int laneX = lane.getXCoord();
        int x = laneX + xDiff;
        
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform af = AffineTransform.getTranslateInstance(x + Car.WIDTH, y + Car.HEIGHT);
        af.rotate(Math.toRadians(180));
        g2.drawImage(carImg, af, panel);
    }

    @Override
    public void move(double boost) {
        y += (baseSpeed * (boost / 100d));
    }
    
}
