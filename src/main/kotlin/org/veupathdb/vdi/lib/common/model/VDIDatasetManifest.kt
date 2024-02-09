package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonDeserialize(`as` = VDIDatasetManifestImpl::class)
interface VDIDatasetManifest {
  @get:JsonGetter(JsonKey.InputFiles)
  @set:JsonSetter(JsonKey.InputFiles)
  var inputFiles: Collection<VDIDatasetFileInfo>

  @get:JsonGetter(JsonKey.DataFiles)
  @set:JsonSetter(JsonKey.DataFiles)
  var dataFiles: Collection<VDIDatasetFileInfo>

  object JsonKey {
    const val InputFiles = "inputFiles"
    const val DataFiles  = "dataFiles"
  }
}

