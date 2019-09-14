package org.fractals.maths

import org.scalatest.{Matchers, WordSpec}

class CoordinateTest extends WordSpec with Matchers {
  "Coordinate" should {
    val coordinate = Coordinate(10,20)

    "translate to 110, 220" in {
      coordinate.translate(Coordinate(100, 200)) shouldBe Coordinate(110, 220)
    }

    "scale to 50,100" in {
      coordinate.scale(0.5) shouldBe Coordinate(5,10)
    }
  }
}
