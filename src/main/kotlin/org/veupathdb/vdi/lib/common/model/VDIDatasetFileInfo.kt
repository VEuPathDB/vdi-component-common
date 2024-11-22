package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonDeserialize(`as` = VDIDatasetFileInfoImpl::class)
interface VDIDatasetFileInfo {
  @get:JsonGetter(JsonKey.FileName)
  val filename: String

  @get:JsonGetter(JsonKey.FileSize)
  val size: ULong

  object JsonKey {
    const val FileName = "filename"
    const val FileSize = "size"
  }
}

fun VDIDatasetFileInfo(filename: String, size: ULong): VDIDatasetFileInfo =
  VDIDatasetFileInfoImpl(filename, size)

private data class VDIDatasetFileInfoImpl @JsonCreator constructor(
  @JsonProperty(VDIDatasetFileInfo.JsonKey.FileName)
  override val filename: String,

  @JsonProperty(VDIDatasetFileInfo.JsonKey.FileSize)
  override val size: ULong,
) : VDIDatasetFileInfo {
  init {
    if (filename.isBlank())
      throw IllegalArgumentException("filename must not be blank")
  }
}
