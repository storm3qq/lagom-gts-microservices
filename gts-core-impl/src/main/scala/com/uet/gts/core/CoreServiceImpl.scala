package com.uet.gts.core

import akka.NotUsed
import akka.cluster.sharding.typed.scaladsl.{ ClusterSharding, EntityRef }
import akka.util.Timeout
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.persistence.PersistentEntityRegistry
import com.uet.gts.core.dtos.TeacherDTO
import com.uet.gts.core.models.commands.CoreCommand
import com.uet.gts.core.models.commands.CoreCommand.GetAllTeachersCommand

import java.util.UUID
import scala.concurrent.duration.DurationInt
import scala.concurrent.{ Future, ExecutionContext => EC }

class CoreServiceImpl(clusterSharding: ClusterSharding)(implicit ec: EC)
  extends CoreService {

  private def entityRef(id: String): EntityRef[CoreCommand] =
    clusterSharding.entityRefFor(CoreCommand.typeKey, id)

  implicit val timeout = Timeout(5.seconds)

  override def hello(): ServiceCall[NotUsed, TeacherDTO] = ServiceCall { _ =>
    Future.successful {
      new TeacherDTO("John Snow", 29)
    }
  }

  override def testCommand(id: Int): ServiceCall[NotUsed, String] = ServiceCall { _ =>
    entityRef(id.toString)
      .ask(reply => GetAllTeachersCommand(id, reply))
      .map(result => result)
  }
}
