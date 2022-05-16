package com.uet.gts.core.controllers

import akka.NotUsed
import akka.cluster.sharding.typed.scaladsl.ClusterSharding
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.uet.gts.core.CoreService
import com.uet.gts.core.dtos.TeacherDTO

import scala.concurrent.{ Future, ExecutionContext => EC }

class CoreServiceImpl(clusterSharding: ClusterSharding)(implicit ec: EC) extends CoreService {

  override def hello(): ServiceCall[NotUsed, TeacherDTO] = ServiceCall { _ =>
    Future.successful {
      new TeacherDTO("John Snow", 29)
    }
  }
}
