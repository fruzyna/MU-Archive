package AbSyn;
import VisitorPkg.Visitor;
import VisitorPkg.ValueVisitor;

public abstract class VarDecList {
  public abstract void accept(Visitor v);
  public abstract Object accept(ValueVisitor v);
}

