package org.fractals.console

import java.awt.image.BufferedImage
import java.awt.{Color, Graphics}
import java.io.FileOutputStream

import javax.imageio.ImageIO
import org.fractals.mandelbrot._

object SquareImageRender extends App {

  def render(rect: Rectangle, iterations: Int, graphics: Graphics): Unit = {
    iterations match {
      case 0 =>
        drawRectangle(rect, graphics)
      case x =>
        render(rect.translate(Coordinate(0, 0)), x - 1, graphics)
        render(rect.translate(Coordinate(0, 0)).shrink(0.5), x - 1, graphics)
        render(rect.translate(Coordinate(10, 10)).shrink(0.5), x - 1, graphics)
    }
  }

  def drawScene(gridSize: GridSize, graphics: Graphics): Unit = {
    graphics.setColor(Color.black)

    val rectangle = Rectangle(Coordinate(10, 10), Coordinate(gridSize.x - 50, gridSize.y - 50))
    render(rectangle, iterations = 10, graphics = graphics)
    //    drawRectangle(rectangle, graphics)
  }

  private def render(): Unit = {
    val gridSize = GridSize(x = 320, y = 200)

    val image = new BufferedImage(gridSize.x, gridSize.y, BufferedImage.TYPE_INT_RGB)

    val filename = "generatedImages/image_boxes.png"
    val stream = new FileOutputStream(filename)

    val graphics: Graphics = image.getGraphics
    graphics.fillRect(0, 0, gridSize.x, gridSize.y)

    drawScene(gridSize, graphics)

    graphics.dispose()

    ImageIO.write(image, "png", stream)
    stream.close()

    Runtime.getRuntime.exec(s"open $filename")
  }

  private def drawRectangle(rect: Rectangle, graphics: Graphics): Unit = {
    graphics.setColor(Color.black)
    graphics.drawRect(rect.topLeft.x, rect.topLeft.y, rect.width, rect.height)
  }


  render()
}


