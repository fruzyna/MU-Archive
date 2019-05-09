package AbSyn;
import VisitorPkg.Visitor;
import VisitorPkg.ValueVisitor;

public class LastStmtList extends StmtList {
  public Stmt head;
  
  public LastStmtList(Stmt s) {
    head = s;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Object accept(ValueVisitor v) {
    return v.visit(this);
  }
}

