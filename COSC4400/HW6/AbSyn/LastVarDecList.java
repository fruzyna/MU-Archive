package AbSyn;
import VisitorPkg.Visitor;
import VisitorPkg.ValueVisitor;

public class LastVarDecList extends VarDecList{
  public VarDec head;
  
  public LastVarDecList(VarDec vd) {
    head = vd;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Object accept(ValueVisitor v) {
    return v.visit(this);
  }
}

