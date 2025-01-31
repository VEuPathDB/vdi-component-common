package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonDeserialize(`as` = VDIDatasetManifestImpl::class)
interface VDIDatasetManifest {
  @get:JsonGetter(JsonKey.InputFiles)
  val inputFiles: Collection<VDIDatasetFileInfo>

  @get:JsonGetter(JsonKey.DataFiles)
  val dataFiles: Collection<VDIDatasetFileInfo>

  object JsonKey {
    const val InputFiles = "inputFiles"
    const val DataFiles  = "dataFiles"
  }
}


@Suppress("unused")
fun VDIDatasetManifest(
  inputFiles: Collection<VDIDatasetFileInfo>,
  dataFiles: Collection<VDIDatasetFileInfo>,
): VDIDatasetManifest =
  VDIDatasetManifestImpl(inputFiles, dataFiles)


private data class VDIDatasetManifestImpl @JsonCreator constructor(
  @JsonProperty(VDIDatasetManifest.JsonKey.InputFiles)
  override val inputFiles: Collection<VDIDatasetFileInfo>,

  @JsonProperty(VDIDatasetManifest.JsonKey.DataFiles)
  override val dataFiles: Collection<VDIDatasetFileInfo>
) : VDIDatasetManifest {
  init {
    if (inputFiles.isEmpty())
      throw IllegalArgumentException("inputFiles must not be empty")
    if (dataFiles.isEmpty())
      throw IllegalArgumentException("dataFiles must not be empty")
  }
}
