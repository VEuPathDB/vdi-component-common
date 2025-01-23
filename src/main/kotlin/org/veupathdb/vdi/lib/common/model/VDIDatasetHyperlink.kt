package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize


@JsonDeserialize(`as` = VDIDatasetHyperlinkImpl::class)
interface VDIDatasetHyperlink {
  @get:JsonGetter(JsonKey.URL)
  val url: String

  @get:JsonGetter(JsonKey.Text)
  val text: String

  @get:JsonGetter(JsonKey.Description)
  val description: String?

  @get:JsonGetter(JsonKey.IsPublication)
  val isPublication: Boolean

  object JsonKey {
    const val URL           = "url"
    const val Text          = "text"
    const val Description   = "description"
    const val IsPublication = "isPublication"
  }
}

fun VDIDatasetHyperlink(url: String, text: String, description: String?, isPublication: Boolean): VDIDatasetHyperlink =
  VDIDatasetHyperlinkImpl(url, text, description, isPublication)

private data class VDIDatasetHyperlinkImpl(
  @JsonProperty(VDIDatasetHyperlink.JsonKey.URL)
  override val url: String,

  @JsonProperty(VDIDatasetHyperlink.JsonKey.Text)
  override val text: String,

  @JsonProperty(VDIDatasetHyperlink.JsonKey.Description)
  override val description: String?,

  @JsonProperty(VDIDatasetHyperlink.JsonKey.IsPublication)
  override val isPublication: Boolean
) : VDIDatasetHyperlink {
  init {
    when {
      url.isBlank() -> throw IllegalArgumentException("url must not be blank")
      text.isBlank() -> throw IllegalArgumentException("text must not be blank")
    }
  }
}
