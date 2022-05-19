package com.uet.gts.core.models.events

import com.lightbend.lagom.scaladsl.persistence.{ AggregateEvent, AggregateEventShards, AggregateEventTag, AggregateEventTagger }
import play.api.libs.json.Json

object CoreEvent {
  val tag: AggregateEventShards[Event] = AggregateEventTag.sharded[Event](numShards = 10)

  sealed trait Event extends AggregateEvent[Event] {
    override def aggregateTag: AggregateEventTagger[Event] = tag
  }

  final case class AllTeachersGotEvent(v: Int = 1) extends Event
  final case class AllStudentsGotEvent(v: Int = 1) extends Event

  object AllTeachersGotEvent {
    implicit val formatter = Json.format[AllTeachersGotEvent]
  }
}