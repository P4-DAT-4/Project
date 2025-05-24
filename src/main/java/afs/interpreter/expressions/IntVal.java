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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;  // same reference
        if (o == null || getClass() != o.getClass()) return false; // null or different type
        IntVal intVal = (IntVal) o;
        return value == intVal.value; // compare values
    }

    @Override
    public Val copy() {
        return new IntVal(value);
    }
}
