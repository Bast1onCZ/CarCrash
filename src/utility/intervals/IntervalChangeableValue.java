package utility.intervals;

public abstract class IntervalChangeableValue<T> extends Interval<T> {
    
    protected T currentValue;
    
    public IntervalChangeableValue(T minValue, T maxValue) {
        super(minValue, maxValue);
    }
    
    public IntervalChangeableValue(T minValue, T maxValue, T value) {
        super(minValue, maxValue);
        
        this.setValue(value);
    }
    
    public T getValue() {
        return currentValue;
    }
    
    public void setValue(T value) {
        if(!this.contains(value))
            throw new IllegalArgumentException(String.valueOf(value) +" is not at interval "+ super.toString());
        
        this.currentValue = value;
    }
    
    public abstract void add(T value);
    
    public abstract void remove(T value);
    
}
