package org.fractals.domain

import java.util.Date

import org.fractals.domain.Domain.Id

case class MandelbrotImage(id: Id = Domain.newId(), name: String, createdTimestamp: Date = new Date)
