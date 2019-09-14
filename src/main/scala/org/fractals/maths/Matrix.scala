package org.fractals.maths

case class Matrix(v11: Double, v21: Double, v12: Double, v22: Double){

  def +(that: Matrix): Matrix = {
    Matrix(
      this.v11+that.v11,
      this.v21+that.v21,
      this.v12+that.v12,
      this.v22+that.v22,
    )
  }

  def*(that: Vector2) : Vector2 = {
    Vector2(
      this.v11 * that.x + this.v12 * that.y,
      this.v21 * that.x + this.v22 * that.y
    )
  }
}
