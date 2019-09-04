package mymandelbrot

import breeze.math.Complex

case class GridCoordinate(x: Int, y: Int)

case class GridSize(x: Int, y: Int)

case class ComplexViewPort(bottomLeft: Complex, topRight: Complex)

case class GridToComplexMapping(size: GridSize, viewPort: ComplexViewPort) {

  val complexWidth = viewPort.topRight.real - viewPort.bottomLeft.real
  val complexHeight = viewPort.topRight.imag - viewPort.bottomLeft.imag

  def toComplex(coordinate: GridCoordinate) = {

    println(s"coordinate.x: ${coordinate.x} size.x: ${size.x} coordinate.x: ${coordinate.x}")
    val complexX = ((complexWidth / size.x.toDouble) * coordinate.x) + viewPort.bottomLeft.real
    val complexY = ((complexHeight / size.y.toDouble) * coordinate.y) + viewPort.bottomLeft.imag

    Complex(complexX, complexY)
  }
}
