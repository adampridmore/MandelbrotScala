package org.fractals.repository

import reactivemongo.api.collections.bson.BSONCollection

import scala.concurrent.{ExecutionContext, Future}

trait MandelbrotMongoCollection extends MandelbrotMongo {
  def collection(implicit ec: ExecutionContext): Future[BSONCollection] = db.map(_.collection(collectionName()))
  def collectionName(): String
}
