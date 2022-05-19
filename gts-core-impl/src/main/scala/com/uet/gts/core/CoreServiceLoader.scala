package com.uet.gts.core

import akka.cluster.sharding.typed.scaladsl.Entity
import com.lightbend.lagom.scaladsl.akka.discovery.AkkaDiscoveryComponents
import com.lightbend.lagom.scaladsl.cluster.ClusterComponents
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.persistence.slick.{ ReadSideSlickPersistenceComponents, SlickPersistenceComponents, WriteSideSlickPersistenceComponents }
import com.lightbend.lagom.scaladsl.playjson.JsonSerializerRegistry
import com.lightbend.lagom.scaladsl.server.{ LagomApplication, LagomApplicationContext, LagomApplicationLoader, LagomServer, LagomServerComponents }
import com.softwaremill.macwire.wire
import com.uet.gts.core.CoreServiceLoader.CoreServiceApplication
import com.uet.gts.core.configs.CoreSerializerRegistry
import com.uet.gts.core.models.CoreBehavior
import com.uet.gts.core.models.commands.CoreCommand
import com.uet.gts.core.repositories.{ TeacherReportProcessor, TeacherRepository }
import play.api.db.HikariCPComponents
import play.api.libs.ws.ahc.AhcWSComponents

class CoreServiceLoader extends LagomApplicationLoader {
  override def load(context: LagomApplicationContext): LagomApplication =
    new CoreServiceApplication(context) with AkkaDiscoveryComponents

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new CoreServiceApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[CoreService])
}

object CoreServiceLoader {
  abstract class CoreServiceApplication(context: LagomApplicationContext)
    extends LagomApplication(context) with AhcWSComponents with ClusterComponents with LagomServerComponents
      with SlickPersistenceComponents with HikariCPComponents {

    override def lagomServer: LagomServer = serverFor[CoreService](wire[CoreServiceImpl])

    override def jsonSerializerRegistry: JsonSerializerRegistry = CoreSerializerRegistry

//    lazy val teacherRepository: TeacherRepository = wire[TeacherRepository]

    readSide.register(wire[TeacherReportProcessor])

    clusterSharding.init(
      Entity(CoreCommand.typeKey)(
        entityContext => CoreBehavior(entityContext)
      )
    )
  }
}
