import breeze.math.Complex
import mymandelbrot.{ComplexViewPort, GridCoordinate, GridSize, GridToComplexMapping, Mandelbrot}

object Main extends App {

  val mapping = GridToComplexMapping(GridSize(x = 80,y = 32), ComplexViewPort(Complex(-2,-1), Complex(0.5,1)))

  val grid = Array.ofDim[String](mapping.gridSize.y, mapping.gridSize.x)

  def inSetToString(inSet: Boolean) = if (inSet) "*" else " "

  def gridToComplex(coordinate: GridCoordinate) = mapping.toComplex(coordinate)

  (for {
    x <- 0 until mapping.gridSize.x
    y <- 0 until mapping.gridSize.y
  } yield GridCoordinate(x = x, y = y))
    .map { coordinate => (gridToComplex(coordinate), coordinate) }
    .map { case (c, coord) => (Mandelbrot.inSet(c), coord) }
    .map { case (inSet, coord) => (inSetToString(inSet), coord) }
    .foreach { case (inSet, coord) => grid(coord.y)(coord.x) = inSet }

  grid.foreach { row => println(row.mkString) }
}