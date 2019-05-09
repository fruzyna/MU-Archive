package AbSyn;
import VisitorPkg.Visitor;
import VisitorPkg.ValueVisitor;

public class If extends Stmt {
  public Expr cond;
  public StmtList body;
  
  public If(Expr e, StmtList sl) {
    cond = e;
    body = sl;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Object accept(ValueVisitor v) {
    return v.visit(this);
  }
}

