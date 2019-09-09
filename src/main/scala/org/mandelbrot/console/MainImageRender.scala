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
//    val gridSize = GridSize(x = 2560, y = 1600)
//    val gridSize = GridSize(x = 1024, y = 768)
    val gridSize = GridSize(x = 320, y = 200)

    val image = new BufferedImage(gridSize.x, gridSize.y, BufferedImage.TYPE_INT_RGB)

    def map(complex: Complex): Int = {
      (if (Mandelbrot.inSet(complex)) Color.black else Color.white).getRGB
    }

    GridToComplexViewIterator(gridSize, view)
      .sequence(map)
      .foreach { case (coord, color) => image.setRGB(coord.x, coord.y, color) }

    val filename = "generatedImages/image_mandelbrot.png"
    val stream = new FileOutputStream(filename)

    ImageIO.write(image, "png", stream)
    stream.close()

    Runtime.getRuntime.exec(s"open $filename")
  }

  render()

  0
}


