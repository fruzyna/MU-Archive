package AbSyn;
import VisitorPkg.Visitor;
import VisitorPkg.ValueVisitor;

public class Access extends Expr{
  public String name;
  public String feature;
  public ExprList params;
  
  public Access(String n, String f, ExprList el) {
    name = n;
    feature = f;
    params = el;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Object accept(ValueVisitor v) {
    return v.visit(this);
  }
}

