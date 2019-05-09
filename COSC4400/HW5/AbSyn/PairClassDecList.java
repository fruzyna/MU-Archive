package AbSyn;
import VisitorPkg.Visitor;
import VisitorPkg.ValueVisitor;

public class PairClassDecList extends ClassDecList {
  public ClassDec head;
  public ClassDecList tail;
  
  public PairClassDecList(ClassDec cd, ClassDecList cdl) {
    head = cd;
    tail = cdl;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Object accept(ValueVisitor v) {
    return v.visit(this);
  }
}

