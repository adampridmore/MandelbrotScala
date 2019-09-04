package mymandelbrot

import breeze.math.Complex
import org.scalatest.FunSuite
import org.scalatest.Matchers._

class MandelbrotTest extends FunSuite {
  test("Two number staring from zero") {
    val results = Mandelbrot
      .sequence(0)
      .take(2)
      .toList

    results shouldBe Seq(0, 0)
  }

  test("Two number staring from zero for complex") {
    val results = Mandelbrot
      .sequenceC(Complex(0, 0))
      .take(2)
      .toList

    results shouldBe Seq(0, 0)
  }

  test("Three number staring from zero") {
    val results = Mandelbrot.sequence(0)
      .take(3)
      .toList

    results shouldBe List(0, 0, 0)
  }

  test("Four numbers staring from -1") {
    val results = Mandelbrot
      .sequence(-1)
      .take(4)
      .toList

    results shouldBe List(0, -1, 0, -1)
  }

  test("Four numbers staring from 0.2 + 0.1i") {
    val results = Mandelbrot
      .sequenceC(Complex(0.2, 0.1))
      .take(1)
      .toList

    results shouldBe List(Complex(0.23, 0.14))
  }

  test("In set") {
    val inSet = Mandelbrot.inSet(Complex(0.2, 0.1))
    inSet shouldBe true
  }

  test("Not in set") {
    val inSet = Mandelbrot.inSet(Complex(5, 5))
    inSet shouldBe false
  }

  test("Period doubling") {
    val results = Mandelbrot
      .sequence(-0.25)
      .take(100)
      .toList

    println(results)
  }
}
