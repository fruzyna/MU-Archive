package AbSyn;
import VisitorPkg.Visitor;
import VisitorPkg.ValueVisitor;

public class CreateIntArray extends Stmt{
  public String name;
  public Expr lower;
  public Expr upper;
  
  public CreateIntArray(String n, Expr e1, Expr e2) {
    name = n;
    lower = e1;
    upper = e2;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Object accept(ValueVisitor v) {
    return v.visit(this);
  }
}

