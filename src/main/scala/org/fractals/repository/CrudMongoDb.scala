package org.fractals.repository

import reactivemongo.api.{DefaultDB, MongoConnection, MongoDriver}

import scala.concurrent.{ExecutionContext, Future}

trait CrudMongoDb extends MongoUri {

  implicit val ec: ExecutionContext

  private val driver: MongoDriver = MongoDriver()

  private def connection: MongoConnection = driver.connection(mongoUri).get

  // TODO: naked gets x 2
  private val databaseName: String = MongoConnection.parseURI(mongoUri).get.db.get

  val db: Future[DefaultDB] = connection.database(databaseName)
}