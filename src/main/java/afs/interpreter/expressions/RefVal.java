package afs.interpreter.expressions;

public class RefVal implements Val{

    private final int location;

    public RefVal(int location) {
        this.location = location;
    }

    public int getLocation() {
        return location;
    }

    @Override
    public Val copy() {
        return new RefVal(location);  // references don't clone values
    }

    @Override
    public String toString() {
        return "Ref(" + location + ")";
    }
}


