package mymandelbrot

import breeze.math.Complex
import mymandelbrot.SequenceHelper.unfold

import scala.collection.immutable

object Mandelbrot {
  def inSet(complex: Complex) = {
    !sequenceC(complex)
      .take(100)
      .exists(c => c.abs > 2)
  }

  def sequence(value: Double): immutable.Seq[Double] = {
    unfold(value)(previousValue => {
      val nextValue = previousValue * previousValue + value
      Some(nextValue, nextValue)
    })
  }

  def sequenceC(value: Complex): immutable.Seq[Complex] = {
    unfold(value)(previousValue => {
      val nextValue = previousValue * previousValue + value
      Some(nextValue, nextValue)
    })
  }
}