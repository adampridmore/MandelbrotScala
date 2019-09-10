package org.fractals.console

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.{File, FileOutputStream}

import breeze.math.Complex
import com.sun.javafx.util.Utils._
import javax.imageio.ImageIO
import org.fractals.mandelbrot._

object MandelbrotImageRender extends App {

  def open(filename: String) = {

    val cmd = (isWindows, isMac) match {
      case (true, _) => Array("cmd.exe", "/c", filename)
      case (_, true) => Array(s"open $filename")
      case _ => throw new Exception(s"Unknown OS: ${System.getProperty("os.name")}")
    }

    Runtime.getRuntime.exec(cmd)
  }

  private def render(): Unit = {
    val view = ComplexViewPort(Complex(-2, -1), Complex(0.5, 1))
//        val gridSize = GridSize(x = 2560 * 4, y = 1600 * 4)
//        val gridSize = GridSize(x = 2560, y = 1600)
    //    val gridSize = GridSize(x = 1024, y = 768)
    val gridSize = GridSize(x = 320, y = 200)

    val image = new BufferedImage(gridSize.x, gridSize.y, BufferedImage.TYPE_INT_RGB)

    def map(complex: Complex): Int = {
      (if (Mandelbrot.inSet(complex)) Color.black else Color.white).getRGB
    }

    GridToComplexViewIterator(gridSize, view)
      .sequence(map)
      .foreach { case (coord, color) => image.setRGB(coord.x, coord.y, color) }


    val filename = s"generatedImages${File.separator}image_mandelbrot.png"
    println(s"Filename: $filename")
    val stream = new FileOutputStream(filename)

    ImageIO.write(image, "png", stream)
    stream.close()

    open(filename)
  }

  render()

  0
}


