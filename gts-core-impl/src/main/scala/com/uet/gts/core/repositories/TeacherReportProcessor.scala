package com.uet.gts.core.repositories

import akka.Done
import com.lightbend.lagom.scaladsl.persistence.{ AggregateEventTag, ReadSideProcessor, WriteSidePersistenceComponents }
import com.lightbend.lagom.scaladsl.persistence.slick.SlickReadSide
import com.uet.gts.core.models.events.TeacherEvent
import com.uet.gts.core.models.events.TeacherEvent.{ Event, GetAllTeachers }
import slick.dbio.DBIOAction

class TeacherReportProcessor (readSide: SlickReadSide, repository: TeacherRepository)
  extends ReadSideProcessor[Event] {

  override def buildHandler(): ReadSideProcessor.ReadSideHandler[Event] =
    readSide
      .builder[Event]("teacher-repository")
      .setEventHandler[GetAllTeachers] { envelope =>
        DBIOAction.successful(Done) // not used in report
      }
      .build()

  override def aggregateTags: Set[AggregateEventTag[Event]] = TeacherEvent.tag.allTags
}
