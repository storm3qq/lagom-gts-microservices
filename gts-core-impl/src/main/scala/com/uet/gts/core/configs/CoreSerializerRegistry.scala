package com.uet.gts.core.configs

import com.lightbend.lagom.scaladsl.playjson.{ JsonSerializer, JsonSerializerRegistry }

object CoreSerializerRegistry extends JsonSerializerRegistry {
  override def serializers: Seq[JsonSerializer[_]] = Seq.empty
}
