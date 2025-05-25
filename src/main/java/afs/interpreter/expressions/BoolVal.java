package afs.interpreter.expressions;

public final class BoolVal implements Val {
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



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;  // same reference
        if (o == null || getClass() != o.getClass()) return false; // null or different type
        BoolVal boolVal = (BoolVal) o;
        return value == boolVal.value; // compare values
    }

    @Override
    public Val copy() {
        return new BoolVal(value);
    }
}
