package CodeGenPkg;

import VisitorPkg.*;
import AbSyn.*;
import java.util.HashMap;
import java.util.ArrayList;

/*
 * Small piece of BabyGustave code generation for expressions
 * by mike slattery, mar 2019
 */
public class ExprCoder extends NoopValueVisitor
{
	HashMap<String, Type> symTab;
	int tempVal = 0;

	public ExprCoder(HashMap<String, Type> st)
	{
		symTab = st;
	}

	int newTemp()
	{
		return tempVal++;
	}

	public int getTemps()
	{
		return tempVal;
	}

	public Object visit(NumberExpr n){
		int t1 = newTemp();
		System.out.println("\tldr\tr0, ="+n.value);
		System.out.println("\tldr\tr2, =temps");
		System.out.println("\tstr\tr0, [r2, #"+(4*t1)+"]");
		return (Integer)t1;
	}

	public Object visit(IdExpr n){
		int t1 = newTemp();
		System.out.println("\tldr\tr0, =V_"+n.name);
		System.out.println("\tldr\tr2, =temps");
		System.out.println("\tldr\tr1, [r0]");
		System.out.println("\tstr\tr1, [r2, #"+(4*t1)+"]");
		return (Integer)t1;
	}

	public Object visit(Lower n){
		int t1 = newTemp();
		System.out.println("\tldr\tr2, =temps");
		System.out.println("\tldr\tr0, =V_"+n.name);
		System.out.println("\tldr\tr1, [r0]");
		System.out.println("\tldr\tr0, [r1]");
		System.out.println("\tstr\tr0, [r2, #"+(4*t1)+"]");
		return (Integer)t1;
	}

	public Object visit(Upper n){
		int t1 = newTemp();
		System.out.println("\tldr\tr2, =temps");
		System.out.println("\tldr\tr0, =V_"+n.name);
		System.out.println("\tldr\tr1, [r0]");
		System.out.println("\tldr\tr0, [r1, #4]");
		System.out.println("\tstr\tr0, [r2, #"+(4*t1)+"]");
		return (Integer)t1;
	}

	public Object visit(Item n){
		int idx = (Integer) n.index.accept(this);
		int t1 = newTemp();
		// load temps
		System.out.println("\tldr\tr2, =temps");
		System.out.println("\tldr\tr4, [r2, #"+(4*idx)+"]");
		// get array position
		System.out.println("\tldr\tr0, =V_"+n.name);
		System.out.println("\tldr\tr1, [r0]");
		// remove lower from index
		System.out.println("\tldr\tr0, [r1]");
		System.out.println("\tsub\tr4, r4, r0");
		// add 2 to index and multiply by 4 bits
		System.out.println("\tadd\tr4, r4, #2");
		System.out.println("\tmov\tr0, #4");
		System.out.println("\tmul\tr4, r4, r0");
		// fetch value
		System.out.println("\tldr\tr3, [r1, r4]");
		// place in temp
		System.out.println("\tstr\tr3, [r2, #"+(4*t1)+"]");
		return (Integer)t1;
	}

	public Object visit(Minus n){
		int tleft = (Integer)(n.left.accept(this));
		int tright = (Integer)(n.right.accept(this));
		int t = newTemp();
		System.out.println("\tldr\tr2, =temps");
		System.out.println("\tldr\tr0, [r2, #"+(4*tleft)+"]");
		System.out.println("\tldr\tr1, [r2, #"+(4*tright)+"]");
		System.out.println("\tsub\tr0, r0, r1");
		System.out.println("\tstr\tr0, [r2, #"+(4*t)+"]");
		return (Integer)t;
	}

	public Object visit(Plus n){
		int tleft = (Integer)(n.left.accept(this));
		int tright = (Integer)(n.right.accept(this));
		int t = newTemp();
		System.out.println("\tldr\tr2, =temps");
		System.out.println("\tldr\tr0, [r2, #"+(4*tleft)+"]");
		System.out.println("\tldr\tr1, [r2, #"+(4*tright)+"]");
		System.out.println("\tadd\tr0, r0, r1");
		System.out.println("\tstr\tr0, [r2, #"+(4*t)+"]");
		return (Integer)t;
	}

