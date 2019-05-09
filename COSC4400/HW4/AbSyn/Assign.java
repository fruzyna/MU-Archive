package AbSyn;
import VisitorPkg.Visitor;
import VisitorPkg.ValueVisitor;

public class Assign extends Stmt {
  public String var;
  public Expr value;
  
  public Assign(String s, Expr e) {
    var = s;
    value = e;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Object accept(ValueVisitor v) {
    return v.visit(this);
  }
}

