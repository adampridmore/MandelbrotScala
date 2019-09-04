import breeze.math.Complex
import mymandelbrot.Mandelbrot

object Main extends App {
  val size = 100
  val scale = 40d

  val grid = Array.ofDim[String](size, size)

  def inSetToString(inSet: Boolean) = if (inSet) "*" else " "

  (for {
    x <- 0 until size
    y <- 0 until size
  } yield (x, y))
    .map { case (x, y) => (Complex((x.toDouble - (size.toDouble / 2d)) / scale, (y.toDouble - (size.toDouble / 2d)) / scale), (x, y)) }
    .map { case (c, coord) => (Mandelbrot.inSet(c), coord) }
    .map { case (inSet, coord) => (inSetToString(inSet), coord) }
    .foreach { case (inSet, coord) => grid(coord._2)(coord._1) = inSet }

  grid.foreach { row => println(row.mkString) }
}