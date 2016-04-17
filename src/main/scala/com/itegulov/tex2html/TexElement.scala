package com.itegulov.tex2html

/**
  * @author Daniyar Itegulov
  */
sealed trait TexElement

case class TexString(string: String) extends TexElement

case class TexInlineMath(children: Seq[TexMathElement]) extends TexElement

sealed trait TexMathElement extends TexElement

case class TexMathNumber(number: BigInt) extends TexMathElement

case class TexMathOperator(operator: String) extends TexMathElement

case class TexMathString(string: String) extends TexMathElement

case class Sup(left: TexMathElement, right: TexMathElement) extends TexMathElement

case class Sub(left: TexMathElement, right: TexMathElement) extends TexMathElement
