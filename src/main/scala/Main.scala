import breeze.math.Complex
import mymandelbrot.Mandelbrot

case class GridCoordinate(x: Int, y: Int)

object Main extends App {
  val gridSize = 100
  val scale = 40d

  val graphBottom = -1d
  val graphTop = 1d

  val grid = Array.ofDim[String](gridSize, gridSize)

  def inSetToString(inSet: Boolean) = if (inSet) "*" else " "

  def gridToComplex(coordinate: GridCoordinate) = {
    val x = (coordinate.x.toDouble - (gridSize.toDouble / 2d)) / scale
    val y = (coordinate.y.toDouble - (gridSize.toDouble / 2d)) / scale

    Complex(x, y)
  }

  (for {
    x <- 0 until gridSize
    y <- 0 until gridSize
  } yield GridCoordinate(x, y))
    .map { coordinate => (gridToComplex(coordinate), coordinate)}
    .map { case (c, coord) => (Mandelbrot.inSet(c), coord) }
    .map { case (inSet, coord) => (inSetToString(inSet), coord) }
    .foreach { case (inSet, coord) => grid(coord.y)(coord.x) = inSet }

  grid.foreach { row => println(row.mkString) }
}