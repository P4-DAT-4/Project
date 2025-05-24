package afs.interpreter.expressions;

import afs.runtime.Shape;

import java.util.List;

public interface Val {
    Val copy();
    default int asInt(){
        if (this instanceof IntVal) {
            return ((IntVal) this).getValue();
        }
        throw new UnsupportedOperationException("Not an IntVal");
    }

    default double asDouble(){
        if (this instanceof DoubleVal){
            return ((DoubleVal) this).getValue();
        }
        throw new UnsupportedOperationException("Not an DoubleVal");
    }

    default String asString(){
        if (this instanceof StringVal){
            return ((StringVal) this).getValue();
        }
        throw new UnsupportedOperationException("Not an StringVal");

    }

    default boolean asBool(){
        if (this instanceof BoolVal){
            return ((BoolVal) this).getValue();
        }
        throw new UnsupportedOperationException("Not an BoolVal");
    }

    default ListVal asList(){
        if (this instanceof ListVal){
            return (ListVal) ((ListVal) this).getElements();
        }
        throw new UnsupportedOperationException("Not an ListVal");
    }




}






