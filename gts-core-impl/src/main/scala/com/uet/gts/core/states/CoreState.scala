package com.uet.gts.core.states

import akka.cluster.sharding.typed.scaladsl.EntityTypeKey

object CoreState {
  val typeKey = EntityTypeKey[CoreCommand]("CoreAggregate")
}

