package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.veupathdb.vdi.lib.common.field.DataType

@JsonDeserialize(`as` = VDIDatasetTypeImpl::class)
interface VDIDatasetType {
  @get:JsonGetter(JsonKey.Name)
  val name: DataType

  @get:JsonGetter(JsonKey.Version)
  val version: String

  object JsonKey {
    const val Name    = "name"
    const val Version = "version"
  }
}

fun VDIDatasetType(name: DataType, version: String): VDIDatasetType =
  VDIDatasetTypeImpl(name, version)


private data class VDIDatasetTypeImpl @JsonCreator constructor(
  @JsonProperty(VDIDatasetType.JsonKey.Name)
  override val name: DataType,

  @JsonProperty(VDIDatasetType.JsonKey.Version)
  override val version: String,
) : VDIDatasetType {
  init {
    if (version.isBlank())
      throw IllegalArgumentException("version must not be blank")
  }
}
