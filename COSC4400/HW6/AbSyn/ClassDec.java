package AbSyn;
import VisitorPkg.Visitor;
import VisitorPkg.ValueVisitor;

public class ClassDec {
  public String name;
  public VarDecList instanceVars;
  public MethodDecList methods;
  
  public ClassDec(String n, VarDecList vdl, MethodDecList mdl) {
    name = n;
    instanceVars = vdl;
    methods = mdl;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Object accept(ValueVisitor v) {
    return v.visit(this);
  }
}

