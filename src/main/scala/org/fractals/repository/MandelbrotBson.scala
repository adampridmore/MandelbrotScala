package org.fractals.repository

import org.fractals.domain.MandelbrotImage
import reactivemongo.bson.{BSONDocumentReader, BSONDocumentWriter, Macros}

trait MandelbrotBson {
  implicit def imageWriter: BSONDocumentWriter[MandelbrotImage] =
    Macros.writer[MandelbrotImage]

  implicit def imageReader: BSONDocumentReader[MandelbrotImage] =
    Macros.reader[MandelbrotImage]
}
