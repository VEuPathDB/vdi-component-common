package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

/**
 * Manifest of VDI dataset files.
 *
 * Files are tracked in 2 categories; 'input' files which are the files
 * originally uploaded to VDI, and 'data' files which are files that have been
 * processed by a VDI plugin import script, and are ready for installation into
 * a target system.
 *
 * On initial dataset upload, the [dataFiles] property will be empty, to be
 * filled by the target plugin on
 */
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
) : VDIDatasetManifest
