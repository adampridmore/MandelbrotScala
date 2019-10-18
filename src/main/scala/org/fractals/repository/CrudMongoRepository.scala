package org.fractals.repository

import reactivemongo.bson.{BSONDocumentReader, BSONDocumentWriter, document}

import scala.concurrent.{ExecutionContext, Future}

trait CrudMongoRepository[T] extends CrudMongoCollection {
  implicit val ec: ExecutionContext

  implicit def imageWriter: BSONDocumentWriter[T]

  implicit def imageReader: BSONDocumentReader[T]

  def collectionName() = "mandelbrotImages2"

  def fetchById(id: String): Future[Option[T]] = {
    val query = document("id" -> id)

    collection
      .find(query, projection = None)
      .one
  }

  def save(document: T): Future[Unit] = {
    collection
      .insert(ordered = false)
      .one(document)
      .map(_=>Unit)
  }
}
