package org.fractals.maths

import org.scalatest.{Matchers, WordSpec}

class RectangleTest extends WordSpec with Matchers {
  "Rectangle(100,200,300,400) " should {
    val rectangle = Rectangle(100, 200, 300, 400)

    "have width 200" in {
      rectangle.width shouldBe 200
    }

    "have height 200" in {
      rectangle.height shouldBe 200
    }

    "translate to (110,220,310,420)" in {
      rectangle.translate(Vector2(10, 20)) shouldBe Rectangle(110, 220, 310, 420)
    }

    "shrink to (100,200,280,380)" in {
      rectangle.scale(Matrix(0.9, 0, 0, 0.9)) shouldBe Rectangle(90, 180, 270, 360)
    }
  }

  "Rectangle(100,100,500,500)" should {
    val rectangle = Rectangle(100, 100, 500, 500)
    "shrink by 0.5 to (50,50,250,250)" in {
      rectangle
        .scale(Matrix(0.5, 0, 0, 0.5)) shouldBe Rectangle(50, 50, 250, 250)
    }
    "transform to (250,250,500,500)" in {
      rectangle
        .scale(Matrix(0.5, 0, 0, 0.5))
        .translate(Vector2(250, 0)) shouldBe Rectangle(300, 50, 500, 250)
    }
  }
}