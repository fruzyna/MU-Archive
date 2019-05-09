package AbSyn;
import VisitorPkg.Visitor;
import VisitorPkg.ValueVisitor;

public class Not extends Expr{
  public Expr expr;
  
  public Not(Expr e) {
    expr = e;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Object accept(ValueVisitor v) {
    return v.visit(this);
  }
}

