package CodeGenPkg;

import VisitorPkg.*;
import AbSyn.*;
import java.util.HashMap;
import java.util.ArrayList;

/*
 * Small piece of BabyGustave code generation
 * by mike slattery, mar 2019
 */
public class Coder extends NoopVisitor
{
	HashMap<String, Type> symTab = new HashMap<String, Type>();
	HashMap<String, String> strTab = new HashMap<String, String>();
	ExprCoder ecoder = new ExprCoder(symTab);

	int labels = 0;
	int strings = 0;

	public String newLabel(){
		return "L_" + labels++;
	}

	public String newString(String str) {
		String lab = "F_" + strings++;
		strTab.put(lab, str);
		return lab;
	}

	public void visit(Program n){
		n.root.accept(this);
		printSuffix();
	}

	/*
	 * print out the assembler code wrap up including
	 * vars and temp space
	 */
	void printSuffix()
	{
		System.out.println("\tldr\tr0, =F_close");
		System.out.println("\tbl\tkprintf");
		System.out.println("\tmov\tr0, r0\t@ nop");
		System.out.println("\tsub\tsp, fp, #4");
		System.out.println("\t@ sp needed");
		System.out.println("\tpop\t{fp, pc}");
		System.out.println("\t.align\t2");
		System.out.println("\t.size\tgustave, .-gustave");
		System.out.println("\t.ident\t\"GCC: (GNU Tools for ARM Embedded Processors) 4.9.3 20150529 (release) [ARM/embedded-4_9-branch revision 227977]\"");
		System.out.println("\t.section\t.rodata");
		System.out.println("\t.align\t2");
		System.out.println("F_intro:");
		System.out.println("\t.ascii\t\"****Begin Gustave Run****\\015\\012\\000\"");
		System.out.println("\t.align\t2");
		System.out.println("F_close:");
		System.out.println("\t.ascii\t\"\\015\\012*****End Gustave Run*****\\015\\012\\000\"");
		System.out.println("\t.align\t2");
		System.out.println("F_int:");
		System.out.println("\t.ascii\t\"%d \\000\"");
		System.out.println("\t.align\t2");
		for (String label:strTab.keySet())
		{
			System.out.println(label + ":\n\t.ascii\t\"" + strTab.get(label).replace("\n", "\\012").replace("\r", "\\015") + "\\000\"");
			System.out.println("\t.align\t2");
		}
		System.out.println("F_newline:");
		System.out.println("\t.ascii\t\"\\015\\012\\000\"");
		System.out.println("\t.section\t.data");
		System.out.println("\t.align 2");
		for (String name:symTab.keySet())
		{
			System.out.println("V_"+name+":\n\t.word\t0");
		}
		System.out.println("temps:");
		System.out.println("\t.space\t"+(4*ecoder.getTemps()));
	}

	public void visit(RootClass n){
		if (n.instanceVars != null)
			n.instanceVars.accept(this);
		n.body.accept(this);
	}

	public void visit(PairVarDecList n){
		n.head.accept(this);
		n.tail.accept(this);
	}

	public void visit(LastVarDecList n){
		n.head.accept(this);
	}

	public void visit(VarDec n){
		symTab.put(n.name, n.type);
	}

	public void visit(PairStmtList n){
		n.head.accept(this);
		n.tail.accept(this);
	}

	public void visit(LastStmtList n){
		n.head.accept(this);
	}

	public void visit(Assign n){
		int val = (Integer) n.value.accept(ecoder);
		System.out.println("\tldr\tr2, =temps");
		System.out.println("\tldr\tr0, [r2, #"+(4*val)+"]");
		System.out.println("\tldr\tr1, =V_"+n.var);
		System.out.println("\tstr\tr0, [r1]");
	}

