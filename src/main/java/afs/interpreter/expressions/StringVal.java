package afs.interpreter.expressions;

public class StringVal implements Val{
    private final String value;

    public StringVal(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;  // same reference
        if (o == null || getClass() != o.getClass()) return false; // null or different type
        StringVal stringVal = (StringVal) o;
        return value == stringVal.value; // compare values
    }
}
