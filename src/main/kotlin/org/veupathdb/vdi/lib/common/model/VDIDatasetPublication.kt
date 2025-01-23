package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonDeserialize(`as` = VDIDatasetPublicationImpl::class)
interface VDIDatasetPublication {
  @get:JsonGetter(JsonKey.Citation)
  val citation: String

  @get:JsonGetter(JsonKey.PubMedID)
  val pubmedID: String

  object JsonKey {
    const val Citation = "citation"
    const val PubMedID = "pubmedId"
  }
}

fun VDIDatasetPublication(citation: String, pubmedID: String): VDIDatasetPublication =
  VDIDatasetPublicationImpl(citation, pubmedID)

private data class VDIDatasetPublicationImpl(
  @JsonProperty(VDIDatasetPublication.JsonKey.Citation)
  override val citation: String,

  @JsonProperty(VDIDatasetPublication.JsonKey.PubMedID)
  override val pubmedID: String,
): VDIDatasetPublication {
  init {
    when {
      citation.isBlank() -> throw IllegalArgumentException("citation value must not be blank")
      pubmedID.isBlank() -> throw IllegalArgumentException("pubmed id must not be blank")
    }
  }
}
