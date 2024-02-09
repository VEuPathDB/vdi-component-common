package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonProperty

data class VDIDatasetFileInfoImpl(
  @JsonProperty(VDIDatasetFileInfo.JsonKey.FileName)
  override var filename: String,

  @JsonProperty(VDIDatasetFileInfo.JsonKey.FileSize)
  override var fileSize: Long,
) : VDIDatasetFileInfo