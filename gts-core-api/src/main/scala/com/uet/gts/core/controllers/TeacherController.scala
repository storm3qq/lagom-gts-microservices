package com.gts.core.controllers

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}

trait TeacherController extends Service {
  def hello(): ServiceCall[NotUsed, String]

  override final def descriptor: Descriptor = {
    import Service._

    named("gts-core-api")
      .withCalls(
        pathCall("/api/hello", hello _)
      )
      .withAutoAcl(true)
  }
}
