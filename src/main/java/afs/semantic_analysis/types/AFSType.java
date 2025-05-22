package afs.semantic_analysis.types;

public abstract class AFSType<T> {
    private final T _value;

    protected AFSType(T value){
        _value = value;
    }

    public T getValue() {
        return _value;
    }
}
