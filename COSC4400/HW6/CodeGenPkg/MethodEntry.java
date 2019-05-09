package CodeGenPkg;
import AbSyn.Type;
import java.util.HashMap;

public class MethodEntry extends SymEntry{
	String start_label;
	HashMap<String, SymEntry> locals; // this will hold local vars and params
	int localsize;  // number of words needed (to build activation record)
	Type return_type;

	public MethodEntry(String lab, HashMap<String, SymEntry> loc, int s, Type t)
	{
		start_label = lab;
		locals = loc;
		localsize = s;
		return_type = t;
	}

	public String toString()
	{
		return "Method("+start_label+", "+localsize+", "+return_type+", <local table below>):\n"+DeclReader.tableToString(locals);
	}
}
