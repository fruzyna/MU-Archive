import java.util.HashMap;

/**
 * COSC 4400
 * Assignment 2
 * 
 * @author Liam Fruzyna
 */
public class Parser {

	public static void main(String[] args) {
		// print nothing to open up console on Eclipse
		System.out.print("");
		new Parser().run();
	}

	HashMap<String, VarEntry> symTable = null;
	Lexan lexer = null;
	int lookAhead;
	int labelCount = 0;

	void run() {
		// Load keywords into table
		symTable = new HashMap<String, VarEntry>();
		symTable.put("div", new VarEntry("div", Lexan.DIV));
		symTable.put("mod", new VarEntry("mod", Lexan.MOD));
		symTable.put("if", new VarEntry("if", Lexan.IF));
		symTable.put("then", new VarEntry("then", Lexan.THEN));
		symTable.put("while", new VarEntry("while", Lexan.WHILE));
		symTable.put("do", new VarEntry("do", Lexan.DO));
		symTable.put("begin", new VarEntry("begin", Lexan.BEGIN));
		symTable.put("end", new VarEntry("end", Lexan.END));
		
		// Init Lexan and start
		lexer = new Lexan(symTable);
		lookAhead = lexer.nextToken();
		while (lookAhead != Lexan.DONE) {
			// all items must derive from a statement
			stmt();
		}
		System.out.println();
	}

	// statement-list
	void stmt_list() {
		// statement statement-rest
		stmt(); stmt_rest();
	}
	
	// statement-rest
	void stmt_rest() {
		if(lookAhead == ';') {
			// ; statement statement-rest
			match(';'); stmt(); stmt_rest();
		}
		// otherwise just return (epsilon)
	}
	
	// statement
	void stmt() {
		switch(lookAhead) {
		case Lexan.ID:
			// assignment, starts with an id
			// id := expression
			Object id = lexer.tokenVal;
			match(Lexan.ID); match(Lexan.ASIGN); emit(Lexan.ASIGN, id); expr(); emit('=');
			break;
		case Lexan.IF:
			// if-then, starts with if
			// if expression then statement
			Integer ifLabel = new Integer(labelCount);
			labelCount++;
			match(Lexan.IF); expr(); emit(Lexan.IF, ifLabel); match(Lexan.THEN); stmt(); emit(Lexan.THEN, ifLabel);
			break;
		case Lexan.WHILE:
			// while-do, starts with while
			// while expression do statement
			Integer startL = new Integer(labelCount);
			labelCount++;
			Integer endL = new Integer(labelCount);
			labelCount++;
			match(Lexan.WHILE); emit(Lexan.LABEL, startL); expr(); match(Lexan.DO); emit(Lexan.DO, endL); stmt(); emit(Lexan.GOTO, startL); emit(Lexan.LABEL, endL);
			break;
		case Lexan.BEGIN:
			// begin-end, starts with begin
			// begin statement-list end
			match(Lexan.BEGIN); stmt_list(); match(Lexan.END);;
			break;
		default:
			error();
		}
	}
	
	// expression
	void expr() {
		// term expression-rest
		term(); exprRest();
	}

	// expression-rest
	void exprRest() {
		if (lookAhead == '+') {
			match('+'); term(); emit('+'); exprRest();
		} else if (lookAhead == '-') {
			match('-'); term(); emit('-'); exprRest();
		}
		// otherwise just return (epsilon)
	}

	// term
	void term() {
		// factor term-rest
		factor(); termRest();
	}

	// term-rest
	void termRest() {
		if (lookAhead == '*') {
			match('*'); factor(); emit('*'); termRest();
		} else if (lookAhead == '/') {
			match('/'); factor(); emit('/'); termRest();
		}
		if (lookAhead == Lexan.DIV) {
			match(Lexan.DIV); factor(); emit(Lexan.DIV); termRest();
		} else if (lookAhead == Lexan.MOD) {
			match(Lexan.MOD); factor(); emit(Lexan.MOD); termRest();
		}
		// otherwise just return (epsilon)
	}

	// factor
	void factor() {
		Object v;
		switch (lookAhead) {

		case Lexan.NUM:
			v = lexer.tokenVal;
			match(Lexan.NUM);
			emit(Lexan.NUM, v);
			break;
		case Lexan.ID:
			v = lexer.tokenVal;
			match(Lexan.ID);
			emit(Lexan.ID, v);
			break;
		case '(':
			match('(');
			expr();
			match(')');
			break;
		default:
			error();
		}
	}

	void match(int c) {
		if (lookAhead == c) {
			lookAhead = lexer.nextToken();
		} else
			error();
	}

	void emit(int t) {
		switch (t) {
		case '+':
		case '-':
		case '*':
		case '/':
			System.out.println((char) t);
			break;
		case '=':
			System.out.println("store");
			break;
		case Lexan.DIV:
			System.out.println("DIV");
			break;
		case Lexan.MOD:
			System.out.println("MOD");
			break;
		case Lexan.STORE:
			System.out.println("store");
			break;
		default:
			System.out.println("Unexpected token = " + t);
		}
	}

	void emit(int t, Object attrib) {
		switch (t) {
		case Lexan.NUM:
			System.out.println("push " + (Integer) attrib);
			break;
		case Lexan.ID:
			System.out.println("rvalue " + ((VarEntry) attrib).name);
			break;
		case Lexan.ASIGN:
			System.out.println("lvalue " + ((VarEntry) attrib).name);
			break;
		case Lexan.IF:
		case Lexan.DO:
			System.out.println("gofalse " + (Integer) attrib);
			break;
		case Lexan.THEN:
		case Lexan.LABEL:
			System.out.println("label " + (Integer) attrib);
			break;
		case Lexan.GOTO:
			System.out.println("goto " + (Integer) attrib);
			break;
		default:
			System.out.println("Unexpected token = " + t + ", tokenval = " + attrib);
		}
	}

	void error() {
		System.out.println("Syntax error encountered on line " + lexer.lineNum);
		System.exit(0);
	}
}