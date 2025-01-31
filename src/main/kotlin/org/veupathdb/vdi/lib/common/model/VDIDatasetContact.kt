package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize


@JsonDeserialize(`as` = VDIDatasetContactImpl::class)
interface VDIDatasetContact {
  @get:JsonGetter(JsonKey.Name)
  val name: String

  @get:JsonGetter(JsonKey.Email)
  val email: String?

  @get:JsonGetter(JsonKey.Affiliation)
  val affiliation: String?

  @get:JsonGetter(JsonKey.City)
  val city: String?

  @get:JsonGetter(JsonKey.State)
  val state: String?

  @get:JsonGetter(JsonKey.Country)
  val country: String?

  @get:JsonGetter(JsonKey.Address)
  val address: String?

  @get:JsonGetter(JsonKey.IsPrimary)
  val isPrimary: Boolean

  object JsonKey {
    const val Name        = "name"
    const val Email       = "email"
    const val Affiliation = "affiliation"
    const val City        = "city"
    const val State       = "state"
    const val Country     = "country"
    const val Address     = "address"
    const val IsPrimary   = "isPrimary"
  }
}


@Suppress("unused")
fun VDIDatasetContact(
  name: String,
  email: String?,
  affiliation: String?,
  city: String?,
  state: String?,
  country: String?,
  address: String?,
  isPrimary: Boolean,
): VDIDatasetContact =
  VDIDatasetContactImpl(name, email, affiliation, city, state, country, address, isPrimary)


private data class VDIDatasetContactImpl(
  @JsonProperty(VDIDatasetContact.JsonKey.Name)
  override val name: String,

  @JsonProperty(VDIDatasetContact.JsonKey.Email)
  override val email: String?,

  @JsonProperty(VDIDatasetContact.JsonKey.Affiliation)
  override val affiliation: String?,

  @JsonProperty(VDIDatasetContact.JsonKey.City)
  override val city: String?,

  @JsonProperty(VDIDatasetContact.JsonKey.State)
  override val state: String?,

  @JsonProperty(VDIDatasetContact.JsonKey.Country)
  override val country: String?,

  @JsonProperty(VDIDatasetContact.JsonKey.Address)
  override val address: String?,

  @JsonProperty(VDIDatasetContact.JsonKey.IsPrimary)
  override val isPrimary: Boolean,
): VDIDatasetContact {
  init {
    if (name.isBlank())
      throw IllegalArgumentException("name must not be blank")
  }
}
