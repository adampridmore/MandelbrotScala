package org.fractals.maths

case class Vector2(x: Double, y: Double){
  def +(that: Vector2): Vector2 = {
    Vector2(this.x + that.x, this.y + that.y)
  }

  def translate(that: Vector2): Vector2 = this + that

  def scale(factor: Matrix): Vector2 = factor * this
}
