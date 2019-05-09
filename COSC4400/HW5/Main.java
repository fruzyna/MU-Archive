import java_cup.runtime.*;
import ParsePkg.*;
import AbSyn.*;
import CodeGenPkg.*;
import VisitorPkg.*;
import java.io.Reader;
import java.io.InputStreamReader;

public class Main {

  public static void main(String[] args) throws java.lang.Exception {
    Reader inp = new InputStreamReader(System.in);
    Program root = new Grm(new Yylex(inp)).getTree();

    //root.accept(new PrettyPrintVisitor());
    root.accept(new Coder());
  }
}
