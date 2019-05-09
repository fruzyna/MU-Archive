package AbSyn;
import VisitorPkg.Visitor;
import VisitorPkg.ValueVisitor;

public class PairMethodDecList extends MethodDecList{
  public MethodDec head;
  public MethodDecList tail;
  
  public PairMethodDecList(MethodDec md, MethodDecList mdl) {
    head = md;
    tail = mdl;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Object accept(ValueVisitor v) {
    return v.visit(this);
  }
}

