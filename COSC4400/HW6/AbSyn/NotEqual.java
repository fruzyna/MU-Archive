package AbSyn;
import VisitorPkg.Visitor;
import VisitorPkg.ValueVisitor;

public class NotEqual extends Expr{
  public Expr left;
  public Expr right;
  
  public NotEqual(Expr e1, Expr e2) {
    left = e1;
    right = e2;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Object accept(ValueVisitor v) {
    return v.visit(this);
  }
}

