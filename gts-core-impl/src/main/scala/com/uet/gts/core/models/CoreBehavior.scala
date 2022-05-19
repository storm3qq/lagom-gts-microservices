package com.uet.gts.core.models

import akka.actor.typed.Behavior
import akka.cluster.sharding.typed.scaladsl.EntityContext
import akka.persistence.typed.PersistenceId
import akka.persistence.typed.scaladsl.{ Effect, EventSourcedBehavior, ReplyEffect, RetentionCriteria }
import com.lightbend.lagom.scaladsl.persistence.AkkaTaggerAdapter
import com.uet.gts.core.models.commands.CoreCommand
import com.uet.gts.core.models.commands.CoreCommand.GetAllTeachersCommand
import com.uet.gts.core.models.events.CoreEvent
import com.uet.gts.core.models.events.CoreEvent.AllTeachersGotEvent
import play.api.libs.json.{ Format, Json }

object CoreBehavior {
  def apply(entityContext: EntityContext[CoreCommand]): Behavior[CoreCommand] =
    apply(PersistenceId(entityContext.entityTypeKey.name, entityContext.entityId))
      .withTagger(AkkaTaggerAdapter.fromLagom(entityContext, CoreEvent.tag))
      .withRetention(RetentionCriteria.snapshotEvery(numberOfEvents = 100, keepNSnapshots = 2))
      .snapshotWhen((x, y, z) => y.isInstanceOf[AllTeachersGotEvent])

  def apply(persistenceId: PersistenceId): EventSourcedBehavior[CoreCommand, CoreEvent.Event, CoreState] = {
    EventSourcedBehavior
      .withEnforcedReplies[CoreCommand, CoreEvent.Event, CoreState](
        persistenceId = persistenceId,
        emptyState = CoreState(),
        commandHandler = (cart, cmd) => cart.applyCommand(cmd),
        eventHandler = (cart, evt) => cart.applyEvent(evt)
      )
  }

  final case class CoreState(v: Int = 0) {
    def applyCommand(cmd: CoreCommand): ReplyEffect[CoreEvent.Event, CoreState] = {
      cmd match {
        case x: GetAllTeachersCommand =>
          if (x.v > 10)
            Effect.reply(x.replyTo)("Hello_World")
          else
            Effect
              .persist(AllTeachersGotEvent())
              .thenReply(x.replyTo) { _ =>
                 "Hello_World_v2"
              }

      }
    }

    def applyEvent(evt: CoreEvent.Event): CoreState = {
      evt match {
        case _: AllTeachersGotEvent =>
          println("======[ AllTeachersGotEvent ] ========")
          CoreState(10)

        case _ => CoreState(1)
      }
    }
  }

  object CoreState {
    implicit val format: Format[CoreState] = Json.format[CoreState]
  }
}
