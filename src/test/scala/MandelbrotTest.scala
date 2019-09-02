import mymandelbrot.Mandelbrot
import org.scalatest.FunSuite
import org.scalatest.Matchers._

class MandelbrotTest extends FunSuite {
  test("Two number staring from zero") {
    val results = Mandelbrot.sequence(0, 2).toList

    println(results)
    results should have length 2

    assert(results === List(0, 0))
  }

  test("Three number staring from zero") {
    val results = Mandelbrot.sequence(0, 3).toList

    results should have length 3

    assert(results === List(0, 0, 0))
  }

  test("Four numbers staring from -1") {
    val results = Mandelbrot.sequence(-1, 4).toList

    results should have length 4

    assert(results === List(0, -1, 0, -1))
  }
}
