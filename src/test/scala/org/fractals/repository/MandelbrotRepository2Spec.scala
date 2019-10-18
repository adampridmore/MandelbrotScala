package org.fractals.repository

import org.fractals.domain.MandelbrotImage
import org.scalatest.{Matchers, WordSpec}

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.ExecutionContext.Implicits.global

class MandelbrotRepository2Spec extends WordSpec with Matchers {

  trait MongoUriForTesting extends MongoUri {
    override def mongoUri: String = "mongodb://localhost:27017/mandelbrot-test"
  }

  class MandelbrotImageRepository2(implicit val ec: ExecutionContext)
    extends CrudRepository[MandelbrotImage] with MongoUriForTesting with MandelbrotBson

  val repository: CrudRepository[MandelbrotImage] = new MandelbrotImageRepository2

  def await[T](future: Future[T]): T = {
    import scala.concurrent.duration._

    Await.result(future, 5 seconds)
  }

  "A repository2" can {
    "save" in {
      repository.save(MandelbrotImage(name = "Image1"))
    }

    "fetch by id" in {
      val image = MandelbrotImage(name = "Image2")

      await(repository.save(image))

      val foundImage = await(repository.fetchById(image.id))

      foundImage.get shouldBe image
    }
  }
}
