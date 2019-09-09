package org.fractals.console

import org.scalatest.{FunSuite, Matchers, WordSpec}

class CoordinateTest extends WordSpec with Matchers {
  "Coordinate" should {
    val coordinate = Coordinate(10,20)
    "translate to 110, 220" in {
      coordinate.translate(Coordinate(100, 200)) shouldBe Coordinate(110, 220)
    }
  }
}
