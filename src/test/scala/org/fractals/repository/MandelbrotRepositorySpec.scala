package org.fractals.repository

import org.fractals.domain.MandelbrotImage
import org.scalatest.{Matchers, WordSpec}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.language.postfixOps

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
