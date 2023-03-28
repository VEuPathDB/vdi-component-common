package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonProperty

data class VDIDatasetManifest(
  @JsonProperty(JsonKey.InputFiles)
  var inputFiles: Collection<String>,

  @JsonProperty(JsonKey.DataFiles)
  var dataFiles: Collection<String>
) {
  object JsonKey {
    const val InputFiles = "inputFiles"
    const val DataFiles  = "dataFiles"
  }
}
