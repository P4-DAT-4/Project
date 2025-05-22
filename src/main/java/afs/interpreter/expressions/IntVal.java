package afs.interpreter.expressions;

public class IntVal implements Val {
    private final int value;

    public IntVal(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    @Override
    public String toString(){
        return Integer.toString(value);
    }
}
