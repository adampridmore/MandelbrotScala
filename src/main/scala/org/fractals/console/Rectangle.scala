package org.fractals.console

case class Rectangle(topLeft: Coordinate, bottomRight: Coordinate) {
  val width: Int = bottomRight.x - topLeft.x
  val height: Int = bottomRight.y - topLeft.y

  def translate(translation: Coordinate): Rectangle = {
    Rectangle(topLeft.translate(translation), bottomRight.translate(translation))
  }

  // TODO: use matrix
  def shrink(factor: Double) : Rectangle = {

    def scale(left: Int, width: Int): Int = {
      (left + (width * factor)).toInt
    }

    copy(topLeft, Coordinate(scale(topLeft.x,width), scale(topLeft.y, height)))
  }
}

object Rectangle{
  def apply(x1: Int, y1: Int, x2: Int, y2: Int) : Rectangle = {
    Rectangle(Coordinate(x1, y1), Coordinate(x2,y2))
  }
}