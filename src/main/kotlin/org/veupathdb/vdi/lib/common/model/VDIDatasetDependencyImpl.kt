package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonProperty

data class VDIDatasetDependencyImpl(
  @JsonProperty(VDIDatasetDependency.JsonKey.Identifier)
  override var identifier: String,

  @JsonProperty(VDIDatasetDependency.JsonKey.Version)
  override var version: String,

  @JsonProperty(VDIDatasetDependency.JsonKey.DisplayName)
  override var displayName: String,
) : VDIDatasetDependency