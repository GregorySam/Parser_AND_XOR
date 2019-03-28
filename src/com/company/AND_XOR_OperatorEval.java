package com.company;

import java.io.InputStream;
import java.io.IOException;

class AND_XOR_OperatorEval {

  private int lookaheadToken;

  private InputStream in;

  public AND_XOR_OperatorEval(InputStream in) throws IOException {
    this.in = in;
    lookaheadToken = in.read();
  }

  private void consume(int symbol) throws IOException, ParseError {
    if (lookaheadToken != symbol)
      throw new ParseError();
    lookaheadToken = in.read();
  }

  private int evalDigit(int digit) {
    return digit - '0';
  }


  private int Factor() throws IOException, ParseError {

    if(lookaheadToken=='('){
      consume('(');
      int expr=Expr();
      consume(')');
      return expr;
    }
    else if(lookaheadToken >='0' && lookaheadToken <='9')
    {
      int eD= evalDigit(lookaheadToken);
      consume(lookaheadToken);
      return eD;
    }
    else
    {
      throw new ParseError();
    }
  }

  private int Term2(int factor) throws IOException, ParseError {

    if(lookaheadToken=='^' || lookaheadToken==')' || lookaheadToken == '\n' || lookaheadToken == -1)
    {
      return factor;
    }
    consume('&');

    factor=factor & Factor();

    return Term2(factor);
  }

  private int Term() throws IOException, ParseError {

    int factor=Factor();

    return Term2(factor);
  }

  private int Expr2(int term) throws IOException, ParseError {

    if(lookaheadToken==')' || lookaheadToken == '\n' || lookaheadToken == -1)
    {
      return term;
    }
    consume('^');

    term=term ^ Term();

    return  Expr2(term);

  }


  private int Expr() throws IOException, ParseError {
    int term=Term();

    return Expr2(term);

  }

  public int eval() throws IOException, ParseError {
    int rv = Expr();
    if (lookaheadToken != '\n' && lookaheadToken != -1)
      throw new ParseError();
    return rv;
  }

  public static void main(String[] args) {
    try {
      AND_XOR_OperatorEval evaluate = new AND_XOR_OperatorEval(System.in);
      System.out.println(evaluate.eval());
    } catch (IOException e) {
      System.err.println(e.getMessage());
    } catch (ParseError err) {
      System.err.println(err.getMessage());
    }
  }
}

