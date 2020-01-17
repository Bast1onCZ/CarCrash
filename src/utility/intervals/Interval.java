package utility.intervals;

public abstract class Interval<T> {
    
    protected T minValue;
    protected T maxValue;
    
    protected Interval(T minValue, T maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }
    
    @Override
    public String toString() {
        return "Interval{from "+ minValue +" to "+ maxValue +"}";
    }
    
    public abstract boolean contains(T value);
    
}
