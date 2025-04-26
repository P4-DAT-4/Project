package afs.astbuilder.builder;

import afs.AFSBaseVisitor;
import afs.AFSParser;
import afs.astbuilder.nodes.def.DefNode;
import afs.astbuilder.nodes.prog.ProgNode;

import java.util.ArrayList;
import java.util.List;

public class ProgParser extends AFSBaseVisitor<ProgNode> {
    @Override public ProgNode visitMain(AFSParser.MainContext ctx) {
        List<DefNode> defs = new ArrayList<>();
        DefParser defParser = new DefParser();

        for (var defCtx : ctx.def()) {
            DefNode def = defCtx.accept(defParser);
            defs.add(def);
        }

        return new ProgNode(defs);
    }
}
