/*
 * Liam Fruzyna
 * COSC 4400
 * Assignment 3
 * Gustave Scanner
 */
import java_cup.runtime.Symbol;
%%
%cup
%public

%eofval{
	{ return new Symbol(sym.EOF); }
%eofval}

%{
	StringBuffer string = new StringBuffer();

	private Symbol symbol(int type) {
		return new Symbol(type, yyline, yycolumn);
	}
	private Symbol symbol(int type, Object value) {
		return new Symbol(type, yyline, yycolumn, value);
	}
%}

digit	= [0-9]
letter	= [A-Za-z]
comment	= --[^\r\n]*
id		= {letter}({letter}|{digit}|_)*

%state STRING

%%
<YYINITIAL> {
	// types
    "INTEGER"			{ return new Symbol(sym.INT); }
    "BOOLEAN"			{ return new Symbol(sym.BOOL); }
    "ARRAY"				{ return new Symbol(sym.ARRAY); }

	// operators
    ":="				{ return new Symbol(sym.ASSIGN); }
    "+"					{ return new Symbol(sym.PLUS); }
    "-"					{ return new Symbol(sym.MINUS); }
    "*"					{ return new Symbol(sym.MULT); }
    "//"				{ return new Symbol(sym.DIV); }
    "("					{ return new Symbol(sym.LPAREN); }
    ")"					{ return new Symbol(sym.RPAREN); }
    "["					{ return new Symbol(sym.LSB); }
    "]"					{ return new Symbol(sym.RSB); }
    "."					{ return new Symbol(sym.DOT); }
    "@"					{ return new Symbol(sym.AT); }
	"!!"				{ return new Symbol(sym.BANG); }
    ","					{ return new Symbol(sym.SEP); }
    ";"					{ return new Symbol(sym.SEMIC); }
    ":"					{ return new Symbol(sym.COLON); }

	// comparisons
    "<="				{ return new Symbol(sym.LTE); }
    ">="				{ return new Symbol(sym.GTE); }
    "<"					{ return new Symbol(sym.LT); }
    ">"					{ return new Symbol(sym.GT); }
    "="					{ return new Symbol(sym.EQ); }
    "/="				{ return new Symbol(sym.NEQ); }
    "not"				{ return new Symbol(sym.NOT); }
    "and"				{ return new Symbol(sym.AND); }
    "or"				{ return new Symbol(sym.OR); }
    "true"				{ return new Symbol(sym.TRUE); }
    "false"				{ return new Symbol(sym.FALSE); }

	// blocks
    "if"				{ return new Symbol(sym.IF); }
    "then"				{ return new Symbol(sym.THEN); }
    "else"				{ return new Symbol(sym.ELSE); }
    "from"				{ return new Symbol(sym.FROM); }
    "until"				{ return new Symbol(sym.UNTIL); }
    "loop"				{ return new Symbol(sym.LOOP); }
    "is"				{ return new Symbol(sym.IS); }
    "do"				{ return new Symbol(sym.DO); }
    "end"				{ return new Symbol(sym.END); }

	// methods
    "local"				{ return new Symbol(sym.LOCAL); }
    "require"			{ return new Symbol(sym.REQ); }
    "ensure"			{ return new Symbol(sym.ENSURE); }

	// sections
    "class"				{ return new Symbol(sym.CLASS); }
    "method"			{ return new Symbol(sym.METHOD); }
    "attribute"			{ return new Symbol(sym.ATTRIB); }
    "creation"			{ return new Symbol(sym.CREATE); }

	// functions
    "make"				{ return new Symbol(sym.MAKE); }
    "io.put_integer"	{ return new Symbol(sym.PUTINT); }
    "io.put_string"		{ return new Symbol(sym.PUTSTR); }
    "put"				{ return new Symbol(sym.PUT); }
    "item"				{ return new Symbol(sym.ITEM); }
    "lower"				{ return new Symbol(sym.LOW); }
	"upper"				{ return new Symbol(sym.UP); }

	// identifiers and literals
    {digit}+			{ return new Symbol(sym.NUMBER, new Integer(yytext())); }
    {id}				{ return new Symbol(sym.ID, yytext()); }
    \"					{ string.setLength(0); yybegin(STRING); }

	// ignored
    {comment}			{ /* ignore comments */ }
    [ \t\n]				{ /* ignore white space. */ }
}

// string definitions
<STRING> {
    \"					{ yybegin(YYINITIAL); return symbol(sym.STRING_LITERAL, string.toString()); }
    [^\n\r\"%]+			{ string.append(yytext()); }
    "%N"				{ string.append('\n'); }
    "%\""				{ string.append('\"'); }
    "%%"				{ string.append('%'); }
}

[^] { System.err.println("Illegal character: "+yytext()); }