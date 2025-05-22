package afs.interpreter.expressions;

public class DoubleVal implements Val {
    private final double value;

    public DoubleVal(double value){
        this.value = value;
    }

    public double getValue(){
        return value;
    }

    @Override
    public String toString(){
        return Double.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;  // same reference
        if (o == null || getClass() != o.getClass()) return false; // null or different type
        DoubleVal doubleVal = (DoubleVal) o;
        return value == doubleVal.value; // compare values
    }

}