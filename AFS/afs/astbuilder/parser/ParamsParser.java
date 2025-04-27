package afs.astbuilder.parser;

import afs.AFSBaseVisitor;
import afs.AFSParser;
import afs.astbuilder.nodes.def.Param;
import afs.astbuilder.nodes.type.TypeNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

public class ParamsParser extends AFSBaseVisitor<List<Param>> {
    private final TypeParser typeParser = new TypeParser();

    @Override
    public List<Param> visitParams(AFSParser.ParamsContext ctx) {
        List<AFSParser.TypeContext> typeContexts = ctx.type();
        List<TerminalNode> idContexts = ctx.ID();
        List<Param> params = new ArrayList<>();

        for (int i = 0; i < typeContexts.size(); i++) {
            TypeNode type = typeContexts.get(i).accept(typeParser);
            String identifier = idContexts.get(i).toString();
            Param param = new Param(type, identifier);
            params.add(param);
        }

        return params;
    }
}
