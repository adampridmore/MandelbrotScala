package org.fractals

case class Coordinate(x: Int, y: Int) {
  def scale(factor: Double): Coordinate = Coordinate((x * factor).toInt , (y * factor).toInt)

  def translate(translation: Coordinate): Coordinate = Coordinate(x + translation.x, y + translation.y)
}