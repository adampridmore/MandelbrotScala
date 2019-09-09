package org.fractals.mandelbrot

import breeze.math.Complex

case class GridCoordinate(x: Int, y: Int)

case class GridSize(x: Int, y: Int)

case class ComplexViewPort(bottomLeft: Complex, topRight: Complex)

class GridToComplexMapping(gridSize: GridSize, complexViewPort: ComplexViewPort) {

  private val complexWidth = complexViewPort.topRight.real - complexViewPort.bottomLeft.real
  private val complexHeight = complexViewPort.topRight.imag - complexViewPort.bottomLeft.imag

  private val sizeXDouble = gridSize.x.toDouble
  private val sizeYDouble = gridSize.y.toDouble

  def toComplex(coordinate: GridCoordinate): Complex = {
    val complexX = mapToView(complexWidth, sizeXDouble, coordinate.x, complexViewPort.bottomLeft.real)
    val complexY = mapToView(complexHeight, sizeYDouble, coordinate.y, complexViewPort.bottomLeft.imag)

    Complex(complexX, complexY)
  }

  private def mapToView(complexSize: Double, gridSize: Double, coordinateValue: Int, lowerViewValue: Double): Double = {
    ((complexSize / gridSize) * coordinateValue) + lowerViewValue
  }
}

case class GridToComplexViewIterator(private val gridSize: GridSize,
                                private val complexViewPort: ComplexViewPort)
  extends GridToComplexMapping(gridSize, complexViewPort) {

  def sequence[R](mapper: Complex => R): Seq[(GridCoordinate, R)] = (for {
    x <- 0 until gridSize.x
    y <- 0 until gridSize.y
  } yield GridCoordinate(x, y))
    .map { coordinate => (coordinate, toComplex(coordinate)) }
    .map { case (coordinate, complex) => (coordinate, mapper(complex)) }
}