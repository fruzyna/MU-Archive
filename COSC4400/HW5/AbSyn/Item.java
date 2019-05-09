package AbSyn;
import VisitorPkg.Visitor;
import VisitorPkg.ValueVisitor;

public class Item extends Expr{
  public String name;
  public Expr index;
  
  public Item(String n, Expr e) {
    name = n;
    index = e;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Object accept(ValueVisitor v) {
    return v.visit(this);
  }
}

