package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonProperty

data class VDIDatasetTypeImpl(
  @JsonProperty(VDIDatasetType.JsonKey.Name)
  override var name: String,

  @JsonProperty(VDIDatasetType.JsonKey.Version)
  override var version: String,
) : VDIDatasetType