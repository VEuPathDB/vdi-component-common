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

  /**
   * @since 18.0.0
   */
  @get:JsonGetter(JsonKey.IsPrimary)
  val isPrimary: Boolean

  object JsonKey {
    const val Citation  = "citation"
    const val PubMedID  = "pubmedId"
    const val IsPrimary = "isPrimary"
  }
}


/**
 * @since 15.0.0
 */
@Suppress("unused")
fun VDIDatasetPublication(pubmedID: String, citation: String?, isPrimary: Boolean = false): VDIDatasetPublication =
  VDIDatasetPublicationImpl(pubmedID, citation, isPrimary)


private data class VDIDatasetPublicationImpl(
  @JsonProperty(VDIDatasetPublication.JsonKey.PubMedID)
  override val pubmedID: String,

  @JsonProperty(VDIDatasetPublication.JsonKey.Citation)
  override val citation: String?,

  @JsonProperty(VDIDatasetPublication.JsonKey.IsPrimary)
  override val isPrimary: Boolean,
): VDIDatasetPublication {
  init {
    when {
      pubmedID.isBlank() -> throw IllegalArgumentException("pubmed id must not be blank")
      citation?.isBlank() == true -> throw IllegalArgumentException("citation value must not be blank")
    }
  }
}
