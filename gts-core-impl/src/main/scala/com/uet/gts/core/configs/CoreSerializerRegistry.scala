package com.uet.gts.core.configs

import com.lightbend.lagom.scaladsl.playjson.{ JsonSerializer, JsonSerializerRegistry }
import com.uet.gts.core.dtos.TeacherDTO
import com.uet.gts.core.models.CoreBehavior.CoreState
import com.uet.gts.core.models.events.CoreEvent

object CoreSerializerRegistry extends JsonSerializerRegistry {
  override def serializers: Seq[JsonSerializer[_]] = Seq(
    JsonSerializer[TeacherDTO],
    JsonSerializer[CoreState],
    JsonSerializer[CoreEvent.AllTeachersGotEvent]
  )
}
