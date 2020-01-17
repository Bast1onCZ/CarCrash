package utility.intervals;

public class IntervalChangeableInteger extends IntervalChangeableValue<Integer> {

    public IntervalChangeableInteger(Integer minValue, Integer maxValue) {
        super(minValue, maxValue);
    }
    
    public IntervalChangeableInteger(Integer minValue, Integer maxValue, Integer value) {
        super(minValue, maxValue, value);
        
        this.setValue(value);
    }

    @Override
    public void add(Integer toAdd) {
        currentValue += toAdd;
        
        if(isSmallerThanLowest(currentValue))
            currentValue = minValue;
        else
            if(isGreaterThanHighest(currentValue))
                currentValue = maxValue;
    }
    
    @Override
    public void setValue(Integer value) {
        if(isSmallerThanLowest(value))
            value = minValue;
        else 
            if(isGreaterThanHighest(value))
                value = maxValue;
        
        super.setValue(value);
    }

    @Override
    public void remove(Integer toRemove) {
        this.add(-toRemove);
    }

    @Override
    public boolean contains(Integer value) {
        return !isSmallerThanLowest(value) && !isGreaterThanHighest(value);
    }
    
    private boolean isSmallerThanLowest(Integer value) {
        return value < minValue;
    }
    
    private boolean isGreaterThanHighest(Integer value) {
        return value > maxValue;
    }
    
}
