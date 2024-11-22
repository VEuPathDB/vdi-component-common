package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonProperty

interface VDIDatasetShareReceipt {
  @get:JsonGetter("action")
  val action: VDIShareReceiptAction
}

fun VDIDatasetShareReceipt(action: VDIShareReceiptAction): VDIDatasetShareReceipt =
  VDIDatasetShareReceiptImpl(action)

private data class VDIDatasetShareReceiptImpl @JsonCreator constructor(
  @JsonProperty("action")
  override val action: VDIShareReceiptAction
): VDIDatasetShareReceipt
