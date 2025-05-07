package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.node.ObjectNode
import org.veupathdb.vdi.lib.json.JSON

@JsonDeserialize(`as` = VDIDatasetPropertiesImpl::class)
interface VDIDatasetProperties {
  val rawValue: ObjectNode
}

inline fun <reified T: Any> VDIDatasetProperties.cast(): T =
  JSON.convertValue(rawValue, T::class.java)

fun VDIDatasetProperties(rawValue: ObjectNode): VDIDatasetProperties =
  VDIDatasetPropertiesImpl(rawValue)

private data class VDIDatasetPropertiesImpl(
  @JsonValue
  override val rawValue: ObjectNode
): VDIDatasetProperties
