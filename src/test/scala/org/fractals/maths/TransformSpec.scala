package org.fractals.maths

import org.scalatest.{Matchers, WordSpec}

class TransformSpec extends WordSpec with Matchers {
  "vector2(10,20)" when {
    val vector2 = Vector2(10, 20)
    "transformed by a translate" in {

      val transform = Translate(vector2)

      transform.perform(Rectangle(x1 = 10, y1 = 20, x2 = 30, y2 = 40)) shouldBe Rectangle(20, 40, 40, 60)

      vector2 == Vector2(10, 20) shouldBe true
    }

    "transforms by a scale" in {

      val transform = Scale(Matrix(0.5, 0, 0, 0.5))

      transform.perform(Rectangle(x1 = 10, y1 = 20, x2 = 30, y2 = 40)) shouldBe Rectangle(5, 10, 15, 20)

      vector2 == Vector2(10, 20) shouldBe true
    }
  }

  "Seq of transforms are applied to a rectangle" in {
    val transforms = Transforms(Translate(Vector2(10, 20)), Translate(Vector2(30, 40)))

    val rectangle = Rectangle(0, 0, 0, 0)

    transforms.perform(rectangle) shouldBe Rectangle(40,60,40,60)
  }

  "Seq of transforms can be self applied" in {
    val transforms = Transforms(Translate(Vector2(10, 20)), Translate(Vector2(30, 40)))

    transforms.selfApply() shouldBe Transforms(Translate(Vector2(50, 80)), Translate(Vector2(70, 100)))
  }
}