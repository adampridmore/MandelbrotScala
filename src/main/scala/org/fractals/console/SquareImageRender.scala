package org.fractals.console

import java.awt.image.BufferedImage
import java.awt.{Color, Graphics}
import java.io.FileOutputStream

import javax.imageio.ImageIO
import org.fractals.FileHelpers
import org.fractals.mandelbrot._
import org.fractals.maths._

object SquareImageRender extends App {

  def pickColor(i: Int) = {
    val colors = Seq(Color.red, Color.green, Color.blue, Color.yellow, Color.magenta, Color.cyan)
    colors(i % colors.size)
  }

  def render(rect: Rectangle,
             transforms: Seq[Transforms],
             iterations: Int)(implicit context: Context): Unit = {
    iterations match {
      case 0 => drawRectangle(rect)
      case _ =>

        val newTransforms = transforms.map(t=>t.selfApply())

        transforms
          .map(transforms => transforms.perform(rect))
          .foreach(rectangle => render(rectangle, transforms, iterations - 1))
    }
  }

  def drawScene(gridSize: GridSize, transforms: Seq[Transforms])
               (implicit context: Context): Unit = {
    context.graphics.setColor(Color.black)

    val offset = 20

    val rectangle = Rectangle(
      Vector2(offset, offset),
      Vector2(gridSize.width - 1 - offset, gridSize.height - 1 - offset))

    render(rectangle, transforms, iterations = 2)
  }

  case class Context(graphics: Graphics, gridSize: GridSize)

  private def render(): Unit = {
    //    val gridSize = GridSize(width = 320, height = 200)
    val gridSize = GridSize(width = 1024, height = 768)

    val image = new BufferedImage(gridSize.width, gridSize.height, BufferedImage.TYPE_INT_RGB)

    val filename = f"generatedImages/image_boxes.png"
    val stream = new FileOutputStream(filename)

    val graphics: Graphics = image.getGraphics
    graphics.fillRect(0, 0, gridSize.width, gridSize.height)

    implicit val context: Context = Context(graphics, gridSize)

    try {

      val transforms: Seq[Transforms] = Seq(
        Transforms(Scale(Matrix(0.5, 0, 0, 0.5))),
        Transforms(Scale(Matrix(0.5, 0, 0, 0.5)), Translate(Vector2(0, gridSize.height))),
        Transforms(Scale(Matrix(0.5, 0, 0, 0.5)), Translate(Vector2(gridSize.width, 0))),
        Transforms(Scale(Matrix(0.5, 0, 0, 0.5)), Translate(Vector2(gridSize.width, gridSize.height)))
      )

      drawScene(gridSize, transforms)
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
    context.graphics.drawRect(
      rect.bottomLeft.x.toInt,
      (context.gridSize.height - rect.topRight.y).toInt,
      rect.width.toInt,
      rect.height.toInt)
  }

  render()
}


