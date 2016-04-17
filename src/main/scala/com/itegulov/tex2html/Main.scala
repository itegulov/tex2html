package com.itegulov.tex2html

/**
  * @author Daniyar Itegulov
  */
object Main extends App {
  val parser = new TexParser("$a^2 + b^2 = c^2$")
  println(parser.InputLine.run())
}
