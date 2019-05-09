package VisitorPkg;

import AbSyn.*;

public class PrettyPrintVisitor implements Visitor {


  String substitute(String s, char c, String r) {
    // replace all copies of c in s with r
    // Used in PutString to recode special chars.
    String new_s = s;
    int t = new_s.indexOf(c);
    while (t >= 0) {
      new_s = new_s.substring(0,t)+r+new_s.substring(t+1);
      t = new_s.indexOf(c, t+r.length());
    }
    return new_s;
  }

  public void visit(Access n){
    System.out.print(n.name+"."+n.feature);
    if (n.params != null) {
      System.out.print("(");
      n.params.accept(this);
      System.out.print(")");
    }
  }

  public void visit(AccessStmt n){
    System.out.print("      "+n.name+"."+n.feature);
    if (n.params != null) {
      System.out.print("(");
      n.params.accept(this);
      System.out.print(")");
    }
    System.out.println();
  }

  public void visit(And n){
    System.out.print("(");
    n.left.accept(this);
    System.out.print(" and ");
    n.right.accept(this);
    System.out.print(")");
  }
  public void visit(ArrayPut n){
    System.out.print("      "+n.name+".put(");
    n.value.accept(this);
    System.out.print(", ");
    n.index.accept(this);
    System.out.println(")");
  }

  public void visit(Assign n){
    System.out.print("      "+n.var+" := ");
    n.value.accept(this);
    System.out.println();
  }

  public void visit(BooleanType n){
    System.out.print("BOOLEAN");
  }
  public void visit(ClassDec n){
    System.out.println("class "+n.name);
    if (n.instanceVars != null){
      System.out.println("attribute");
      n.instanceVars.accept(this);
    }
    if (n.methods != null){
      System.out.println("method");
      n.methods.accept(this);
    }
  }

  public void visit(ClassType n){
    System.out.print(n.name);
  }
  public void visit(Create n){
    System.out.println("      !! "+n.name);
  }

  public void visit(CreateIntArray n){
    System.out.print("      !! "+n.name+".make(");
    n.lower.accept(this);
    System.out.print(", ");
    n.upper.accept(this);
    System.out.println(")");
  }

  public void visit(Equal n){
    System.out.print("(");
    n.left.accept(this);
    System.out.print(" = ");
    n.right.accept(this);
    System.out.print(")");
  }
  public void visit(False n){
    System.out.print("false");
  }
  public void visit(From n){
    System.out.println("    from");
    if (n.init != null)
      n.init.accept(this);
    System.out.print("    until\n      ");
    n.cond.accept(this);
    System.out.println("\n    loop");
    if (n.body != null)
      n.body.accept(this);
    System.out.println("    end");
  }

  public void visit(GreaterEqual n){
    System.out.print("(");
    n.left.accept(this);
    System.out.print(" >= ");
    n.right.accept(this);
    System.out.print(")");
  }
  public void visit(GreaterThan n){
    System.out.print("(");
    n.left.accept(this);
    System.out.print(" > ");
    n.right.accept(this);
    System.out.print(")");
  }
  public void visit(IdExpr n){
    System.out.print(n.name);
  }
  public void visit(If n){
    System.out.print("    If ");
    n.cond.accept(this);
    System.out.println(" then");
    if (n.body != null)
      n.body.accept(this);
    System.out.println("    end");
  }

  public void visit(IfElse n){
    System.out.print("    If ");
    n.cond.accept(this);
    System.out.println(" then");
    if (n.trueBody != null)
      n.trueBody.accept(this);
    System.out.println("    else");
    if (n.falseBody != null)
      n.falseBody.accept(this);
    System.out.println("    end");
  }
  public void visit(IntArrayType n){
    System.out.print("ARRAY[INTEGER]");
  }
  public void visit(IntDivide n){
    System.out.print("(");
    n.left.accept(this);
    System.out.print(" // ");
    n.right.accept(this);
    System.out.print(")");
  }
  public void visit(IntegerType n){
    System.out.print("INTEGER");
  }
  public void visit(Item n){
    System.out.print("("+n.name+" @ ");
    n.index.accept(this);
    System.out.print(")");
  }

  public void visit(LastClassDecList n){
    n.head.accept(this);
  }
  public void visit(LastExprList n){
    n.head.accept(this);
  }
  public void visit(LastMethodDecList n){
    n.head.accept(this);
  }

