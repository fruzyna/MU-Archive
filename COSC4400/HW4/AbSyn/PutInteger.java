package AbSyn;
import VisitorPkg.Visitor;
import VisitorPkg.ValueVisitor;

public class PutInteger extends Stmt {
  public Expr expr;
  
  public PutInteger(Expr e) {
    expr = e;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Object accept(ValueVisitor v) {
    return v.visit(this);
  }
}

