package org.fractals.repository

import java.util.{Date, UUID}

import org.fractals.repository.Domain.{Id, newId}
import org.scalatest.{Matchers, WordSpec}
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.api.commands.WriteResult
import reactivemongo.api.{Collection, Cursor, DefaultDB, MongoConnection, MongoDriver}
import reactivemongo.bson.{BSONDocumentWriter, Macros}

import scala.concurrent.{Await, Future}
import scala.language.postfixOps
import scala.util.Try
import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.bson.{BSONDocumentReader, BSONDocumentWriter, Macros, document}


object Domain {
  type Id = String

  def newId(): Id = UUID.randomUUID().toString
}

case class MandelbrotImage(id: Id = newId(), name: String, createdTimestamp: Date = new Date)

trait MandelbrotBson {
  implicit def imageWriter: BSONDocumentWriter[MandelbrotImage] =
    Macros.writer[MandelbrotImage]

  implicit def imageReader: BSONDocumentReader[MandelbrotImage] =
    Macros.reader[MandelbrotImage]
}

trait Collection {

}

class MandelbrotRepository extends MandelbrotBson {

  import ExecutionContext.Implicits.global

  val driver: MongoDriver = MongoDriver()

  val mongoUri = "mongodb://localhost:27017/mandelbrot-test"

  // TODO: naked gets x 2
  val databaseName: String = MongoConnection.parseURI(mongoUri).get.db.get

  val collectionName = "mandelbrotImages"

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
      .map(_=> Unit)
  }

  def collection: Future[BSONCollection] = collectionByName(collectionName)

  def collectionByName(name: String) : Future[BSONCollection] = {
    val connection: MongoConnection = driver.connection(mongoUri).get

    for {
      db <- connection.database(databaseName)
      coll: BSONCollection = db.collection(name)
    } yield coll
  }
}

class MandelbrotRepositorySpec extends WordSpec with Matchers {
  def await[T](future: Future[T]): T = {
    import scala.concurrent.duration._

    Await.result(future, 5 seconds)
  }

  "A repository" can {
    "save" in {
      val repository = new MandelbrotRepository()
      repository.save(MandelbrotImage(name = "Image1"))
    }

    "fetch by id" in {
      val repository = new MandelbrotRepository()
      val image = MandelbrotImage(name = "Image2")

      await(repository.save(image))

      val foundImage = await(repository.fetchById(image.id))

      foundImage.get shouldBe image
    }
  }
}
