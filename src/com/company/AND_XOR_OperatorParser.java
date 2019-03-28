package com.company;

import java.io.InputStream;
import java.io.IOException;




class AND_XOR_OperatorParser {



    private int lookaheadToken;

    private InputStream in;

    public AND_XOR_OperatorParser(InputStream in) throws IOException {
      this.in = in;
      lookaheadToken = in.read();
    }

    private void consume(int symbol) throws IOException, ParseError {
  	  if (lookaheadToken != symbol)
  	    throw new ParseError();
  	  lookaheadToken = in.read();
    }

    private void Factor() throws IOException, ParseError {

      if(lookaheadToken=='('){
        consume('(');
        Expr();
        consume(')');
      }
      else if(lookaheadToken >='0' && lookaheadToken <='9')
      {
        consume(lookaheadToken);
      }
      else
      {
        throw new ParseError();
      }


    }
    private void Term2() throws IOException, ParseError {

      if(lookaheadToken=='^' || lookaheadToken==')' || lookaheadToken == '\n' || lookaheadToken == -1)
      {
        return;
      }
      consume('&');
      Factor();
      Term2();
    }

    private void Term() throws IOException, ParseError {
      Factor();
      Term2();
    }

    private void Expr2() throws IOException, ParseError {

      if(lookaheadToken==')' || lookaheadToken == '\n' || lookaheadToken == -1)
      {
        return;
      }
      consume('^');
      Term();
      Expr2();
    }

    private void Expr() throws IOException, ParseError {
      Term();
      Expr2();
    }

    public void parse() throws IOException, ParseError {
      Expr();

     if (lookaheadToken != '\n' && lookaheadToken != -1){
	        throw new ParseError();
     }
    }

    public static void main(String[] args) {
      try {
          AND_XOR_OperatorParser parser = new AND_XOR_OperatorParser(System.in);
          parser.parse();
      }
      catch (IOException e) {
          System.err.println(e.getMessage());
      }
      catch(ParseError err){
          System.err.println(err.getMessage());
      }
    }
}
