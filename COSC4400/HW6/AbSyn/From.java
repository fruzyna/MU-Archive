package AbSyn;
import VisitorPkg.Visitor;
import VisitorPkg.ValueVisitor;

public class From extends Stmt {
  public StmtList init;
  public Expr cond;
  public StmtList body;
  
  public From(StmtList sl1, Expr e, StmtList sl2) {
    init = sl1;
    cond = e;
    body = sl2;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Object accept(ValueVisitor v) {
    return v.visit(this);
  }
}

