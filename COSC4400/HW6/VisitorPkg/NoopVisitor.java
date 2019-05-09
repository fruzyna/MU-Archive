package VisitorPkg;

import AbSyn.*;

/*
 * Define a class which implements Visitor with
 * do nothing methods.
 * For use as a base class when implementing a
 * Visitor which doesn't care about all the trees.
 *
 * written by mike slattery - feb 2004
 */

public class NoopVisitor implements Visitor {
  public void visit(Access n){};
  public void visit(AccessStmt n){};
  public void visit(And n){};
  public void visit(ArrayPut n){};
  public void visit(Assign n){};
  public void visit(BooleanType n){};
  public void visit(ClassDec n){};
  public void visit(ClassType n){};
  public void visit(Create n){};
  public void visit(CreateIntArray n){};
  public void visit(Equal n){};
  public void visit(False n){};
  public void visit(From n){};
  public void visit(GreaterEqual n){};
  public void visit(GreaterThan n){};
  public void visit(IdExpr n){};
  public void visit(If n){};
  public void visit(IfElse n){};
  public void visit(IntArrayType n){};
  public void visit(IntDivide n){};
  public void visit(IntegerType n){};
  public void visit(Item n){};
  public void visit(LastClassDecList n){};
  public void visit(LastExprList n){};
  public void visit(LastMethodDecList n){};
  public void visit(LastStmtList n){};
  public void visit(LastVarDecList n){};
  public void visit(LessEqual n){};
  public void visit(LessThan n){};
  public void visit(Lower n){};
  public void visit(MethodDec n){};
  public void visit(Minus n){};
  public void visit(Not n){};
  public void visit(NotEqual n){};
  public void visit(NumberExpr n){};
  public void visit(Or n){};
  public void visit(PairClassDecList n){};
  public void visit(PairExprList n){};
  public void visit(PairMethodDecList n){};
  public void visit(PairStmtList n){};
  public void visit(PairVarDecList n){};
  public void visit(Plus n){};
  public void visit(Program n){};
  public void visit(PutInteger n){};
  public void visit(PutString n){};
  public void visit(RootClass n){};
  public void visit(Times n){};
  public void visit(True n){};
  public void visit(Upper n){};
  public void visit(VarDec n){};
}
