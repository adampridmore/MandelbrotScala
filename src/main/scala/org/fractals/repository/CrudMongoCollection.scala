package org.fractals.repository

import reactivemongo.api.collections.bson.BSONCollection
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

trait CrudMongoCollection extends CrudMongoDb {

  private def collectionF: Future[BSONCollection] =
    db.map(_.collection(collectionName))

  // TODO: Not sure about this. await?
  private lazy val collectionAwait: BSONCollection = {
    Await.result(db.map(_.collection(collectionName)), 10 seconds)
  }

  def collection = collectionAwait

  def collectionName: String
}
