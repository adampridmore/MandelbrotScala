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
}

class MandelbrotRepository extends MandelbrotBson {

  import ExecutionContext.Implicits.global // use any appropriate context


  val mongoUri = "mongodb://localhost:27017/mandelbrot-test"

  def fetchById(id: String): Future[MandelbrotImage] = {
    Future.successful(MandelbrotImage(name = "foo"))
  }

  def save(image: MandelbrotImage): Future[Unit] = {


    // Connect to the database: Must be done only once per application
    val driver: MongoDriver = MongoDriver()

    // TODO: naked gets x 2
    val databaseName = MongoConnection.parseURI(mongoUri).get.db.get

    driver.connection(mongoUri)
      .map((mongoConnection: MongoConnection) => mongoConnection.database(databaseName))
      .map((fDb: Future[DefaultDB]) =>
        fDb.flatMap(db =>{
          val collection: BSONCollection =  db.collection("mandelbrotImages")
          val x: Future[WriteResult] = collection.insert(ordered = false).one(image)
          x
        })).get.map(_ => Unit)

//    Future.successful()
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

      await (repository.save(image))

      val foundImage = await(repository.fetchById(image.id))

      foundImage shouldBe image
    }
  }
}
