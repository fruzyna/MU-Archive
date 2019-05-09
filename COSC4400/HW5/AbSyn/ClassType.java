package AbSyn;
import VisitorPkg.Visitor;
import VisitorPkg.ValueVisitor;

public class ClassType extends Type{
  public String name;
  
  public ClassType(String s) {
    name = s;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Object accept(ValueVisitor v) {
    return v.visit(this);
  }
}

