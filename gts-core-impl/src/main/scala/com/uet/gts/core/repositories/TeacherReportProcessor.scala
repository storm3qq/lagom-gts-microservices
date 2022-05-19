package com.uet.gts.core.repositories

import akka.persistence.query.{ NoOffset, Offset }
import akka.{ Done, NotUsed }
import akka.stream.scaladsl.Flow
import com.lightbend.lagom.scaladsl.persistence.ReadSideProcessor.ReadSideHandler
import com.lightbend.lagom.scaladsl.persistence.{ AggregateEventTag, EventStreamElement, ReadSideProcessor }
import com.lightbend.lagom.scaladsl.persistence.slick.SlickReadSide
import com.uet.gts.core.models.events.CoreEvent
import com.uet.gts.core.models.events.CoreEvent.{ AllTeachersGotEvent, Event }

import scala.concurrent.Future

class TeacherReportProcessor (readSide: SlickReadSide)
  extends ReadSideProcessor[Event] {

  override def buildHandler(): ReadSideProcessor.ReadSideHandler[Event] = {
    new ReadSideHandler[Event] {

      override def prepare(tag: AggregateEventTag[Event]): Future[Offset] =
        Future.successful(Offset.sequence(6))

      override def handle(): Flow[EventStreamElement[Event], Done, NotUsed] =
        Flow[EventStreamElement[Event]]
          .mapAsync(1) { eventElement =>
            eventElement.event match {
              case _: AllTeachersGotEvent =>
                println("======[ TeacherReportProcessor: AllTeachersGotEvent ] ==============")
                Future.successful(Done)

              case _: Event =>
                println("======[ TeacherReportProcessor: Event ] ==============")
                Future.successful(Done)
            }
          }
    }
  }
//    readSide
//      .builder[Event]("teacher-repository")
//      .setEventHandler[AllTeachersGotEvent] { envelope =>
//        println("======[ TeacherReportProcessor ] ==============")
//        DBIOAction.successful(Done) // not used in report
//      }
//      .build()

  override def aggregateTags: Set[AggregateEventTag[Event]] = CoreEvent.tag.allTags
}
