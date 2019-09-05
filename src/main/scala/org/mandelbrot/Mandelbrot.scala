package org.mandelbrot

import breeze.math.Complex
import org.mandelbrot.sequence.SequenceHelper.unfold

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

  def sequenceC(initialValue: Complex): immutable.Seq[Complex] = {
    unfold(initialValue)(previousValue => {
      val nextValue = previousValue.pow(2) + initialValue
      Some(nextValue, nextValue)
    })
  }
}
