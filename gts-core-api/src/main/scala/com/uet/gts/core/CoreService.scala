package com.uet.gts.core

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.{ Descriptor, Service, ServiceCall }
import com.uet.gts.core.dtos.TeacherDTO

trait CoreService extends Service {
  def hello(): ServiceCall[NotUsed, TeacherDTO]

  override final def descriptor: Descriptor = {
    import Service._

    named("gts-core-api")
      .withCalls(
        pathCall("/api/hello", hello _)
      )
      .withAutoAcl(true)
  }
}
