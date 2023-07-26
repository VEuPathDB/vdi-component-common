package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.veupathdb.vdi.lib.common.field.ProjectID
import org.veupathdb.vdi.lib.common.field.UserID

data class VDIDatasetMetaImpl(
  @JsonProperty(VDIDatasetMeta.JsonKey.Type)
  override var type: VDIDatasetType,

  @JsonProperty(VDIDatasetMeta.JsonKey.Projects)
  override var projects: Set<ProjectID>,

  @JsonProperty(VDIDatasetMeta.JsonKey.Visibility)
  override var visibility: VDIDatasetVisibility,

  @JsonProperty(VDIDatasetMeta.JsonKey.Owner)
  override var owner: UserID,

  @JsonProperty(VDIDatasetMeta.JsonKey.Name)
  override var name: String,

  @JsonProperty(VDIDatasetMeta.JsonKey.Summary)
  override var summary: String?,

  @JsonProperty(VDIDatasetMeta.JsonKey.Description)
  override var description: String?,

  @JsonProperty(VDIDatasetMeta.JsonKey.Origin)
  override var origin: String,

  @JsonProperty(VDIDatasetMeta.JsonKey.Dependencies)
  override var dependencies: Collection<VDIDatasetDependency>,
) : VDIDatasetMeta