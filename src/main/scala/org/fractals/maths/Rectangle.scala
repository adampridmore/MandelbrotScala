package org.fractals.maths

case class Rectangle(bottomLeft: Vector2, topRight: Vector2) {
  val width: Double = topRight.x - bottomLeft.x
  val height: Double = topRight.y - bottomLeft.y

  def translate(translation: Vector2): Rectangle =
    Rectangle(bottomLeft.translate(translation), topRight.translate(translation))

  def scale(factor: Matrix): Rectangle =
    copy(bottomLeft.scale(factor), topRight.scale(factor))

  @Deprecated
  def scale(factor: Double): Rectangle =
    copy(bottomLeft.scale(factor), topRight.scale(factor))
}

object Rectangle {
  def apply(x1: Double, y1: Double, x2: Double, y2: Double): Rectangle =
    Rectangle(Vector2(x1, y1), Vector2(x2, y2))
}