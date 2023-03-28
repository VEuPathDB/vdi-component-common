package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class VDIShareOfferAction(@get:JsonValue val value: String) {
  Grant("grant"),
  Revoke("revoke"),
  ;

  companion object {
    @JvmStatic
    @JsonCreator
    fun fromString(value: String) =
      fromStringOrNull(value)
        ?: throw IllegalArgumentException("unrecognized VDIShareOfferAction value: $value")

    @JvmStatic
    fun fromStringOrNull(value: String): VDIShareOfferAction? {
      for (enum in values())
        if (enum.value == value)
          return enum

      return null
    }
  }
}
