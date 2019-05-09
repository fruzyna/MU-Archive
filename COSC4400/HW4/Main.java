import java_cup.runtime.*;
import ParsePkg.*;
import AbSyn.*;
import VisitorPkg.*;
import java.io.Reader;
import java.io.InputStreamReader;

public class Main {

  public static void main(String[] args) throws java.lang.Exception {
    Reader inp = new InputStreamReader(System.in);
    Program root = new Grm(new Yylex(inp)).getTree();

    // Without parser, just build a tree by hand and print it
    // This is the tree the parser should build for the input
    /*
    Program root = new Program(
					  new RootClass("HELLO_WORLD", null, null,
    					new LastStmtList(
							new PutString("Hello World.\n")
						),
					  null),
				   null);*/
    root.accept(new PrettyPrintVisitor());
  }
}
