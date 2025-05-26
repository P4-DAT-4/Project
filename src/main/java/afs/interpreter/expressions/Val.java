package afs.interpreter.expressions;

import afs.interpreter.expressions.shape.Shape;

import java.util.List;

public sealed interface Val permits BoolVal, DoubleVal, IntVal, ListVal, ShapeVal, StringVal{
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

    default List<Val> asList(){
        if (this instanceof ListVal){
            return ((ListVal) this).getValue();
        }
        throw new UnsupportedOperationException("Not an ListVal");
    }

    default List<Shape> asShape(){
        if (this instanceof ShapeVal) {
            return ((ShapeVal) this).getValue();
        }
        throw new UnsupportedOperationException("Not a ShapeVal");
    }

    default ShapeVal asShapeVal(){
        if (this instanceof ShapeVal) {
            return ((ShapeVal) this);
        }
        throw new UnsupportedOperationException("Not a ShapeVal");
    }
}






