package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

/**
 * Represents the type of action taken to update a VDI dataset's data.
 *
 * @since 18.0.0
 */
enum class VDIDatasetRevisionAction(
  /**
   * Numeric ID of the action type.
   */
  val id: UByte,

  /**
   * String representation of the action type.
   */
  @get:JsonValue
  val stringValue: String
) {
  /**
   * The dataset was replaced with an updated/corrected version.
   */
  Revise(0u, "revise"),

  /**
   * A new dataset was added in addition to the original as an extension of the
   * data in the original dataset.
   */
  Extend(1u, "extend"),
  ;

  override fun toString() = stringValue

  companion object {
    @JsonCreator
    fun fromString(value: String) =
      fromStringOrNull(value)
        ?: throw IllegalArgumentException("unrecognized VDIDatasetRevisionAction value: $value")

    fun fromStringOrNull(value: String): VDIDatasetRevisionAction? {
      for (e in entries)
        if (e.stringValue == value)
          return e

      return null
    }

    fun fromID(id: UByte) =
      fromIDOrNull(id)
        ?: throw IllegalArgumentException("unrecognized VDIDatasetRevisionAction id: $id")


    fun fromIDOrNull(id: UByte): VDIDatasetRevisionAction? {
      for (e in entries)
        if (e.id == id)
          return e

      return null
    }
  }
}
