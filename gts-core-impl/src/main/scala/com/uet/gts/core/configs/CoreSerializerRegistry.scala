package com.uet.gts.core.configs

import com.lightbend.lagom.scaladsl.playjson.{ JsonSerializer, JsonSerializerRegistry }
import com.uet.gts.core.dtos.TeacherDTO

object CoreSerializerRegistry extends JsonSerializerRegistry {
  override def serializers: Seq[JsonSerializer[_]] = Seq(
    JsonSerializer[TeacherDTO]
  )
}
