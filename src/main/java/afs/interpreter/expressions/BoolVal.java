package afs.interpreter.expressions;

public class BoolVal implements Val {
    private final boolean value;

    public BoolVal(boolean value){
        this.value = value;
    }

    public boolean getValue(){
        return value;
    }

    @Override
    public String toString(){
        return Boolean.toString(value);
    }
}
