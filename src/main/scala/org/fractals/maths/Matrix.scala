package org.fractals.maths

case class Matrix(v11: Int, v21: Int, v12: Int, v22: Int){
  def +(that: Matrix): Matrix = {
    Matrix(
      this.v11+that.v11,
      this.v21+that.v21,
      this.v12+that.v12,
      this.v22+that.v22,
    )
  }
}
