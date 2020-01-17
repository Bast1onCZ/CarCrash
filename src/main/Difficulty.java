package main;

public enum Difficulty {
    
    EASY(2, 1, 3, 3),
    NORMAL(3, 2, 3, 3),
    HARD(4, 3, 3, 3),
    EXTREME(5, 3, 3, 3);
    
    private static int initValue = 0;
    
    private int value;
    
    private int lanes;
    private int maxEmptyLanes;
    private int startSpeed;
    private int startHealth;
    
    private Difficulty(int lanes, int maxEmptyLanes, int startSpeed, int startHealth) {
        initValue();
        
        this.lanes = lanes;
        this.maxEmptyLanes = maxEmptyLanes;
        this.startSpeed = startSpeed;
        this.startHealth = startHealth;
    }
    
    private void initValue() {
        value = initValue;
        initValue ++;
    }
    
    public int getValue() {
        return value;
    }

    public int getLanes() {
        return lanes;
    }

    public int getMaxEmptyLanes() {
        return maxEmptyLanes;
    }

    public int getBaseSpeed() {
        return startSpeed;
    }
    
    public int getStartHealth() {
        return startHealth;
    }
    
    public static Difficulty getByValue(int value) {
        Difficulty[] values = Difficulty.values();
        if(value < 0 || value >= values.length)
            return null;
        
        return values[value];
    }
    
}
