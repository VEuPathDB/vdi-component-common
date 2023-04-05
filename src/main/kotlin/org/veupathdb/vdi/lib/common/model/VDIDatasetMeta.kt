package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.veupathdb.vdi.lib.common.field.ProjectID
import org.veupathdb.vdi.lib.common.field.UserID

@JsonDeserialize(`as` = VDIDatasetMetaImpl::class)
interface VDIDatasetMeta {
  @get:JsonGetter(JsonKey.Type)
  @set:JsonSetter(JsonKey.Type)
  var type: VDIDatasetType

  @get:JsonGetter(JsonKey.Projects)
  @set:JsonSetter(JsonKey.Projects)
  var projects: Set<ProjectID>

  @get:JsonGetter(JsonKey.Owner)
  @set:JsonSetter(JsonKey.Owner)
  var owner: UserID

  @get:JsonGetter(JsonKey.Name)
  @set:JsonSetter(JsonKey.Name)
  var name: String

  @get:JsonGetter(JsonKey.Summary)
  @set:JsonSetter(JsonKey.Summary)
  var summary: String?

  @get:JsonGetter(JsonKey.Description)
  @set:JsonSetter(JsonKey.Description)
  var description: String?

  @get:JsonGetter(JsonKey.Dependencies)
  @set:JsonSetter(JsonKey.Dependencies)
  var dependencies: Collection<VDIDatasetDependency>

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

