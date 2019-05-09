package AbSyn;
import VisitorPkg.Visitor;
import VisitorPkg.ValueVisitor;

public class LastClassDecList extends ClassDecList {
  public ClassDec head;
  
  public LastClassDecList(ClassDec cd) {
    head = cd;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Object accept(ValueVisitor v) {
    return v.visit(this);
  }
}

