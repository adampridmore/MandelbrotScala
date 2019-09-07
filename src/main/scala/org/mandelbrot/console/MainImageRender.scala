package org.mandelbrot.console

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.FileOutputStream

import breeze.math.Complex
import javax.imageio.ImageIO
import org.mandelbrot._

object MainImageRender extends App {

  private def render(): Unit = {
    val view = ComplexViewPort(Complex(-2, -1), Complex(0.5, 1))
    val gridSize = GridSize(x = 100, y = 100)

    val image = new BufferedImage(gridSize.x, gridSize.y, BufferedImage.TYPE_INT_RGB)

    def map(complex: Complex): Int = {
      (if (Mandelbrot.inSet(complex)) Color.black else Color.white).getRGB
    }

    GridToComplexViewIterator(gridSize, view)
      .sequence(map)
      .foreach { case (coord, color) => image.setRGB(coord.x, coord.y, color) }

    val stream = new FileOutputStream("generatedImages/image_mandelbrot.png")

    ImageIO.write(image, "png", stream)
    stream.close()
  }

  render()

  0
}


