package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonProperty

interface VDIDatasetShareOffer {
  @get:JsonGetter("action")
  val action: VDIShareOfferAction
}

fun VDIDatasetShareOffer(action: VDIShareOfferAction): VDIDatasetShareOffer =
  VDIDatasetShareOfferImpl(action)

private data class VDIDatasetShareOfferImpl @JsonCreator constructor(
  @JsonProperty("action")
  override val action: VDIShareOfferAction
): VDIDatasetShareOffer
