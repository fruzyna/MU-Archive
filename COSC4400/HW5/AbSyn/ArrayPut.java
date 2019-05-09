package AbSyn;
import VisitorPkg.Visitor;
import VisitorPkg.ValueVisitor;

public class ArrayPut extends Stmt{
  public String name;
  public Expr value;
  public Expr index;
  
  public ArrayPut(String n, Expr e1, Expr e2) {
    name = n;
    value = e1;
    index = e2;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Object accept(ValueVisitor v) {
    return v.visit(this);
  }
}

