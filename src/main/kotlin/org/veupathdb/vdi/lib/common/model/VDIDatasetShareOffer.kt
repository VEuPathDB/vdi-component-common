package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonDeserialize(`as` = VDIDatasetShareOfferImpl::class)
interface VDIDatasetShareOffer {
  @get:JsonGetter("action")
  val action: VDIShareOfferAction
}


@Suppress("unused")
fun VDIDatasetShareOffer(action: VDIShareOfferAction): VDIDatasetShareOffer =
  VDIDatasetShareOfferImpl(action)


private data class VDIDatasetShareOfferImpl @JsonCreator constructor(
  @JsonProperty("action")
  override val action: VDIShareOfferAction
): VDIDatasetShareOffer
