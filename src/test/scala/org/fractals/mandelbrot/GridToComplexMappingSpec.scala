package org.fractals.mandelbrot

import breeze.math.Complex
import org.scalatest.{Matchers, WordSpec}

class GridToComplexMappingSpec extends WordSpec with Matchers {
  "GridSize(100, 100" when {

    val gridSize = GridSize(100, 100)

    "ComplexViewPort(0,0,100,100)" should {
      val complexViewPort = ComplexViewPort(Complex(0,0), Complex(100,100))
      val mapping = new GridToComplexMapping(gridSize, complexViewPort)

      "map 0,0 to 0,0" in {
        mapping.toComplex(GridCoordinate(0, 0)) shouldBe Complex(0, 0)
      }

      "map 100,100 to 100,100" in {
        mapping.toComplex(GridCoordinate(100, 100)) shouldBe Complex(100, 100)
      }
      "map 25,75 to 25,75" in {
        mapping.toComplex(GridCoordinate(100, 100)) shouldBe Complex(100, 100)
      }
    }

    "ComplexViewPort(0,0,50,50)" should {
      val complexViewPort = ComplexViewPort(Complex(0,0), Complex(50,50))
      val mapping = new GridToComplexMapping(gridSize, complexViewPort)

      "map 0,0 to 0,0" in {
        mapping.toComplex(GridCoordinate(0, 0)) shouldBe Complex(0, 0)
      }

      "map 100,100 to 50,50" in {
        mapping.toComplex(GridCoordinate(100, 100)) shouldBe Complex(50, 50)
      }
      "map 25,75 to 12.5,37.5" in {
        mapping.toComplex(GridCoordinate(25, 75)) shouldBe Complex(12.5, 37.5)
      }
    }

    "ComplexViewPort(100,100,200,200)" should {
      val complexViewPort = ComplexViewPort(Complex(100,100), Complex(200,200))
      val mapping = new GridToComplexMapping(gridSize, complexViewPort)

      "map 0,0 to 100,100" in {
        mapping.toComplex(GridCoordinate(0, 0)) shouldBe Complex(100, 100)
      }

      "map 100,100 to 200,200" in {
        mapping.toComplex(GridCoordinate(100, 100)) shouldBe Complex(200, 200)
      }
    }
  }
}