	public Object visit(Times n){
		int tleft = (Integer)(n.left.accept(this));
		int tright = (Integer)(n.right.accept(this));
		int t = newTemp();
		System.out.println("\tldr\tr2, =temps");
		System.out.println("\tldr\tr0, [r2, #"+(4*tleft)+"]");
		System.out.println("\tldr\tr1, [r2, #"+(4*tright)+"]");
		System.out.println("\tmul\tr0, r0, r1");
		System.out.println("\tstr\tr0, [r2, #"+(4*t)+"]");
		return (Integer)t;
	}

	public Object visit(IntDivide n){
		int tleft = (Integer)(n.left.accept(this));
		int tright = (Integer)(n.right.accept(this));
		int t = newTemp();
		System.out.println("\tldr\tr3, =temps");
		System.out.println("\tldr\tr0, [r3, #"+(4*tleft)+"]");
		System.out.println("\tldr\tr1, [r3, #"+(4*tright)+"]");
		System.out.println("\tbl\t__aeabi_idiv");
		System.out.println("\tldr\tr3, =temps");
		System.out.println("\tstr\tr0, [r3, #"+(4*t)+"]");
		return (Integer)t;
	}

	public Object visit(True n){
		int t = newTemp();
		System.out.println("\tldr\tr2, =temps");
		System.out.println("\tmov\tr0, #1");
		System.out.println("\tstr\tr0, [r2, #"+(4*t)+"]");
		return (Integer)t;
	}

	public Object visit(False n){
		int t = newTemp();
		System.out.println("\tldr\tr2, =temps");
		System.out.println("\tmov\tr0, #0");
		System.out.println("\tstr\tr0, [r2, #"+(4*t)+"]");
		return (Integer)t;
	}

	public Object visit(Equal n){
		int tleft = (Integer)(n.left.accept(this));
		int tright = (Integer)(n.right.accept(this));
		int t = newTemp();
		System.out.println("\tldr\tr2, =temps");
		System.out.println("\tldr\tr0, [r2, #"+(4*tleft)+"]");
		System.out.println("\tldr\tr1, [r2, #"+(4*tright)+"]");
		System.out.println("\tcmp\tr0, r1");
		System.out.println("\tmoveq\tr0, #1");
		System.out.println("\tmovne\tr0, #0");
		System.out.println("\tstr\tr0, [r2, #"+(4*t)+"]");
		return (Integer)t;
	}

	public Object visit(NotEqual n){
		int tleft = (Integer)(n.left.accept(this));
		int tright = (Integer)(n.right.accept(this));
		int t = newTemp();
		System.out.println("\tldr\tr2, =temps");
		System.out.println("\tldr\tr0, [r2, #"+(4*tleft)+"]");
		System.out.println("\tldr\tr1, [r2, #"+(4*tright)+"]");
		System.out.println("\tcmp\tr0, r1");
		System.out.println("\tmoveq\tr0, #0");
		System.out.println("\tmovne\tr0, #1");
		System.out.println("\tstr\tr0, [r2, #"+(4*t)+"]");
		return (Integer)t;
	}

	public Object visit(LessEqual n){
		int tleft = (Integer)(n.left.accept(this));
		int tright = (Integer)(n.right.accept(this));
		int t = newTemp();
		System.out.println("\tldr\tr2, =temps");
		System.out.println("\tldr\tr0, [r2, #"+(4*tleft)+"]");
		System.out.println("\tldr\tr1, [r2, #"+(4*tright)+"]");
		System.out.println("\tcmp\tr0, r1");
		System.out.println("\tmovle\tr0, #1");
		System.out.println("\tmovgt\tr0, #0");
		System.out.println("\tstr\tr0, [r2, #"+(4*t)+"]");
		return (Integer)t;
	}

