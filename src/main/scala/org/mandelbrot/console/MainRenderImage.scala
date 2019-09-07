package org.mandelbrot.console

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.FileOutputStream

import breeze.math.Complex
import javax.imageio.ImageIO
import org.mandelbrot._

object MainRenderImage extends App {

  private def render(): Unit = {
    val view = ComplexViewPort(Complex(-2, -1), Complex(0.5, 1))
    val mapping = GridToComplexMapping(GridSize(x = 100, y = 100), view)

    val image = new BufferedImage(mapping.gridSize.x, mapping.gridSize.y, BufferedImage.TYPE_INT_RGB)

    def inSetToColor(inSet: Boolean) = (if (inSet) Color.black else Color.white).getRGB

    def gridToComplex(coordinate: GridCoordinate) = mapping.toComplex(coordinate)

    (for {
      x <- 0 until image.getWidth
      y <- 0 until image.getHeight
    } yield GridCoordinate(x, y))
      .map { coordinate => (gridToComplex(coordinate), coordinate) }
      .map { case (c, coord) => (Mandelbrot.inSet(c), coord) }
      .map { case (inSet, coord) => (inSetToColor(inSet), coord) }
      .foreach { case (color, coord) => image.setRGB(coord.x,coord.y, color) }

    val stream = new FileOutputStream("generatedImages/image_mandelbrot.png")

    ImageIO.write(image, "png", stream)
    stream.close()
  }

  render()
}


