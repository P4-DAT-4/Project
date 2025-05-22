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

}