package org.fractals.maths

case class Rectangle(bottomLeft: Coordinate, topRight: Coordinate) {
  val width: Int = topRight.x - bottomLeft.x
  val height: Int = topRight.y - bottomLeft.y

  def translate(translation: Coordinate): Rectangle =
    Rectangle(bottomLeft.translate(translation), topRight.translate(translation))

  def scale(factor: Double): Rectangle =
    copy(bottomLeft.scale(factor), topRight.scale(factor))
}

object Rectangle {
  def apply(x1: Int, y1: Int, x2: Int, y2: Int): Rectangle =
    Rectangle(Coordinate(x1, y1), Coordinate(x2, y2))
}