package mymandelbrot

import mymandelbrot.SequenceHelper.unfold

import scala.collection.immutable

object Mandelbrot {
  def sequence(value: Double): immutable.Seq[Double] = {
    unfold(value)(previousValue => {
      val nextValue = previousValue * previousValue + value
      Some(nextValue, nextValue)
    })
  }
}