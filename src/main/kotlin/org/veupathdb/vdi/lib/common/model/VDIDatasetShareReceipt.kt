package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonDeserialize(`as` = VDIDatasetShareReceiptImpl::class)
interface VDIDatasetShareReceipt {
  @get:JsonGetter("action")
  val action: VDIShareReceiptAction
}


@Suppress("unused")
fun VDIDatasetShareReceipt(action: VDIShareReceiptAction): VDIDatasetShareReceipt =
  VDIDatasetShareReceiptImpl(action)


private data class VDIDatasetShareReceiptImpl @JsonCreator constructor(
  @JsonProperty("action")
  override val action: VDIShareReceiptAction
): VDIDatasetShareReceipt
