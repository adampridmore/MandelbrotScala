package mymandelbrot

import scala.collection.immutable

object Mandelbrot {
  def sequence(value: Double, numberToGenerate: Int): immutable.Seq[Double] = {
    new MandelbrotSeq(value)
      .take(numberToGenerate)
      .toList
  }
}

class MandelbrotSeq(val initialValue: Double) extends Iterator[Double] {
  private def nextValue = currentValue * currentValue + initialValue

  private var currentValue = initialValue

  override def hasNext: Boolean = {
    true
  }

  override def next(): Double = {
    currentValue = nextValue
    currentValue
  }
}