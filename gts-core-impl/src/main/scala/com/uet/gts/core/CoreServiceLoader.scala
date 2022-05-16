package com.uet.gts.core

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.cluster.ClusterComponents
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.playjson.JsonSerializerRegistry
import com.lightbend.lagom.scaladsl.server.{ LagomApplication, LagomApplicationContext, LagomApplicationLoader, LagomServer }
import com.softwaremill.macwire.wire
import com.uet.gts.core.CoreServiceLoader.CoreServiceApplication
import com.uet.gts.core.configs.CoreSerializerRegistry
import com.uet.gts.core.controllers.CoreServiceImpl
import play.api.libs.ws.ahc.AhcWSComponents

class CoreServiceLoader extends LagomApplicationLoader {
  override def load(context: LagomApplicationContext): LagomApplication =
    new CoreServiceApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new CoreServiceApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[CoreService])
}

object CoreServiceLoader {
  abstract class CoreServiceApplication(context: LagomApplicationContext)
    extends LagomApplication(context) with AhcWSComponents with ClusterComponents {

    override def lagomServer: LagomServer = serverFor[CoreService](wire[CoreServiceImpl])

    override def jsonSerializerRegistry: JsonSerializerRegistry = CoreSerializerRegistry
  }

}
