package VisitorPkg;

import AbSyn.*;

/*
 * Define a do nothing ValueVisitor for use as a base
 * class when you don't care about all the trees.
 * Just returns null for every tree.
 *
 * written by mike slattery - feb 2004
 */

public class NoopValueVisitor implements ValueVisitor {
  public Object visit(Access n){ return null; };
  public Object visit(AccessStmt n){ return null; };
  public Object visit(And n){ return null; };
  public Object visit(ArrayPut n){ return null; };
  public Object visit(Assign n){ return null; };
  public Object visit(BooleanType n){ return null; };
  public Object visit(ClassDec n){ return null; };
  public Object visit(ClassType n){ return null; };
  public Object visit(Create n){ return null; };
  public Object visit(CreateIntArray n){ return null; };
  public Object visit(Equal n){ return null; };
  public Object visit(False n){ return null; };
  public Object visit(From n){ return null; };
  public Object visit(GreaterEqual n){ return null; };
  public Object visit(GreaterThan n){ return null; };
  public Object visit(IdExpr n){ return null; };
  public Object visit(If n){ return null; };
  public Object visit(IfElse n){ return null; };
  public Object visit(IntArrayType n){ return null; };
  public Object visit(IntDivide n){ return null; };
  public Object visit(IntegerType n){ return null; };
  public Object visit(Item n){ return null; };
  public Object visit(LastClassDecList n){ return null; };
  public Object visit(LastExprList n){ return null; };
  public Object visit(LastMethodDecList n){ return null; };
  public Object visit(LastStmtList n){ return null; };
  public Object visit(LastVarDecList n){ return null; };
  public Object visit(LessEqual n){ return null; };
  public Object visit(LessThan n){ return null; };
  public Object visit(Lower n){ return null; };
  public Object visit(MethodDec n){ return null; };
  public Object visit(Minus n){ return null; };
  public Object visit(Not n){ return null; };
  public Object visit(NotEqual n){ return null; };
  public Object visit(NumberExpr n){ return null; };
  public Object visit(Or n){ return null; };
  public Object visit(PairClassDecList n){ return null; };
  public Object visit(PairExprList n){ return null; };
  public Object visit(PairMethodDecList n){ return null; };
  public Object visit(PairStmtList n){ return null; };
  public Object visit(PairVarDecList n){ return null; };
  public Object visit(Plus n){ return null; };
  public Object visit(Program n){ return null; };
  public Object visit(PutInteger n){ return null; };
  public Object visit(PutString n){ return null; };
  public Object visit(RootClass n){ return null; };
  public Object visit(Times n){ return null; };
  public Object visit(True n){ return null; };
  public Object visit(Upper n){ return null; };
  public Object visit(VarDec n){ return null; };
}
