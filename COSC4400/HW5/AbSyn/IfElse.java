package AbSyn;
import VisitorPkg.Visitor;
import VisitorPkg.ValueVisitor;

public class IfElse extends Stmt {
  public Expr cond;
  public StmtList trueBody;
  public StmtList falseBody;
  
  public IfElse(Expr e, StmtList sl1, StmtList sl2) {
    cond = e;
    trueBody = sl1;
    falseBody = sl2;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Object accept(ValueVisitor v) {
    return v.visit(this);
  }
}

