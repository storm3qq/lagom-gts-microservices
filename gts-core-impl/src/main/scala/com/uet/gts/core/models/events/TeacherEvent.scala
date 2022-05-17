package com.uet.gts.core.models.events

import com.lightbend.lagom.scaladsl.persistence.{ AggregateEvent, AggregateEventShards, AggregateEventTag, AggregateEventTagger }

object TeacherEvent {
  val tag: AggregateEventShards[Event] = AggregateEventTag.sharded[Event](numShards = 10)

  sealed trait Event extends AggregateEvent[Event] {
    override def aggregateTag: AggregateEventTagger[Event] = tag
  }

  final case class GetAllTeachers() extends Event
}