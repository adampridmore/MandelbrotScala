package org.fractals.repository

import org.fractals.domain.MandelbrotImage

import reactivemongo.bson.document

import scala.concurrent.{ExecutionContext, Future}

abstract class MandelbrotRepository(implicit val ec: ExecutionContext)
  extends MandelbrotBson with MandelbrotMongoCollection {

  def collectionName() = "mandelbrotImages"

  def fetchById(id: String): Future[Option[MandelbrotImage]] = {
    val query = document("id" -> id)

    for {
      coll <- collection
      image <- coll.find(query, projection = None).one
    } yield image
  }

  def save(image: MandelbrotImage): Future[Unit] = {
    for {
      coll <- collection
      _ <- coll.insert(ordered = false).one(image)
    } yield Unit
  }
}
