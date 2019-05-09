package AbSyn;
import VisitorPkg.Visitor;
import VisitorPkg.ValueVisitor;

public class PairStmtList extends StmtList {
  public Stmt head;
  public StmtList tail;
  
  public PairStmtList(Stmt s, StmtList sl) {
    head = s;
    tail = sl;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Object accept(ValueVisitor v) {
    return v.visit(this);
  }
}

