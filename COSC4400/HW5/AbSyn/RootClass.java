package AbSyn;
import VisitorPkg.Visitor;
import VisitorPkg.ValueVisitor;

public class RootClass {
  public String name;
  public VarDecList instanceVars;
  public VarDecList localVars;
  public StmtList body;
  public MethodDecList methods;

  public RootClass(String n, VarDecList vdl1, VarDecList vdl2, StmtList s, MethodDecList mdl) {
    name = n;
    instanceVars = vdl1;
    localVars = vdl2;
    body = s;
    methods = mdl;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Object accept(ValueVisitor v) {
    return v.visit(this);
  }
}

