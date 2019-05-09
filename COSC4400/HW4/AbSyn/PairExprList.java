package AbSyn;
import VisitorPkg.Visitor;
import VisitorPkg.ValueVisitor;

public class PairExprList extends ExprList{
  public Expr head;
  public ExprList tail;
  
  public PairExprList(Expr e, ExprList el) {
    head = e;
    tail = el;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Object accept(ValueVisitor v) {
    return v.visit(this);
  }
}

