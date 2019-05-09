package AbSyn;
import VisitorPkg.Visitor;
import VisitorPkg.ValueVisitor;

public class IdExpr extends Expr{
  public String name;
  
  public IdExpr(String n) {
    name = n;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Object accept(ValueVisitor v) {
    return v.visit(this);
  }
}

