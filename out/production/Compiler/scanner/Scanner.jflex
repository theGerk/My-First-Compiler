/* declaration */

package scanner;

%%
%public
%class Scanner
%function nextToken
%type Token
%line
%column
%eofval{
	return null;
%eofval}

/* paterns */

whitespace	= [ \n\t]|\r\n
letter		= [A-Za-z_]
digit		= [0-9]
word		= ({letter})({letter}|{digit})*
integer		= [+-]?{digit}+
real		= [+-]?{digit}+\.{digit}+
symbol		= <>|<=|>=|:=|[;,.:\[\]()+\-=*<>/]
comment		= \{[^\}]*\}


%%

/** lexical rules
*	yytext creates a function that prints out the word */

{word}	{
	System.out.println("Found a word: " + yytext());
	return(new Token(yytext(), yyline, yycolumn));
}

{integer}   {
    System.out.println("found a int: " + yytext());
    return(new Token(Integer.parseInt(yytext()), yytext(), yyline, yycolumn));
}

{real}		{
	System.out.println("Found a real: " + yytext());
	return(new Token(Float.parseFloat(yytext()), yytext(), yyline, yycolumn));
}

{symbol}	{
	System.out.println("Found a symbol: " + yytext());
	return(new Token(yytext(), yyline, yycolumn));
}

{whitespace} {
	//do nothing
}

{comment}	{
	System.out.println("\t----\tHere be a comment.\t----");
	// do nothing
}

.	{
	System.out.println("You done fucked up.");
}


