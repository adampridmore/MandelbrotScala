import mymandelbrot.Mandelbrot
import org.scalatest.FunSuite
import org.scalatest.Matchers._

class MandelbrotTest extends FunSuite {
  test("Two number staring from zero") {
    val results = Mandelbrot
      .sequence(0)
      .take(2)
      .toList

    results shouldBe Seq(0,0)
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

    results should have length 4

    assert(results === List(0, -1, 0, -1))
  }
}
