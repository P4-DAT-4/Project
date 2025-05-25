package afs.interpreter.expressions;

public final class StringVal implements Val{
    private final String value;

    public StringVal(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof StringVal)) return false;
        StringVal other = (StringVal) obj;
        return this.value.equals(other.value);
    }
}
