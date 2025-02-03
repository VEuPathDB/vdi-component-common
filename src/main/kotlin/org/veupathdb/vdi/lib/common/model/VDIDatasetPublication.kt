package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

/**
 * @since 15.0.0
 */
@JsonDeserialize(`as` = VDIDatasetPublicationImpl::class)
interface VDIDatasetPublication {
  @get:JsonGetter(JsonKey.PubMedID)
  val pubmedID: String

  @get:JsonGetter(JsonKey.Citation)
  val citation: String?

  object JsonKey {
    const val Citation = "citation"
    const val PubMedID = "pubmedId"
  }
}


/**
 * @since 15.0.0
 */
@Suppress("unused")
fun VDIDatasetPublication(pubmedID: String, citation: String?): VDIDatasetPublication =
  VDIDatasetPublicationImpl(pubmedID, citation)


private data class VDIDatasetPublicationImpl(
  @JsonProperty(VDIDatasetPublication.JsonKey.PubMedID)
  override val pubmedID: String,

  @JsonProperty(VDIDatasetPublication.JsonKey.Citation)
  override val citation: String?,
): VDIDatasetPublication {
  init {
    when {
      pubmedID.isBlank() -> throw IllegalArgumentException("pubmed id must not be blank")
      citation?.isBlank() == true -> throw IllegalArgumentException("citation value must not be blank")
    }
  }
}
