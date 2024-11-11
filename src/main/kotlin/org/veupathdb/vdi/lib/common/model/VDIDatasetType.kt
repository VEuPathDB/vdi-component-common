package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.veupathdb.vdi.lib.common.field.DataType

@JsonDeserialize(`as` = VDIDatasetTypeImpl::class)
interface VDIDatasetType {
  @get:JsonGetter(JsonKey.Name)
  @set:JsonSetter(JsonKey.Name)
  var name: DataType

  @get:JsonGetter(JsonKey.Version)
  @set:JsonSetter(JsonKey.Version)
  var version: String

  object JsonKey {
    const val Name    = "name"
    const val Version = "version"
  }
}

