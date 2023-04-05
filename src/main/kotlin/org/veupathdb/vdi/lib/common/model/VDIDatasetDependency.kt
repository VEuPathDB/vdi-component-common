package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonDeserialize(`as` = VDIDatasetDependencyImpl::class)
interface VDIDatasetDependency {
  @get:JsonGetter(JsonKey.Identifier)
  @set:JsonSetter(JsonKey.Identifier)
  var identifier: String

  @get:JsonGetter(JsonKey.Version)
  @set:JsonSetter(JsonKey.Version)
  var version: String

  @get:JsonGetter(JsonKey.DisplayName)
  @set:JsonSetter(JsonKey.DisplayName)
  var displayName: String

  object JsonKey {
    const val Identifier  = "resourceIdentifier"
    const val Version     = "resourceVersion"
    const val DisplayName = "resourceDisplayName"
  }
}

