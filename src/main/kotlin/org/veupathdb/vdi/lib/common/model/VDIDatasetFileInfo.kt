package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonDeserialize(`as` = VDIDatasetFileInfoImpl::class)
interface VDIDatasetFileInfo {
  @get:JsonGetter(JsonKey.FileName)
  @set:JsonSetter(JsonKey.FileName)
  var filename: String

  @get:JsonGetter(JsonKey.FileSize)
  @set:JsonSetter(JsonKey.FileSize)
  var fileSize: Long

  object JsonKey {
    const val FileName = "filename"
    const val FileSize = "fileSize"
  }
}