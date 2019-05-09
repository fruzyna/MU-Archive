package AbSyn;
import VisitorPkg.Visitor;
import VisitorPkg.ValueVisitor;

public class Program {
  public RootClass root;
  public ClassDecList classes;
  
  public Program(RootClass r, ClassDecList cdl) {
    root = r;
    classes = cdl;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Object accept(ValueVisitor v) {
    return v.visit(this);
  }
}

