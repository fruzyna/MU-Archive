package AbSyn;
import VisitorPkg.Visitor;
import VisitorPkg.ValueVisitor;

public class MethodDec {
  public String name;
  public VarDecList params;
  public Type type;
  public Expr reqCond;
  public VarDecList locals;
  public StmtList body;
  public Expr ensCond;
  
  public MethodDec(String n, VarDecList vdl1, Type t, Expr e1, VarDecList vdl2, StmtList s, Expr e2) {
    name = n;
    params = vdl1;
    type = t;
    reqCond = e1;
    locals = vdl2;
    body = s;
    ensCond = e2;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Object accept(ValueVisitor v) {
    return v.visit(this);
  }
}

