package org.fractals.repository

import reactivemongo.bson.{BSONDocumentReader, BSONDocumentWriter, document}

import scala.concurrent.{ExecutionContext, Future}

trait CrudMongoRepository[T] extends CrudMongoCollection  {
  implicit val ec: ExecutionContext
  implicit def imageWriter: BSONDocumentWriter[T]
  implicit def imageReader: BSONDocumentReader[T]

  def collectionName() = "mandelbrotImages2"

  def fetchById(id: String): Future[Option[T]] = {
    val query = document("id" -> id)

    for {
      coll <- collection
      doc <- coll.find(query, projection = None).one
    } yield doc
  }

  def save(document: T): Future[Unit] = {
    for {
      coll <- collection
      _ <- coll.insert(ordered = false).one(document)
    } yield Unit
  }
}
