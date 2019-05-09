package CodeGenPkg;

import VisitorPkg.*;
import AbSyn.*;
import java.util.HashMap;
import java.util.ArrayList;

/*
 * Build symbol table for Toddler Gustave program
 * by mike slattery, apr 2019
 */
public class DeclReader extends NoopVisitor
{
	HashMap<String, SymEntry> symTab = new HashMap<String, SymEntry>();
	HashMap<String, SymEntry> currentTab = symTab;

	int labels = 0;
	int offset, delta_off;

	public String newLabel(){
		return "M_" + labels++;
	}

	public static String tableToString(HashMap<String, SymEntry> hm)
	{
		StringBuffer sb = new StringBuffer();
		for (String key: hm.keySet())
			sb.append(key+" ==> "+hm.get(key)+"\n");
		return new String(sb);
	}

	public HashMap<String, SymEntry> getTable()
	{
		return symTab;
	}

	public void dumpTable()
	{
		System.out.println("======Symbol Table=======");
		System.out.println(tableToString(symTab));
	}

	public void visit(Program n){
		n.root.accept(this);
	}

	public void visit(RootClass n){
		if (n.instanceVars != null)
		{
			offset = 0;
			delta_off = 4;
			n.instanceVars.accept(this);
		}
	currentTab = new HashMap<String, SymEntry>();
    if (n.localVars != null){
	offset = -4;
	delta_off = -4;
      n.localVars.accept(this);
    }
	symTab.put("make", new MethodEntry(newLabel(), currentTab, varLength(n.localVars), null));
	currentTab = symTab;
	    if (n.methods != null) {
	      n.methods.accept(this);
	    }
	}

	public void visit(PairVarDecList n){
		n.head.accept(this);
		n.tail.accept(this);
	}

	public void visit(LastVarDecList n){
		n.head.accept(this);
	}

	public int varLength(VarDecList n){
		if (n == null)
		return 0;
		if (n instanceof LastVarDecList)
		  return 1;
		else
		  return 1 + varLength(((PairVarDecList)n).tail);
	}

	public void visit(VarDec n){
		currentTab.put(n.name, new VarEntry(n.type, offset));
		offset += delta_off;
	}

  public void visit(LastMethodDecList n){
    n.head.accept(this);
  }


  public void visit(MethodDec n){
	currentTab = new HashMap<String, SymEntry>();
    if (n.params != null){
	offset = 8;
	delta_off = 4;
      n.params.accept(this);
    }
    if (n.locals != null){
	offset = -4;
	delta_off = -4;
      n.locals.accept(this);
    }
	symTab.put(n.name, new MethodEntry(newLabel(), currentTab, varLength(n.locals), n.type));
	currentTab = symTab;
  }


  public void visit(PairMethodDecList n){
    n.head.accept(this);
    n.tail.accept(this);
  }


}
