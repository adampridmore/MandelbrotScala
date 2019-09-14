package org.fractals.console

import java.awt.image.BufferedImage
import java.awt.{Color, Graphics}
import java.io.FileOutputStream

import javax.imageio.ImageIO
import org.fractals.mandelbrot._
import org.fractals.maths.{Matrix, Rectangle, Vector2}

import scala.util.Random

object SquareImageRender extends App {

  def pickColor(i: Int) = {
    val colors = Seq(Color.red, Color.green, Color.blue, Color.yellow, Color.magenta, Color.cyan)
    colors(i % colors.size)
  }

  type Transforms = Seq[Rectangle => Rectangle]

  def render(rect: Rectangle, transforms: Transforms, iterations: Int)(implicit context: Context): Unit = {
    iterations match {
      case 0 =>
        drawRectangle(rect)
      case x =>

        transforms
          .foreach(t => render(t(rect), transforms, x - 1))
    }
  }

  def drawScene(gridSize: GridSize, transforms: Transforms)(implicit context: Context): Unit = {
    context.graphics.setColor(Color.black)

    val offset = 20

    val rectangle = Rectangle(
      Vector2(offset, offset),
      Vector2(gridSize.width - 1 - offset, gridSize.height - 1 - offset))

    render(rectangle, transforms, iterations = 10)
  }

  case class Context(graphics: Graphics, gridSize: GridSize)

  def buildRandomTransforms(index: Int): Seq[Rectangle => Rectangle] = {

    //scale(Matrix(0.4177198264054247,0.7452955793015856,0.43699313134167417,-0.6718761009775782)), transform(Vector2(-14.5850736370323,-16.283307951952416))

    val random = new Random(index)

    def randomScale() = (random.nextDouble() * 2) - 1
    val scale = Matrix(randomScale(), randomScale(), randomScale(), randomScale())

    def randomTransform() = (random.nextDouble() * 20) - 20
    val transform = Vector2(randomTransform(), randomTransform())

    println(s"$index: scale($scale), transform($transform)")

    {
      Seq(
        {rect :Rectangle => rect},
        {rect :Rectangle => rect.scale(scale).translate(Vector2(30, 30))}
      )
    }
  }


  private def render(): Unit = {
    //    val gridSize = GridSize(width = 320, height = 200)
    val gridSize = GridSize(width = 1024, height = 768)

    val image = new BufferedImage(gridSize.width, gridSize.height, BufferedImage.TYPE_INT_RGB)


    Range
      .inclusive(0, 1000)
      .foreach(index => {
        val indexPadded = index.formatted("%05d")
        val filename =f"generatedImages/${indexPadded}image_boxes.png"
        val stream = new FileOutputStream(filename)

        val graphics: Graphics = image.getGraphics
        graphics.fillRect(0, 0, gridSize.width, gridSize.height)

        implicit val context: Context = Context(graphics, gridSize)

        try {

          val transforms = buildRandomTransforms(index)

          drawScene(gridSize,transforms)
        } catch {
          case ex: Exception => {
            graphics.dispose();
            throw ex
          }
        }

        ImageIO.write(image, "png", stream)
        stream.close()
      })

    //    FileHelpers.open(filename)
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


