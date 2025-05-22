package afs.semantic_analysis.types;

import java.util.List;

public class AFSArray extends AFSType<List<AFSType<?>>>{
    public AFSArray(List<AFSType<?>> value) {
        super(value);
    }
}
