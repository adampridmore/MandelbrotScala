package org.fractals.repository

import org.fractals.domain.MandelbrotImage
import reactivemongo.bson.{BSONDocumentReader, BSONDocumentWriter, Macros}

import scala.concurrent.ExecutionContext

trait MandelbrotBson {
  implicit def imageWriter: BSONDocumentWriter[MandelbrotImage] =
    Macros.writer[MandelbrotImage]

  implicit def imageReader: BSONDocumentReader[MandelbrotImage] =
    Macros.reader[MandelbrotImage]
}

abstract class MandelbrotImageRepository(implicit val ec: ExecutionContext)
  extends Read[MandelbrotImage]
    with Write[MandelbrotImage]
    with MandelbrotBson {

  def collectionName() = "mandelbrotImages2"
}