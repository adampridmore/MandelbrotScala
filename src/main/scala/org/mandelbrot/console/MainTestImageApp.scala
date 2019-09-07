package org.mandelbrot.console

import java.awt.image.BufferedImage
import java.awt.{BasicStroke, Color}
import java.io.FileOutputStream

import breeze.math.Complex
import javax.imageio.ImageIO
import org.mandelbrot.{ComplexViewPort, GridSize, GridToComplexMapping}

object MainTestImageApp extends App {
  val image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB)

  val view = ComplexViewPort(Complex(-2, -1), Complex(0.5, 1))
  val mapping = GridToComplexMapping(GridSize(x = 80, y = 32), view)

  (for {
    x <- 0 until image.getWidth
    y <- 0 until image.getHeight
  } yield (x, y))
    .foreach {
      case (x, y) => image.setRGB(x, y, getColor(x, y).getRGB)
    }

  val gc = image.createGraphics()

  gc.setColor(Color.green)
  gc.setStroke(new BasicStroke(3))
  gc.drawLine(0, 0, 100, 100)
  val stream = new FileOutputStream("generatedImages/image.png")

  def getColor(coord: (Int, Int)): Color = coord match {
    case (x, _) if x > 25 && x < 75 => Color.blue
    case _ => Color.red
  }

  ImageIO.write(image, "png", stream)
  stream.close()
}
