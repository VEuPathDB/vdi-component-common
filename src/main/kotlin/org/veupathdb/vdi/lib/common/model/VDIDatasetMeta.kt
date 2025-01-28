package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.module.kotlin.readValue
import org.veupathdb.vdi.lib.common.field.ProjectID
import org.veupathdb.vdi.lib.common.field.UserID
import org.veupathdb.vdi.lib.json.JSON
import java.io.InputStream
import java.time.OffsetDateTime

@JsonDeserialize(`as` = VDIDatasetMetaImpl::class)
interface VDIDatasetMeta {
  /**
   * Dataset type information.
   */
  @get:JsonGetter(JsonKey.Type)
  val type: VDIDatasetType

  /**
   * Projects the dataset targets.
   *
   * This value MUST NOT be empty.
   */
  @get:JsonGetter(JsonKey.Projects)
  val projects: Set<ProjectID>

  /**
   * Dataset visibility setting.
   */
  @get:JsonGetter(JsonKey.Visibility)
  val visibility: VDIDatasetVisibility

  /**
   * ID of the user who owns the dataset.
   */
  @get:JsonGetter(JsonKey.Owner)
  val owner: UserID

  /**
   * User provided name for the dataset.
   *
   * This value MUST NOT be [blank][String.isBlank].
   */
  @get:JsonGetter(JsonKey.Name)
  val name: String

  @get:JsonGetter(JsonKey.ShortName)
  val shortName: String?

  /**
   * Optional short summary of the dataset.
   */
  @get:JsonGetter(JsonKey.Summary)
  val summary: String?

  /**
   * Optional long-form description of the dataset.
   */
  @get:JsonGetter(JsonKey.Description)
  val description: String?

  @get:JsonGetter(JsonKey.ShortAttribution)
  val shortAttribution: String?

  @get:JsonGetter(JsonKey.Category)
  val category: String?

  /**
   * Name or identifier for the originating source of the dataset.
   *
   * This value MUST NOT be [blank][String.isBlank].
   */
  @get:JsonGetter(JsonKey.Origin)
  val origin: String

  /**
   * Optional URL for the dataset's origin.
   */
  @get:JsonGetter(JsonKey.SourceURL)
  val sourceURL: String?

  /**
   * Collection of identifiers for external data the dataset depends on.
   */
  @get:JsonGetter(JsonKey.Dependencies)
  val dependencies: Collection<VDIDatasetDependency>

  @get:JsonGetter(JsonKey.Publications)
  val publications: Collection<VDIDatasetPublication>

  @get:JsonGetter(JsonKey.Hyperlinks)
  val hyperlinks: Collection<VDIDatasetHyperlink>

  @get:JsonGetter(JsonKey.Organisms)
  val organisms: Collection<String>

  @get:JsonGetter(JsonKey.Contacts)
  val contacts: Collection<VDIDatasetContact>

  /**
   * Timestamp for when the dataset was created.
   */
  @get:JsonGetter(JsonKey.Created)
  val created: OffsetDateTime

  object JsonKey {
    const val Category         = "category"
    const val Contacts         = "contacts"
    const val Created          = "created"
    const val Dependencies     = "dependencies"
    const val Description      = "description"
    const val Hyperlinks       = "hyperlinks"
    const val Name             = "name"
    const val Organisms        = "organisms"
    const val Origin           = "origin"
    const val Owner            = "owner"
    const val Projects         = "projects"
    const val Publications     = "publications"
    const val ShortAttribution = "shortAttribution"
    const val ShortName        = "shortName"
    const val SourceURL        = "sourceUrl"
    const val Summary          = "summary"
    const val Type             = "type"
    const val Visibility       = "visibility"
  }
}

/**
 * Attempts to parse the contents of the given stream as a JSON representation
 * of a [VDIDatasetMeta] instance.
 *
 * @param rawJSON Input stream containing a serialized JSON representation of a
 * valid `VDIDatasetMeta` instance.
 *
 * @return The parsed `VDIDatasetMeta` instance.
 *
 * @since v14.0.0
 */
fun VDIDatasetMeta(rawJSON: InputStream): VDIDatasetMeta = JSON.readValue(rawJSON)

/**
 * Attempts to parse the contents of the given string as a JSON representation
 * of a [VDIDatasetMeta] instance.
 *
 * @param rawJSON String containing a serialized JSON representation of a valid
 * `VDIDatasetMeta` instance.
 *
 * @return The parsed `VDIDatasetMeta` instance.
 *
 * @since v14.0.0
 */
fun VDIDatasetMeta(rawJSON: String): VDIDatasetMeta = JSON.readValue(rawJSON)

