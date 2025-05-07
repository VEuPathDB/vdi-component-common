package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class VDIDatasetVisibility {
  Private,
  Protected,
  Controlled,
  Public,
  ;

  @get:JsonValue
  val value
    get() = when (this) {
      Private    -> "private"
      Protected  -> "protected"
      Controlled -> "controlled"
      Public     -> "public"
    }

  companion object {
    @JvmStatic
    @JsonCreator
    fun fromString(value: String) =
      fromStringOrNull(value)
        ?: throw IllegalArgumentException("Unrecognized VDIDatasetVisibility value: $value")

    @JvmStatic
    fun fromStringOrNull(value: String): VDIDatasetVisibility? {
      value.lowercase().let {
        for (enum in entries)
          if (enum.value == it)
            return enum
      }

      return null
    }
  }
}
