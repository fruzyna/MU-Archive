package CodeGenPkg;
import AbSyn.Type;

public class VarEntry extends SymEntry{
	public Type type;
	public int  offset;

	public VarEntry(Type t, int off)
	{
		type = t;
		offset = off;
	}

	public String toString()
	{
		return "Var("+type+", "+offset+") ";
	}
}
