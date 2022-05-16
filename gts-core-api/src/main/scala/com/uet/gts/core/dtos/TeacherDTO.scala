package com.uet.gts.core.dtos

import play.api.libs.json.Json

final case class TeacherDTO(name: String, age: Int)

object TeacherDTO {
  implicit val teacherFormatter = Json.format[TeacherDTO]
}
