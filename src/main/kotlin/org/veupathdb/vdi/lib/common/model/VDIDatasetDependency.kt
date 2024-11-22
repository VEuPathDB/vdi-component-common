package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonDeserialize(`as` = VDIDatasetDependencyImpl::class)
interface VDIDatasetDependency {
  @get:JsonGetter(JsonKey.Identifier)
  val identifier: String

  @get:JsonGetter(JsonKey.Version)
  val version: String

  @get:JsonGetter(JsonKey.DisplayName)
  val displayName: String

  object JsonKey {
    const val Identifier  = "resourceIdentifier"
    const val Version     = "resourceVersion"
    const val DisplayName = "resourceDisplayName"
  }
}

fun VDIDatasetDependency(identifier: String, version: String, displayName: String): VDIDatasetDependency =
  VDIDatasetDependencyImpl(identifier, version, displayName)

private data class VDIDatasetDependencyImpl @JsonCreator constructor(
  @JsonProperty(VDIDatasetDependency.JsonKey.Identifier)
  override val identifier: String,

  @JsonProperty(VDIDatasetDependency.JsonKey.Version)
  override val version: String,

  @JsonProperty(VDIDatasetDependency.JsonKey.DisplayName)
  override val displayName: String,
) : VDIDatasetDependency {
  init {
    if (identifier.isBlank())
      throw IllegalArgumentException("identifier must not be blank")
    if (version.isBlank())
      throw IllegalArgumentException("version must not be blank")
    if (displayName.isBlank())
      throw IllegalArgumentException("displayName must not be blank")
  }
}
