package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class VDIDatasetVisibility {
  Private,
  Protected,
  Public,
  ;

  @get:JsonValue
  val value
    get() = when (this) {
      Private    -> "private"
      Protected  -> "protected"
      Public     -> "public"
    }

  companion object {
    @JvmStatic
    @JsonCreator
    fun fromString(value: String) =
      fromStringOrNull(value)
        ?: throw IllegalArgumentException("Unrecognized VDIDatasetVisibility value: $value")

    @JvmStatic
    fun fromStringOrNull(value: String): VDIDatasetVisibility? =
      value.lowercase().let { entries.firstOrNull { e -> e.value == it } }
  }
}
