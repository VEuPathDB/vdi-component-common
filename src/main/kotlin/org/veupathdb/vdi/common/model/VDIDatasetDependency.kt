package org.veupathdb.vdi.common.model

import com.fasterxml.jackson.annotation.JsonProperty

data class VDIDatasetDependency(
  @JsonProperty(JsonKey.Identifier)
  var identifier: String,

  @JsonProperty(JsonKey.Version)
  var version: String,

  @JsonProperty(JsonKey.DisplayName)
  var displayName: String,
) {
  object JsonKey {
    const val Identifier  = "resourceIdentifier"
    const val Version     = "resourceVersion"
    const val DisplayName = "resourceDisplayName"
  }
}
