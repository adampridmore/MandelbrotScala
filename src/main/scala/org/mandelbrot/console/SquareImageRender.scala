package org.mandelbrot.console

import java.awt.image.BufferedImage
import java.awt.{Color, Graphics}
import java.io.FileOutputStream

import javax.imageio.ImageIO
import org.mandelbrot._

case class Point(x: Int, y: Int) {
  def translate(translation: Point): Point = {
    Point(x + translation.x, y + translation.y)
  }
}

case class Rectangle(topLeft: Point, bottomRight: Point) {
  val width: Int = bottomRight.x - topLeft.x
  val height: Int = bottomRight.y - topLeft.y

  def translate(translation: Point): Rectangle = {
    Rectangle(topLeft.translate(translation), bottomRight.translate(translation))
  }
}

object SquareImageRender extends App {

  def render(rect: Rectangle, iterations: Int, graphics: Graphics): Unit = {
    iterations match {
      case 0 => {
        drawRectangle(rect, graphics)
      }
      case x => {
        render(rect.translate(Point(0,0)), x - 1,graphics)
        render(rect.translate(Point(40,40)), x - 1,graphics)
      }
    }
  }

  def drawScene(gridSize: GridSize, graphics: Graphics): Unit = {
    graphics.setColor(Color.black)

    val rectangle = Rectangle(Point(10, 10), Point(gridSize.x - 10, gridSize.y - 10))
    render(rectangle, iterations = 4, graphics = graphics)
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

  0
}


