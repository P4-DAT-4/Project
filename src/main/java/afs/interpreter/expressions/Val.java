package afs.interpreter.expressions;

import afs.runtime.Shape;

public interface Val {
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




}






