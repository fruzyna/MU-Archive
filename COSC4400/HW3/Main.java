public class Main {

  public static void main(String argv[]) throws java.io.IOException,
						 java.lang.Exception {
	// Program expects input file name on command line
      String filename = argv[0];
      java.io.Reader inp = new java.io.FileReader(filename);
      java_cup.runtime.Scanner lexer = new Yylex(inp);
      java_cup.runtime.Symbol tok;

      do { 
         tok=lexer.next_token();
	 System.out.println(tok.sym + " attr:" + tok.value);
      } while (tok.sym != sym.EOF);

      inp.close();
  }
}
