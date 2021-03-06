package org.fractals.maths

import org.scalatest.{Matchers, WordSpec}

class MatrixSpec extends WordSpec with Matchers {
  "Matrix" when {
    val matrix = Matrix(1,2,3,4)
    "+ applied" in {
      matrix + Matrix(5,6,7,8) shouldBe Matrix(6,8,10,12)
    }

    "* by a Vector2(10,20)" in {
      matrix * Vector2(10,20) shouldBe Vector2(70,100)
    }
  }
}
