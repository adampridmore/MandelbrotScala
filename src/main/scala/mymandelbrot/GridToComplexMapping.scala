package mymandelbrot

import breeze.math.Complex

case class GridCoordinate(x: Int, y: Int)

case class GridSize(x: Int, y: Int)

case class ComplexViewPort(bottomLeft: Complex, topRight: Complex)

case class GridToComplexMapping(size: GridSize, viewPort: ComplexViewPort) {

  private val complexWidth = viewPort.topRight.real - viewPort.bottomLeft.real
  private val complexHeight = viewPort.topRight.imag - viewPort.bottomLeft.imag

  private val sizeXDouble = size.x.toDouble
  private val sizeYDouble = size.y.toDouble

  def toComplex(coordinate: GridCoordinate): Complex = {
    val complexX = mapToView(complexWidth, sizeXDouble, coordinate.x, viewPort.bottomLeft.real)
    val complexY = mapToView(complexHeight, sizeYDouble, coordinate.y, viewPort.bottomLeft.imag)

    Complex(complexX, complexY)
  }

  private def mapToView(complexSize: Double, gridSize: Double, coordinateValue: Int, lowerViewValue: Double): Double = {
    ((complexSize / gridSize) * coordinateValue) + lowerViewValue
  }
}
