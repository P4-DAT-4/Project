package afs.interpreter.expressions;

import afs.interpreter.*;
import afs.nodes.expr.ExprNode;
import org.javatuples.Pair;

public class Point {
    public final double x, y;
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }





}
