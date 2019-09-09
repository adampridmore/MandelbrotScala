package org.fractals.console

import breeze.math.Complex
import org.fractals.mandelbrot._

object MandelbrotConsoleRender extends App {

  def render(): Unit = {
    val gridSize =  GridSize(x = 80, y = 32)
    val viewPort = ComplexViewPort(Complex(-2, -1), Complex(0.5, 1))

    val grid = Array.ofDim[String](gridSize.y, gridSize.x)

    def map(complex: Complex): String = if (Mandelbrot.inSet(complex)) "*" else " "

    GridToComplexViewIterator(gridSize, viewPort)
      .sequence(map)
      .foreach { case (coord, value) => grid(coord.y)(coord.x) = value }

    grid.foreach { row => println(row.mkString) }
  }

  render()

  0
}


