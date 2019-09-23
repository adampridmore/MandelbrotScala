package org.fractals.maths

import org.fractals.mandelbrot.GridSize

case class Vector2(x: Double, y: Double){
  def +(that: Vector2): Vector2 = {
    Vector2(this.x + that.x, this.y + that.y)
  }

  def translate(that: Vector2): Vector2 = this + that

  def scale(factor: Matrix): Vector2 = factor * this
}

object Vector2 {
  val zero : Vector2 = Vector2(0,0)

  def apply(gridSize: GridSize): Vector2 = Vector2(gridSize.width, gridSize.height)
}
