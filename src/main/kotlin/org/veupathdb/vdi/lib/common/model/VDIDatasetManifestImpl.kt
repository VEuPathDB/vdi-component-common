package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonProperty

data class VDIDatasetManifestImpl(
  @JsonProperty(VDIDatasetManifest.JsonKey.InputFiles)
  override var inputFiles: Collection<VDIDatasetFileInfo>,

  @JsonProperty(VDIDatasetManifest.JsonKey.DataFiles)
  override var dataFiles: Collection<VDIDatasetFileInfo>
) : VDIDatasetManifest
