package org.fractals.maths

case class Vector2(x: Int, y: Int){
  def +(that: Vector2): Vector2 = {
    Vector2(this.x + that.x, this.y + that.y)
  }
}
