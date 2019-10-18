package org.fractals.repository

import reactivemongo.api.collections.bson.BSONCollection

import scala.concurrent.{ExecutionContext, Future}

trait CrudMongoCollection extends CrudMongoDb {
  def collection: Future[BSONCollection] =
    db.map(_.collection(collectionName()))

  def collectionName(): String
}
