package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class VDIShareReceiptAction(@get:JsonValue val value: String) {
  Accept("accept"),
  Reject("reject"),
  ;

  companion object {
    @JvmStatic
    @JsonCreator
    fun fromString(value: String) =
      fromStringOrNull(value)
        ?: throw IllegalArgumentException("unrecognized VDIShareReceiptAction value: $value")

    @JvmStatic
    fun fromStringOrNull(value: String): VDIShareReceiptAction? {
      for (enum in values())
        if (enum.value == value)
          return enum

      return null
    }
  }
}
