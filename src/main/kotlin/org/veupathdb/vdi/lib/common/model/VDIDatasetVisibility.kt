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
      Private   -> "private"
      Protected -> "protected"
      Public    -> "public"
    }

  companion object {
    @JvmStatic
    @JsonCreator
    fun fromString(value: String) =
      fromStringOrNull(value)
        ?: throw IllegalArgumentException("Unrecognized VDIDatasetVisibility value: $value")

    @JvmStatic
    fun fromStringOrNull(value: String): VDIDatasetVisibility? {
      val value = value.lowercase()

      for (enum in values())
        if (value == enum.value)
          return enum

      return null
    }
  }
}