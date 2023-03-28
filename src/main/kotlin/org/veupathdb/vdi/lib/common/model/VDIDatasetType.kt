package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonProperty

data class VDIDatasetType(
  @JsonProperty(JsonKey.Name)
  var name: String,

  @JsonProperty(JsonKey.Version)
  var version: String,
) {
  object JsonKey {
    const val Name    = "name"
    const val Version = "version"
  }
}