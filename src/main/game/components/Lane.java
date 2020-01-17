package main.game.components;

import main.game.components.PlayerCar;
import main.game.components.Car;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JComponent;
import main.game.GameComponent;
import main.game.Paintable;

public class Lane implements Paintable, Moveable {
    
    public static final int WIDTH = 90;
    
    private JComponent panel;
    
    private List<Car> cars = new ArrayList<>();
    private int order;
    
    public Lane(JComponent panel, int order) {
        this.panel = panel;
        
        this.order = order;
    }
    
    /**
     * 
     * @return Copy of car list
     */
    public List<Car> getCars() {
        return Collections.unmodifiableList(cars);
    }
    
    public int getOrder() {
        return order;
    }
    
    public int getXCoord() {
        return GameComponent.LEFT_MARGIN + order * Lane.WIDTH;
    }
    
    public void addCar(Car car) {
        cars.add(car);
    }
    
    public void removeCar(Car car) {
        cars.remove(car);
    }
    
    public void removeAllCars() {
        for(int i = 0; i < cars.size(); i++) {
            Car c = cars.get(i);
            if(c instanceof PlayerCar)
                continue;
            
            cars.remove(i);
            i--;
        }
    }

    @Override
    public void paint(Graphics g, JComponent panel) {
        for(Car c : cars) {
            c.paint(g, panel);
        }
    }
    
    public void checkForDespawn() {
        int panelHeight = panel.getHeight();
        for(int i = 0; i < cars.size(); i++) {
            Car car = cars.get(i);
            
            // if car is out of the map, it removes its object
            if(car.getY() > panelHeight) {
                despawnCar(car);
                i--;
            }
        }
    }
    
    private void despawnCar(Car c) {
        cars.remove(c);
    }

    @Override
    public void move(double boost) {
        for(Car c : cars) {
            c.move(boost);
        }
    }
    
}
