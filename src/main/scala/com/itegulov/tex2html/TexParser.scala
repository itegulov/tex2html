package com.itegulov.tex2html

import org.parboiled2._

/**
  * @author Daniyar Itegulov
  */
class TexParser(val input: ParserInput) extends Parser {
  def InputLine = rule { Expression ~ EOI }

  def Expression = rule { zeroOrMore(TexConstruction) }

  def TexConstruction: Rule1[TexElement] = rule { TexStringR | (InlineMath ~> (TexInlineMath(_))) }

  def InlineMath: Rule1[Seq[TexMathElement]] = rule { '$' ~ zeroOrMore(MathExpression ~ WhiteSpace) ~ '$' }

  def MathExpression: Rule1[TexMathElement] = rule { AtomicChar ~ zeroOrMore(
    ('^' ~ AtomicChar ~> Sup)
      | ('^' ~ '{' ~ AtomicWord ~ '}' ~> Sup)
      | ('_' ~ AtomicChar ~> Sub)
      | ('_' ~ '{' ~ AtomicWord ~ '}' ~> Sub))
  }

  def AtomicWord: Rule1[TexMathElement] = rule { TexMathOperatorR | TexMathNumberR | TexMathStringR }

  def AtomicChar: Rule1[TexMathElement] = rule { TexMathOperatorR | TexMathDigitR | TexMathCharR }

  def TexMathCharR: Rule1[TexMathString] = rule { capture(MathChar) ~> (TexMathString(_)) }

  def TexStringR: Rule1[TexString] = rule { capture(String) ~> (TexString(_)) }

  def TexMathStringR: Rule1[TexMathString] = rule { capture(MathString) ~> (TexMathString(_)) }

  def TexMathNumberR: Rule1[TexMathNumber] = rule { capture(Digits) ~> ((s: String) => TexMathNumber(BigInt(s))) }

  def TexMathDigitR: Rule1[TexMathNumber] = rule { capture(Digit) ~> ((s: String) => TexMathNumber(BigInt(s))) }

  def TexMathOperatorR: Rule1[TexMathOperator] = rule { capture(Operator) ~> (TexMathOperator(_)) }

  def Char = rule { CharPredicate.Printable -- '$' }

  def MathChar = rule { CharPredicate.Visible -- '$' -- '_' -- '^' -- '{' -- '}' }

  def String = rule { oneOrMore(Char) }

  def MathString = rule { oneOrMore(MathChar) }

  def Digit = rule { CharPredicate.Digit }

  def Digits = rule { oneOrMore(Digit) }

  def Operator = rule { CharPredicate('*', '+', '-', '/', '=') }

  def WhiteSpaceChar = CharPredicate(" \n\r\t\f")

  def WhiteSpace = rule { zeroOrMore(WhiteSpaceChar) }
}
