package org.fractals.console

case class Coordinate(x: Int, y: Int) {
  def translate(translation: Coordinate): Coordinate = {
    Coordinate(x + translation.x, y + translation.y)
  }
}
