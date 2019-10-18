package org.fractals.repository

import reactivemongo.api.{DefaultDB, MongoConnection, MongoDriver}

import scala.concurrent.{ExecutionContext, Future}

trait MandelbrotMongo extends MongoUri {

  implicit def ec: ExecutionContext

  val driver: MongoDriver = MongoDriver()


  def connection: MongoConnection = driver.connection(mongoUri).get

  // TODO: naked gets x 2
  val databaseName: String = MongoConnection.parseURI(mongoUri).get.db.get

  val db: Future[DefaultDB] = connection.database(databaseName)
}

trait MongoUri {
  def mongoUri : String
}
