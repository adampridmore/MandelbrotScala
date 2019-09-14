package org.fractals.console

import java.awt.image.BufferedImage
import java.awt.{Color, Graphics}
import java.io.FileOutputStream

import javax.imageio.ImageIO
import org.fractals.mandelbrot._
import org.fractals.maths.{Coordinate, Rectangle}
import org.fractals.FileHelpers

object SquareImageRender extends App {

  def pickColor(i: Int) = {
    val colors = Seq(Color.red, Color.green, Color.blue, Color.yellow, Color.magenta, Color.cyan)
    colors(i % colors.size)
  }

  def render(rect: Rectangle, iterations: Int, color: Color = Color.black)(implicit context: Context): Unit = {
    iterations match {
      case 0 =>
        drawRectangle(rect)
      case x =>
        val transformed = {
          Seq(
            rect,
//            rect.scale(0.6),
            {
              val factor = 0.8
              val temp = rect.scale(factor)
//              temp.translate(Coordinate((temp.width * factor).toInt, (temp.height * factor).toInt ))
              temp.translate(Coordinate(30,30))
            }
          )
        }

        transformed
          .foreach(t => render(t, x - 1, pickColor(iterations)))
    }
  }

  def drawScene(gridSize: GridSize)(implicit context: Context): Unit = {
    context.graphics.setColor(Color.black)

    val offset = 10

    val rectangle = Rectangle(Coordinate(offset, offset), Coordinate(gridSize.width - 1 - offset, gridSize.height - 1 - offset))
    render(rectangle, iterations = 20)
  }

  case class Context(graphics: Graphics, gridSize: GridSize)

  private def render(): Unit = {
    //    val gridSize = GridSize(width = 320, height = 200)
    val gridSize = GridSize(width = 1024, height = 768)

    val image = new BufferedImage(gridSize.width, gridSize.height, BufferedImage.TYPE_INT_RGB)

    val filename = "generatedImages/image_boxes.png"
    val stream = new FileOutputStream(filename)

    val graphics: Graphics = image.getGraphics
    graphics.fillRect(0, 0, gridSize.width, gridSize.height)

    implicit val context: Context = Context(graphics, gridSize)

    try {
      drawScene(gridSize)
    } catch {
      case ex: Exception => {
        graphics.dispose()
        throw ex
      }
    }

    ImageIO.write(image, "png", stream)
    stream.close()

    FileHelpers.open(filename)
  }

  private def drawRectangle(rect: Rectangle)(implicit context: Context): Unit = {
    drawRectangle(rect, Color.black)
  }

  private def drawRectangle(rect: Rectangle, color: Color)(implicit context: Context): Unit = {
    context.graphics.setColor(color)
    context.graphics.drawRect(rect.bottomLeft.x, context.gridSize.height - rect.topRight.y, rect.width, rect.height)
  }

  render()
}


