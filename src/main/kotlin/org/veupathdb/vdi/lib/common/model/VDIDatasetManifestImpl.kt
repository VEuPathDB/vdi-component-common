package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonProperty

data class VDIDatasetManifestImpl(
  @JsonProperty(VDIDatasetManifest.JsonKey.InputFiles)
  override var inputFiles: Collection<String>,

  @JsonProperty(VDIDatasetManifest.JsonKey.DataFiles)
  override var dataFiles: Collection<String>
) : VDIDatasetManifest