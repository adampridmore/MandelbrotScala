package org.fractals.maths

import org.scalatest.{Matchers, WordSpec}

class Vector2Spec extends WordSpec with Matchers {
  "vector2(10,20)" when {
    val vector2 = Vector2(10,20)
    "== applied another Vector2" should {
      "be true when Vector(10,20)" in {
        vector2 == Vector2(10,20) shouldBe true
      }
      "be false when Vector(30,40)" in {
        vector2 == Vector2(30,40) shouldBe false
      }
    }

    "+ by vector2(30,40)" in {
      Vector2(10, 20) + Vector2(30, 40) shouldBe Vector2(40, 60)
    }
  }
}