	public Object visit(GreaterEqual n){
		int tleft = (Integer)(n.left.accept(this));
		int tright = (Integer)(n.right.accept(this));
		int t = newTemp();
		System.out.println("\tldr\tr2, =temps");
		System.out.println("\tldr\tr0, [r2, #"+(4*tleft)+"]");
		System.out.println("\tldr\tr1, [r2, #"+(4*tright)+"]");
		System.out.println("\tcmp\tr0, r1");
		System.out.println("\tmovge\tr0, #1");
		System.out.println("\tmovlt\tr0, #0");
		System.out.println("\tstr\tr0, [r2, #"+(4*t)+"]");
		return (Integer)t;
	}

	public Object visit(LessThan n){
		int tleft = (Integer)(n.left.accept(this));
		int tright = (Integer)(n.right.accept(this));
		int t = newTemp();
		System.out.println("\tldr\tr2, =temps");
		System.out.println("\tldr\tr0, [r2, #"+(4*tleft)+"]");
		System.out.println("\tldr\tr1, [r2, #"+(4*tright)+"]");
		System.out.println("\tcmp\tr0, r1");
		System.out.println("\tmovlt\tr0, #1");
		System.out.println("\tmovge\tr0, #0");
		System.out.println("\tstr\tr0, [r2, #"+(4*t)+"]");
		return (Integer)t;
	}

	public Object visit(GreaterThan n){
		int tleft = (Integer)(n.left.accept(this));
		int tright = (Integer)(n.right.accept(this));
		int t = newTemp();
		System.out.println("\tldr\tr2, =temps");
		System.out.println("\tldr\tr0, [r2, #"+(4*tleft)+"]");
		System.out.println("\tldr\tr1, [r2, #"+(4*tright)+"]");
		System.out.println("\tcmp\tr0, r1");
		System.out.println("\tmovgt\tr0, #1");
		System.out.println("\tmovle\tr0, #0");
		System.out.println("\tstr\tr0, [r2, #"+(4*t)+"]");
		return (Integer)t;
	}

	public Object visit(Not n){
		int texpr = (Integer)(n.expr.accept(this));
		int t = newTemp();
		System.out.println("\tldr\tr2, =temps");
		System.out.println("\tldr\tr0, [r2, #"+(4*texpr)+"]");
		System.out.println("\tcmp\tr0, #1");
		System.out.println("\tmoveq\tr0, #0");
		System.out.println("\tmovne\tr0, #1");
		System.out.println("\tstr\tr0, [r2, #"+(4*t)+"]");
		return (Integer)t;
	}

	public Object visit(And n){
		int tleft = (Integer)(n.left.accept(this));
		int tright = (Integer)(n.right.accept(this));
		int t = newTemp();
		System.out.println("\tldr\tr2, =temps");
		System.out.println("\tldr\tr0, [r2, #"+(4*tleft)+"]");
		System.out.println("\tldr\tr1, [r2, #"+(4*tright)+"]");
		System.out.println("\tadd\tr0, r0, r1");
		System.out.println("\tcmp\tr0, #2");
		System.out.println("\tmoveq\tr0, #1");
		System.out.println("\tmovne\tr0, #0");
		System.out.println("\tstr\tr0, [r2, #"+(4*t)+"]");
		return (Integer)t;
	}

	public Object visit(Or n){
		int tleft = (Integer)(n.left.accept(this));
		int tright = (Integer)(n.right.accept(this));
		int t = newTemp();
		System.out.println("\tldr\tr2, =temps");
		System.out.println("\tldr\tr0, [r2, #"+(4*tleft)+"]");
		System.out.println("\tldr\tr1, [r2, #"+(4*tright)+"]");
		System.out.println("\tadd\tr0, r0, r1");
		System.out.println("\tcmp\tr0, #1");
		System.out.println("\tmovge\tr0, #1");
		System.out.println("\tmovlt\tr0, #0");
		System.out.println("\tstr\tr0, [r2, #"+(4*t)+"]");
		return (Integer)t;
	}
}
