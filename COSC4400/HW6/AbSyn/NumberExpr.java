package AbSyn;
import VisitorPkg.Visitor;
import VisitorPkg.ValueVisitor;

public class NumberExpr extends Expr{
  public int value;
  
  public NumberExpr(int v) {
    value = v;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Object accept(ValueVisitor v) {
    return v.visit(this);
  }
}

