package AbSyn;
import VisitorPkg.Visitor;
import VisitorPkg.ValueVisitor;

public class PairVarDecList extends VarDecList{
  public VarDec head;
  public VarDecList tail;
  
  public PairVarDecList(VarDec vd, VarDecList vdl) {
    head = vd;
    tail = vdl;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Object accept(ValueVisitor v) {
    return v.visit(this);
  }
}

