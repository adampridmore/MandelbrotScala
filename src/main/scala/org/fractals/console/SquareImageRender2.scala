package org.fractals.console

import java.awt.image.BufferedImage
import java.awt.{Color, Graphics}
import java.io.FileOutputStream

import javax.imageio.ImageIO
import org.fractals.FileHelpers
import org.fractals.mandelbrot._
import org.fractals.maths.{Rectangle, Vector2}

case class ViewPort(bottomLeft: Vector2, topRight: Vector2)

object SquareImageRender2 extends App {

  def render(rect: Rectangle, viewPort: ViewPort, iterations: Int)(implicit context: Context): Unit = {
    iterations match {
      case 0 =>
        drawRectangle(rect, viewPort)
      case x =>
        render(rect, viewPort, x - 1)
    }
  }

  def drawScene(gridSize: GridSize)(implicit context: Context): Unit = {
    context.graphics.setColor(Color.black)

    val offset = 20

    val rectangle = Rectangle(0.1, 0.1, 0.9, 0.9)

    val viewPort = ViewPort(Vector2.zero, Vector2(gridSize))

    drawRectangle(rectangle, viewPort)

    //    render(rectangle, viewPort, iterations = 10)
  }

  case class Context(graphics: Graphics, gridSize: GridSize)

  private def render(): Unit = {
    //    val gridSize = GridSize(width = 320, height = 200)
    val gridSize = GridSize(width = 200, height = 100)

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

    ImageIO.write(image, "png", stream)
    stream.close()

    FileHelpers.open(filename)
  }

  private def drawRectangle(rect: Rectangle, viewPort: ViewPort)(implicit context: Context): Unit = {
    context.graphics.setColor(Color.black)

    def mapPointToX(x: Double) = {
      x * (viewPort.topRight.x - viewPort.bottomLeft.x)
    }

    def mapPointToY(y: Double) = {
      y * (viewPort.topRight.y - viewPort.bottomLeft.y)
    }

    val drawRectValues = (
      mapPointToX(rect.bottomLeft.x).toInt,
      mapPointToY(rect.bottomLeft.y).toInt,
      mapPointToX(rect.topRight.x - rect.bottomLeft.x).toInt,
      mapPointToY(rect.topRight.y - rect.bottomLeft.y).toInt)

    (context.graphics.drawRect _ tupled) (drawRectValues)
  }

  render()
}


