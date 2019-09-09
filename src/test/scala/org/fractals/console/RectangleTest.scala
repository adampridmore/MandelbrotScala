package org.fractals.console

import org.scalatest.{FunSuite, Matchers, WordSpec}

class RectangleTest extends WordSpec with Matchers {
  "Rectangle(100,200,300,400) " should {
    val rectangle = Rectangle(100,200,300,400)

    "have width 200" in {
      rectangle.width shouldBe 200
    }

    "have height 200" in {
      rectangle.height shouldBe 200
    }

    "translate to (110,220,310,420)" in {
      rectangle.translate(Coordinate(10, 20)) shouldBe Rectangle(110,220,310,420)
    }

    "shrink to (100,200,280,380)" in {
      rectangle.shrink(0.9) shouldBe Rectangle(100,200,280,380)
    }
  }
}
