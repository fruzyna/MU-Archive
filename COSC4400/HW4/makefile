#
# makefile for CUPdemo
#
# written by mike slattery - feb 2001
# rewritten by mike slattery - mar 2005
# rewritten by mike slattery - mar 2019
#
JAVA=java
JAVAC=javac
JFLEX=../../jflex-1.6.1/bin/jflex
CUPJAR=../jflex-1.6.1/lib/java-cup-11a.jar
CUP=$(JAVA) -jar ../$(CUPJAR)
# Specify classpath
# For Linux:
CP=.:$(CUPJAR)

# For CygWin:
#CP=.\;$(CUPJAR)

Main.class: ParsePkg/Grm.class
	$(JAVAC) -classpath $(CP) Main.java

Main.class: AbSyn/AbSyn.class VisitorPkg/VisitorPkg.class
	$(JAVAC) -classpath $(CP) Main.java

ParsePkg/Grm.class: ParsePkg/Yylex.java ParsePkg/Grm.java AbSyn/AbSyn.class jgo
	$(JAVAC) -classpath $(CP) ParsePkg/*.java

ParsePkg/Grm.java: ParsePkg/one.cup
	cd ParsePkg; $(CUP) -parser Grm -expect 3 <one.cup

ParsePkg/Yylex.java: ParsePkg/one.flex
	cd ParsePkg; $(JFLEX) one.flex

AbSyn/AbSyn.class:  AbSyn/*.java
	$(JAVAC) -classpath $(CP) AbSyn/*.java

VisitorPkg/VisitorPkg.class:  VisitorPkg/*.java
	$(JAVAC) -classpath $(CP) VisitorPkg/*.java

#
# Create a one-line shell script to run the
# scanner with the correct classpath
#
# Usage: jgo file
#  (where file is the input program)
#
jgo: makefile
	echo "java -classpath $(CP) Main < \$$1" > jgo
	chmod a+x jgo

clean:
	rm ParsePkg/*.class AbSyn/*.class ParsePkg/Grm.java ParsePkg/sym.java ParsePkg/Yylex.java jgo VisitorPkg/*.class Main.class
