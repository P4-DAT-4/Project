package afs.interpreter.expressions;

public final class StringVal implements Val{
    private final String value;

    public StringVal(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
