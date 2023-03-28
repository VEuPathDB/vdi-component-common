package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.veupathdb.vdi.common.field.ProjectID

data class VDIDatasetMeta(
  @JsonProperty(JsonKey.Type)
  var type: VDIDatasetType,

  @JsonProperty(JsonKey.Projects)
  var projects: Set<ProjectID>,

  @JsonProperty(JsonKey.Owner)
  var owner: String,

  @JsonProperty(JsonKey.Name)
  var name: String,

  @JsonProperty(JsonKey.Summary)
  var summary: String?,

  @JsonProperty(JsonKey.Description)
  var description: String?,

  @JsonProperty(JsonKey.Dependencies)
  var dependencies: Collection<VDIDatasetDependency>,
) {
  object JsonKey {
    const val Dependencies = "dependencies"
    const val Description  = "description"
    const val Name         = "name"
    const val Owner        = "owner"
    const val Projects     = "projects"
    const val Summary      = "summary"
    const val Type         = "type"
  }
}
