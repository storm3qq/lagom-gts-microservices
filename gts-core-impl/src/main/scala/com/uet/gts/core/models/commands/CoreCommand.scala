package com.uet.gts.core.models.commands

import akka.actor.typed.ActorRef
import akka.cluster.sharding.typed.scaladsl.EntityTypeKey

trait CoreCommandSerializable

sealed trait CoreCommand extends CoreCommandSerializable

object CoreCommand {
  val typeKey = EntityTypeKey[CoreCommand]("CoreAggregate")

  final case class GetAllTeachersCommand(v: Int, replyTo: ActorRef[String]) extends CoreCommand
}
