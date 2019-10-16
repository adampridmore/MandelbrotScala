package org.fractals.domain

import java.util.UUID

object Domain {
  type Id = String

  def newId(): Id = UUID.randomUUID().toString
}