  public void visit(LastStmtList n){
    n.head.accept(this);
  }

  public void visit(LastVarDecList n){
    n.head.accept(this);
  }

  public void visit(LessEqual n){
    System.out.print("(");
    n.left.accept(this);
    System.out.print(" <= ");
    n.right.accept(this);
    System.out.print(")");
  }
  public void visit(LessThan n){
    System.out.print("(");
    n.left.accept(this);
    System.out.print(" < ");
    n.right.accept(this);
    System.out.print(")");
  }
  public void visit(Lower n){
    System.out.print(n.name+".lower");
  }
  public void visit(MethodDec n){
    System.out.print("  "+n.name);
    if (n.params != null){
      System.out.print("(");
      n.params.accept(this);
      System.out.print(")");
    }
    if (n.type != null){
      System.out.print(": ");
      n.type.accept(this);
    }
    System.out.println(" is");
    if (n.reqCond != null){
      System.out.println("    require");
      System.out.print("      ");
      n.reqCond.accept(this);
    }
    if (n.locals != null){
      System.out.println("    locals");
      n.locals.accept(this);
    }
    System.out.println("    do");
    if (n.body != null)
      n.body.accept(this);
    if (n.ensCond != null){
      System.out.println("    ensure");
      System.out.print("      ");
      n.ensCond.accept(this);
    }
    System.out.println("    end");
  }

  public void visit(Minus n){
    System.out.print("(");
    n.left.accept(this);
    System.out.print(" - ");
    n.right.accept(this);
    System.out.print(")");
  }
  public void visit(Not n){
    System.out.print("not ");
    n.expr.accept(this);
  }
  public void visit(NotEqual n){
    System.out.print("(");
    n.left.accept(this);
    System.out.print(" /= ");
    n.right.accept(this);
    System.out.print(")");
  }
  public void visit(NumberExpr n){
    System.out.print(n.value);
  }
  public void visit(Or n){
    System.out.print("(");
    n.left.accept(this);
    System.out.print(" or ");
    n.right.accept(this);
    System.out.print(")");
  }

  public void visit(PairClassDecList n){
    n.head.accept(this);
    n.tail.accept(this);
  }

  public void visit(PairExprList n){
    n.head.accept(this);
    n.tail.accept(this);
  }

  public void visit(PairMethodDecList n){
    n.head.accept(this);
    n.tail.accept(this);
  }

  public void visit(PairStmtList n){
    n.head.accept(this);
    n.tail.accept(this);
  }

  public void visit(PairVarDecList n){
    n.head.accept(this);
    n.tail.accept(this);
  }

  public void visit(Plus n){
    System.out.print("(");
    n.left.accept(this);
    System.out.print(" + ");
    n.right.accept(this);
    System.out.print(")");
  }
  public void visit(Program n){
    n.root.accept(this);
    if (n.classes != null){
      n.classes.accept(this);
    }
  }

  public void visit(PutInteger n){
    System.out.print("      io.put_integer(");
    n.expr.accept(this);
    System.out.println(")");
  }

  public void visit(PutString n){
    // Need to swap special chars back to seqs
    String s = substitute(n.msg, '%', "%%");
    s = substitute(s, '\"', "%\"");
    s = substitute(s, '\n', "%N");
    System.out.println("      io.put_string(\""+s+"\")");
  }

  public void visit(RootClass n){
    System.out.println("class "+n.name);
    System.out.println("\ncreation make\n");
    if (n.instanceVars != null) {
      System.out.println("attribute");
      n.instanceVars.accept(this);
    }
    System.out.println("method\n\n  make is");
    if (n.localVars != null) {
      System.out.println("  local");
      n.localVars.accept(this);
    }
    System.out.println("    do");
    n.body.accept(this);
    System.out.println("    end");
    if (n.methods != null) {
      n.methods.accept(this);
    }
    System.out.println("end\n");

  }

  public void visit(Times n){
    System.out.print("(");
    n.left.accept(this);
    System.out.print(" * ");
    n.right.accept(this);
    System.out.print(")");
  }
  public void visit(True n){
    System.out.print("true");
  }
  public void visit(Upper n){
    System.out.print(n.name+".upper");
  }

  public void visit(VarDec n){
    System.out.print("  "+n.name+": ");
    n.type.accept(this);
    System.out.println();
  }
}
