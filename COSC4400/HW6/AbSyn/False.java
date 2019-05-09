package AbSyn;
import VisitorPkg.Visitor;
import VisitorPkg.ValueVisitor;

public class False extends Expr{

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Object accept(ValueVisitor v) {
    return v.visit(this);
  }
}

