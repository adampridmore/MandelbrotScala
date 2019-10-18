package org.fractals.repository

import org.fractals.domain.MandelbrotImage
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.{BSONDocumentReader, BSONDocumentWriter, Macros, document}

import scala.concurrent.{ExecutionContext, Future}

trait CrudMongoCollection extends MandelbrotMongo {
  def collection(implicit ec: ExecutionContext): Future[BSONCollection] =
    db.map(_.collection(collectionName()))

  def collectionName(): String
}

// TODO - MandelbrotBson
trait CrudRepository[T] extends CrudMongoCollection  {
  implicit val ec: ExecutionContext
  implicit def imageWriter: BSONDocumentWriter[T]
  implicit def imageReader: BSONDocumentReader[T]

  def collectionName() = "mandelbrotImages2"

  def fetchById(id: String): Future[Option[T]] = {
    val query = document("id" -> id)

    for {
      coll <- collection
      image <- coll.find(query, projection = None).one
    } yield image
  }

  def save(document: T): Future[Unit] = {
    for {
      coll <- collection
      _ <- coll.insert(ordered = false).one(document)
    } yield Unit
  }
}

trait MandelbrotRepository2 extends CrudRepository[MandelbrotImage]  {

}

