package com.uet.gts.core

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.{ Descriptor, Service, ServiceAcl, ServiceCall }
import com.uet.gts.core.dtos.TeacherDTO

trait CoreService extends Service {
  def hello(): ServiceCall[NotUsed, TeacherDTO]

  def testCommand(id: Int): ServiceCall[NotUsed, String]

  override final def descriptor: Descriptor = {
    import Service._

    named("gts-core-api")
      .withCalls(
        pathCall("/api/hello", hello _),
        pathCall("/api/test?id", testCommand _)
      )
      .withAcls(ServiceAcl(pathRegex = Some("/api/hello")))
     //.withAutoAcl(true) -> no filter
  }
}
