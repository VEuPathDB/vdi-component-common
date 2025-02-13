package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.node.ObjectNode

@JsonDeserialize(using = FileInfoAdapter::class)
interface VDIDatasetFileInfo {
  @get:JsonGetter(JsonKey.FileName)
  val filename: String

  @get:JsonGetter(JsonKey.FileSize)
  val size: ULong

  object JsonKey {
    const val FileName = "filename"
    const val FileSize = "fileSize"
  }
}


@Suppress("unused")
fun VDIDatasetFileInfo(filename: String, size: ULong): VDIDatasetFileInfo =
  VDIDatasetFileInfoImpl(filename, size)


private data class VDIDatasetFileInfoImpl(
  override val filename: String,
  override val size: ULong,
) : VDIDatasetFileInfo {
  init {
    if (filename.isBlank())
      throw IllegalArgumentException("filename must not be blank")
  }
}

private object FileInfoAdapter : JsonDeserializer<VDIDatasetFileInfo>() {
  override fun deserialize(parser: JsonParser, ctx: DeserializationContext): VDIDatasetFileInfo {
    val raw = ctx.readValue(parser, ObjectNode::class.java)

    var name: String? = null
    var size: ULong? = null

    for ((key, value) in raw.fields()) {
      when (key) {
        VDIDatasetFileInfo.JsonKey.FileName -> name = value.textValue()
          ?: throw JsonParseException("expected $key to be a string but was ${value.nodeType} instead")
        VDIDatasetFileInfo.JsonKey.FileSize,
        "size" -> size = (value.takeIf { it.isNumber }
          ?.asText() ?: throw JsonParseException("expected $key to be a number but was ${value.nodeType} instead"))
          .toULong()
      }
    }

    if (name == null)
      throw JsonParseException("missing required field: ${VDIDatasetFileInfo.JsonKey.FileName}")
    if (size == null)
      throw JsonParseException("missing required field: ${VDIDatasetFileInfo.JsonKey.FileSize}")

    return VDIDatasetFileInfoImpl(name, size)
  }
}
