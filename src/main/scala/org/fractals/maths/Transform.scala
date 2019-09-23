package org.fractals.maths

trait Transform {
  def perform(rectangle: Rectangle): Rectangle

  def perform(transform: Transform): Transform
}

case class Transforms(transforms: Transform*) {
  def selfApply(): Transforms = {
    Transforms(transforms.map(t => perform(t)): _*)
  }

  def perform(rectangle: Rectangle): Rectangle = {
    transforms.foldLeft(rectangle)((r: Rectangle, t: Transform) => t.perform(r))
  }

  def perform(transform: Transform): Transform = {
    transforms.foldLeft(transform)((s: Transform, t: Transform) => t.perform(s))
  }
}

object Transforms {
  //  def apply(transforms: Seq[Transform]): Transforms = new Transforms(transforms)
}

case class Translate(vector: Vector2) extends Transform {
  override def perform(rectangle: Rectangle): Rectangle = {
    rectangle.translate(vector)
  }

  override def perform(transform: Transform): Transform = {
    transform match {
      case Translate(v) => Translate(v.translate(v))
      case Scale(m) => ???
      case _ => ???
    }
  }
}

case class Scale(matrix: Matrix) extends Transform {
  override def perform(rectangle: Rectangle): Rectangle = {
    rectangle.scale(matrix)
  }

  override def perform(transform: Transform): Transform = ???
}