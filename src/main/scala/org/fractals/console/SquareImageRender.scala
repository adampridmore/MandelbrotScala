package org.fractals.console

import java.awt.image.BufferedImage
import java.awt.{Color, Graphics}
import java.io.FileOutputStream
import java.time.Instant

import javax.imageio.ImageIO
import org.fractals.FileHelpers
import org.fractals.mandelbrot._
import org.fractals.maths.Rectangle

import scala.language.postfixOps
import scala.util.Random

object SquareImageRender extends App {
  implicit class Pipe[T](val v: T) extends AnyVal {
    def |>[U] (f: T => U) = f(v)
  }

  val rand = new Random(10)

  def renderIteration(rect: Rectangle, iterations: Int)
                     (implicit context: Context): Unit = {


    def toPosNeg(boolean: Boolean) = if (boolean) 1 else -1

//    def wobble: Double = (rect.width * rand.nextDouble() * context.wobbleRange) * (rand.nextBoolean() |> toPosNeg)
    def wobble: Double = (rect.width * context.wobbleRange)

    iterations match {
      case 0 => drawRectangle(rect)
      case _ =>
        val width = rect.topRight.x - rect.bottomLeft.x
        val height = rect.topRight.y - rect.bottomLeft.y

        val scale = 0.5
        val rectangles: Seq[Rectangle] = Seq(
          Rectangle(
            rect.bottomLeft.x,
            rect.bottomLeft.y,
            rect.topRight.x - width * scale,
            rect.topRight.y - height * scale),
          Rectangle(
            rect.bottomLeft.x,
            rect.bottomLeft.y + height * scale,
            rect.topRight.x - width * scale,
            rect.topRight.y),
          Rectangle(
            rect.bottomLeft.x + width * scale,
            rect.bottomLeft.y + height * scale,
            rect.topRight.x,
            rect.topRight.y),
          Rectangle(
            rect.bottomLeft.x + width * scale,
            rect.bottomLeft.y,
            rect.topRight.x,
            rect.topRight.y - height * scale))
          .map(r => Rectangle(
            r.bottomLeft.x + wobble,
            r.bottomLeft.y + wobble,
            r.topRight.x - wobble,
            r.topRight.y - wobble))

        // println(s"rectangles($iterations): ${rectangles.mkString(",")}")

        rectangles
          .take(3)
          .foreach(r => renderIteration(r, iterations - 1))
    }
  }

  def drawScene(gridSize: GridSize)(implicit context: Context): Unit = {
    context.graphics.setColor(Color.black)

    val rectangle = Rectangle(0, 0, 1, 1)

    renderIteration(rectangle, iterations = 11)
  }

  case class Context(graphics: Graphics, gridSize: GridSize, wobbleRange: Double)

  private def render(now: Instant)(index: Int): Unit = {
    println(s"index: $index")

    //val gridSize = GridSize(width = 200, height = 100)
    val scale = 1
    val gridSize = GridSize(width = 1024 * scale, height = 768 * scale)


    val image = new BufferedImage(gridSize.width, gridSize.height, BufferedImage.TYPE_INT_RGB)

    val filename = f"generatedImages/${now.getEpochSecond}_${index}_image_boxes.png"
    val stream = new FileOutputStream(filename)

    val graphics: Graphics = image.getGraphics
    graphics.fillRect(0, 0, gridSize.width, gridSize.height)

    implicit val context: Context = Context(graphics, gridSize, wobbleRange = index * 0.001)

    try {
      drawScene(gridSize)
    } catch {
      case ex: Exception =>
        graphics.dispose()
        throw ex
    }

    val formatName = "png"
    ImageIO.write(image, formatName, stream)
    stream.close()

//    FileHelpers.open(filename)
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

  val now = Instant.now

  (for(
    x <- 0 to 100
  ) yield x)
    .foreach(render(now))
}