	public void visit(CreateIntArray n){
		int lower = (Integer) n.lower.accept(ecoder);
		int upper = (Integer) n.upper.accept(ecoder);
		// get temps
		System.out.println("\tldr\tr2, =temps");
		System.out.println("\tldr\tr3, [r2, #"+(4*lower)+"]");
		System.out.println("\tldr\tr4, [r2, #"+(4*upper)+"]");
		// get difference
		System.out.println("\tsub\tr0, r4, r3");
		// add 2 places and multiply by 4 bits
		System.out.println("\tadd\tr0, r0, #2");
		System.out.println("\tmov\tr1, #4");
		System.out.println("\tmul\tr0, r0, r1");
		// save upper delta from beginning
		System.out.println("\tsub\tr3, r0, #4");
		// request memory
		System.out.println("\tbl\tmalloc");
		// point variable at memory location
		System.out.println("\tldr\tr1, =V_"+n.name);
		System.out.println("\tstr\tr0, [r1]");
		// save lower and upper
		System.out.println("\tldr\tr2, =temps");
		System.out.println("\tldr\tr3, [r2, #"+(4*lower)+"]");
		System.out.println("\tldr\tr4, [r2, #"+(4*upper)+"]");
		System.out.println("\tstr\tr3, [r0]");
		System.out.println("\tstr\tr4, [r0, #4]");

	}

	public void visit(ArrayPut n){
		int val = (Integer) n.value.accept(ecoder);
		int idx = (Integer) n.index.accept(ecoder);
		// get temps
		System.out.println("\tldr\tr2, =temps");
		System.out.println("\tldr\tr3, [r2, #"+(4*val)+"]");
		System.out.println("\tldr\tr4, [r2, #"+(4*idx)+"]");
		// get position and lower of array
		System.out.println("\tldr\tr0, =V_"+n.name);
		System.out.println("\tldr\tr1, [r0]");
		// remove lower from index
		System.out.println("\tldr\tr0, [r1]");
		System.out.println("\tsub\tr4, r4, r0");
		// add 2 to index and multiply by 4 bits
		System.out.println("\tadd\tr4, r4, #2");
		System.out.println("\tmov\tr0, #4");
		System.out.println("\tmul\tr4, r4, r0");
		// store value at new index
		System.out.println("\tstr\tr3, [r1, r4]");
	}

	public void visit(If n){
		String lend = newLabel();
		int cond = (Integer) n.cond.accept(ecoder);
		System.out.println("\tldr\tr2, =temps");
		System.out.println("\tldr\tr1, [r2, #"+(4*cond)+"]");
		System.out.println("\tcmp\tr1, #1");
		System.out.println("\tbne\t"+lend);
		n.body.accept(this);
		System.out.println(lend + ":"); 
	}

	public void visit(IfElse n){
		String lel = newLabel();
		String lend = newLabel();
		int cond = (Integer) n.cond.accept(ecoder);
		System.out.println("\tldr\tr2, =temps");
		System.out.println("\tldr\tr1, [r2, #"+(4*cond)+"]");
		System.out.println("\tcmp\tr1, #1");
		System.out.println("\tbne\t"+lel);
		// start if condition
		n.trueBody.accept(this);
		System.out.println("\tb\t"+lend);
		// start else condition
		System.out.println(lel + ":"); 
		n.falseBody.accept(this);
		// post statement
		System.out.println(lend + ":"); 
	}

	public void visit(From n){
		String lloop = newLabel();
		String lend = newLabel();
		if(n.init != null)
			n.init.accept(this);
		System.out.println("\tldr\tr2, =temps");
		// start loop
		System.out.println(lloop + ":");
		// check condition
		int cond = (Integer) n.cond.accept(ecoder);
		System.out.println("\tldr\tr1, [r2, #"+(4*cond)+"]");
		System.out.println("\tcmp\tr1, #1");
		System.out.println("\tbeq\t"+lend);
		// loop body
		n.body.accept(this);
		// end loop
		System.out.println("\tb\t"+lloop);
		System.out.println(lend + ":");
	}

	public void visit(PutString n){
		String l = newString(n.msg);
		System.out.println("\tldr\tr0, ="+l);
		System.out.println("\tbl\tkprintf");
	}

	public void visit(PutInteger n){
		int t1 = (Integer)n.expr.accept(ecoder);
		System.out.println("\tldr\tr0, =F_int");
		System.out.println("\tldr\tr2, =temps");
		System.out.println("\tldr\tr1, [r2, #"+(4*t1)+"]");
		System.out.println("\tbl\tkprintf");
		//System.out.println("\tldr\tr0, =F_newline");
		//System.out.println("\tbl\tkprintf");
	}
}
