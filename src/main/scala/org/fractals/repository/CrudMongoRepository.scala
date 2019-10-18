package org.fractals.repository

import reactivemongo.bson.{BSONDocumentReader, BSONDocumentWriter, document}

import scala.concurrent.{ExecutionContext, Future}

trait Read[T] extends CrudMongoCollection[T] {
  implicit def imageReader: BSONDocumentReader[T]

  def fetchById(id: String): Future[Option[T]] = {
    val query = document("id" -> id)

    collection
      .find(query, projection = None)
      .one
  }
}

trait Write[T] extends CrudMongoCollection[T]{
  implicit def imageWriter: BSONDocumentWriter[T]

  def save(document: T): Future[Unit] = {
    collection
      .insert(ordered = false)
      .one(document)
      .map(_=>Unit)
  }
}
//
//trait CrudMongoRepository[T] extends Read[T] with Write[T] {
//
//}
