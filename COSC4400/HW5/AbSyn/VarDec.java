package AbSyn;
import VisitorPkg.Visitor;
import VisitorPkg.ValueVisitor;

public class VarDec {
  public String name;
  public Type type;
  
  public VarDec(String n, Type t) {
    name = n;
    type = t;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Object accept(ValueVisitor v) {
    return v.visit(this);
  }
}

