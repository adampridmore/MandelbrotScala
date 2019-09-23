package org.fractals.console

import java.awt.image.BufferedImage
import java.awt.{Color, Graphics}
import java.io.FileOutputStream

import javax.imageio.ImageIO
import org.fractals.FileHelpers
import org.fractals.mandelbrot._
import org.fractals.maths.Rectangle

object SquareImageRender extends App {
  def renderIteration(rect: Rectangle, iterations: Int)(implicit context: Context): Unit = {

    iterations match {
      case 0 =>
        drawRectangle(rect)
      case x => {
        val width = rect.topRight.x - rect.bottomLeft.x
        val height = rect.topRight.y - rect.bottomLeft.y

        val rectangles: Seq[Rectangle] = Seq(
          Rectangle(
            rect.bottomLeft.x,
            rect.bottomLeft.y,
            rect.topRight.x - width / 2,
            rect.topRight.y - height / 2),
          Rectangle(
            rect.bottomLeft.x,
            rect.bottomLeft.y + height / 2,
            rect.topRight.x - width / 2,
            rect.topRight.y),
          Rectangle(
            rect.bottomLeft.x + width / 2,
            rect.bottomLeft.y + height / 2,
            rect.topRight.x,
            rect.topRight.y),
          Rectangle(
            rect.bottomLeft.x + width / 2,
            rect.bottomLeft.y,
            rect.topRight.x,
            rect.topRight.y - height / 2))

//        println(s"rectangles($iterations): ${rectangles.mkString(",")}")

        rectangles
          .take(3)
          .foreach(r => renderIteration(r, iterations - 1))
      }
    }
  }

  def drawScene(gridSize: GridSize)(implicit context: Context): Unit = {
    context.graphics.setColor(Color.black)

    val rectangle = Rectangle(0, 0, 1, 1)

    renderIteration(rectangle, iterations = 5)
  }

  case class Context(graphics: Graphics, gridSize: GridSize)

  private def render(): Unit = {
    //val gridSize = GridSize(width = 200, height = 100)
    val scale = 1
    val gridSize = GridSize(width = 1024 * scale, height = 768 * scale)

    val image = new BufferedImage(gridSize.width, gridSize.height, BufferedImage.TYPE_INT_RGB)

    val filename = f"generatedImages/image_boxes.png"
    val stream = new FileOutputStream(filename)

    val graphics: Graphics = image.getGraphics
    graphics.fillRect(0, 0, gridSize.width, gridSize.height)

    implicit val context: Context = Context(graphics, gridSize)

    try {
      drawScene(gridSize)
    } catch {
      case ex: Exception => {
        graphics.dispose();
        throw ex
      }
    }

    val formatName = "png"
    ImageIO.write(image, formatName, stream)
    stream.close()

    FileHelpers.open(filename)
  }

  private def drawRectangle(rect: Rectangle)(implicit context: Context): Unit = {
    def mapPointToX(x: Double): Double = {
      x * context.gridSize.width
    }

    def mapPointToY(y: Double): Double = {
      y * context.gridSize.height
    }

    val drawRectValues = (
      mapPointToX(rect.bottomLeft.x).toInt,
      mapPointToY(rect.bottomLeft.y).toInt,
      mapPointToX(rect.topRight.x - rect.bottomLeft.x).toInt,
      mapPointToY(rect.topRight.y - rect.bottomLeft.y).toInt)

    context.graphics.setColor(Color.black)

    (context.graphics.drawRect _ tupled) (drawRectValues)
  }

  render()
}


