package AbSyn;
import VisitorPkg.Visitor;
import VisitorPkg.ValueVisitor;

public class LastMethodDecList extends MethodDecList{
  public MethodDec head;
  
  public LastMethodDecList(MethodDec md) {
    head = md;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Object accept(ValueVisitor v) {
    return v.visit(this);
  }
}

