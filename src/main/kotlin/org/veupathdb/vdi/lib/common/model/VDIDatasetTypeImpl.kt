package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.veupathdb.vdi.lib.common.field.DataType

data class VDIDatasetTypeImpl @JsonCreator constructor(
  @JsonProperty(VDIDatasetType.JsonKey.Name)
  override var name: DataType,

  @JsonProperty(VDIDatasetType.JsonKey.Version)
  override var version: String,
) : VDIDatasetType
