package org.fractals.repository

import org.fractals.domain.MandelbrotImage
import reactivemongo.bson.document
import scala.concurrent.{ExecutionContext, Future}

class MandelbrotRepository(implicit val ec: ExecutionContext)
  extends MandelbrotBson with MandelbrotMongoCollection {

  def collectionName = "mandelbrotImages"

  def fetchById(id: String): Future[Option[MandelbrotImage]] = {
    collection
      .flatMap(_
        .find(document("id" -> id), projection = None)
        .cursor[MandelbrotImage]()
        .headOption)
  }

  def save(image: MandelbrotImage): Future[Unit] = {
    collection
      .flatMap(_.insert(ordered = false).one(image))
      .map(_ => Unit)
  }
}