/**
 * Constructs a new `VDIDatasetMeta` instance from the given values.
 *
 * @param type Dataset type information.
 *
 * @param projects Projects the dataset targets.
 *
 * This value MUST NOT be empty.
 *
 * @param visibility Dataset visibility setting.
 *
 * @param owner ID of the user who owns the dataset.
 *
 * @param name User provided name for the dataset.
 *
 * This value MUST NOT be [blank][String.isBlank].
 *
 * @param summary Optional short summary of the dataset.
 *
 * @param description Optional long-form description of the dataset.
 *
 * @param origin Name or identifier for the originating source of the dataset.
 *
 * This value MUST NOT be [blank][String.isBlank].
 *
 * @param sourceURL Optional URL for the dataset's origin.
 *
 * @param created Timestamp for when the dataset was created.
 *
 * @param dependencies Collection of identifiers for external data the dataset
 * depends on.
 *
 * @return A new `VDIDatasetMeta` instance.
 *
 * @throws IllegalArgumentException if [projects] is empty.
 *
 * @throws IllegalArgumentException if [name] is blank.
 *
 * @throws IllegalArgumentException if [origin] is blank.
 *
 * @since v14.0.0
 */
fun VDIDatasetMeta(
  type: VDIDatasetType,
  projects: Set<ProjectID>,
  visibility: VDIDatasetVisibility,
  owner: UserID,
  name: String,
  shortName: String?,
  summary: String?,
  description: String?,
  shortAttribution: String?,
  category: String?,
  origin: String,
  sourceURL: String?,
  created: OffsetDateTime,
  dependencies: Collection<VDIDatasetDependency>,
  publications: Collection<VDIDatasetPublication>,
  hyperlinks: Collection<VDIDatasetHyperlink>,
  organisms: Collection<String>,
  contacts: Collection<VDIDatasetContact>,
): VDIDatasetMeta =
  VDIDatasetMetaImpl(
    type             = type,
    projects         = projects,
    visibility       = visibility,
    owner            = owner,
    name             = name,
    shortName        = shortName,
    summary          = summary,
    description      = description,
    shortAttribution = shortAttribution,
    category         = category,
    origin           = origin,
    sourceURL        = sourceURL,
    created          = created,
    dependencies     = dependencies,
    publications     = publications,
    hyperlinks       = hyperlinks,
    organisms        = organisms,
    contacts         = contacts,
  )

private data class VDIDatasetMetaImpl @JsonCreator constructor(
  @JsonProperty(VDIDatasetMeta.JsonKey.Type)
  override val type: VDIDatasetType,

  @JsonProperty(VDIDatasetMeta.JsonKey.Projects)
  override val projects: Set<ProjectID>,

  @JsonProperty(VDIDatasetMeta.JsonKey.Visibility)
  override val visibility: VDIDatasetVisibility,

  @JsonProperty(VDIDatasetMeta.JsonKey.Owner)
  override val owner: UserID,

  @JsonProperty(VDIDatasetMeta.JsonKey.Name)
  override val name: String,

  @JsonProperty(VDIDatasetMeta.JsonKey.ShortName)
  override val shortName: String?,

  @JsonProperty(VDIDatasetMeta.JsonKey.Summary)
  override val summary: String?,

  @JsonProperty(VDIDatasetMeta.JsonKey.Description)
  override val description: String?,

  @JsonProperty(VDIDatasetMeta.JsonKey.ShortAttribution)
  override val shortAttribution: String?,

  @JsonProperty(VDIDatasetMeta.JsonKey.Category)
  override val category: String?,

  @JsonProperty(VDIDatasetMeta.JsonKey.Origin)
  override val origin: String,

  @JsonProperty(VDIDatasetMeta.JsonKey.SourceURL)
  override val sourceURL: String?,

  @JsonProperty(VDIDatasetMeta.JsonKey.Created)
  override val created: OffsetDateTime,

  @JsonProperty(VDIDatasetMeta.JsonKey.Dependencies)
  override val dependencies: Collection<VDIDatasetDependency>,

  @JsonProperty(VDIDatasetMeta.JsonKey.Publications)
  override val publications: Collection<VDIDatasetPublication>,

  @JsonProperty(VDIDatasetMeta.JsonKey.Hyperlinks)
  override val hyperlinks: Collection<VDIDatasetHyperlink>,

  @JsonProperty(VDIDatasetMeta.JsonKey.Organisms)
  override val organisms: Collection<String>,

  @JsonProperty(VDIDatasetMeta.JsonKey.Contacts)
  override val contacts: Collection<VDIDatasetContact>,
) : VDIDatasetMeta {
  init {
    if (projects.isEmpty())
      throw IllegalArgumentException("projects must not be empty")
    if (origin.isBlank())
      throw IllegalArgumentException("origin must not be blank")
    if (name.isBlank())
      throw IllegalArgumentException("name must not be blank")
  }
}
