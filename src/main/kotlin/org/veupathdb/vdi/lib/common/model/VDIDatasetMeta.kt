package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.module.kotlin.readValue
import org.veupathdb.vdi.lib.common.field.DatasetID
import org.veupathdb.vdi.lib.common.field.ProjectID
import org.veupathdb.vdi.lib.common.field.UserID
import org.veupathdb.vdi.lib.json.JSON
import java.io.InputStream
import java.time.OffsetDateTime

/**
 * @since 1.0.0
 */
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

  /**
   * An optional abbreviated name for the dataset.
   *
   * @since 15.0.0
   */
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

  /**
   * An optional short attribution value for the dataset.
   *
   * @since 15.0.0
   */
  @get:JsonGetter(JsonKey.ShortAttribution)
  val shortAttribution: String?

  /**
   * An optional category for the dataset.
   *
   * @since 15.0.0
   */
  @get:JsonGetter(JsonKey.Category)
  val category: String?

  /**
   * Name or identifier for the originating source of the dataset.
   *
   * This value MUST NOT be [blank][String.isBlank].
   *
   * @since 2.0.0
   */
  @get:JsonGetter(JsonKey.Origin)
  val origin: String

  /**
   * Optional URL for the dataset's origin.
   *
   * @since 2.2.0
   */
  @get:JsonGetter(JsonKey.SourceURL)
  val sourceURL: String?

  /**
   * Collection of identifiers for external data the dataset depends on.
   */
  @get:JsonGetter(JsonKey.Dependencies)
  val dependencies: Collection<VDIDatasetDependency>

  /**
   * Collection of PubMed identifiers.
   *
   * @since 15.0.0
   */
  @get:JsonGetter(JsonKey.Publications)
  val publications: Collection<VDIDatasetPublication>

  /**
   * Collection of hyperlinks relevant to this VDI dataset.
   *
   * @since 15.0.0
   */
  @get:JsonGetter(JsonKey.Hyperlinks)
  val hyperlinks: Collection<VDIDatasetHyperlink>

  /**
   * Collection of organisms relevant to this VDI dataset.
   *
   * @since 15.0.0
   */
  @get:JsonGetter(JsonKey.Organisms)
  val organisms: Collection<String>

  /**
   * Collection of contacts for this VDI dataset.
   *
   * @since 15.0.0
   */
  @get:JsonGetter(JsonKey.Contacts)
  val contacts: Collection<VDIDatasetContact>

  /**
   * ID of the original VDI dataset, if this dataset is a revision.
   *
   * If this dataset is _not_ a revision, this field must be null.  It is an
   * error to set this field to a non-null value if [revisionHistory] contains
   * one or more elements.
   *
   * @since 18.0.0
   */
  @get:JsonGetter(JsonKey.OriginalID)
  val originalID: DatasetID?

  /**
   * List of revisions that have been made to the dataset represented by
   * [originalID].
   *
   * If this dataset is _not_ a revision, this list must be empty.  It is an
   * error to set this field to a non-empty value if [originalID] is null one or
   * more elements.
   *
   * @since 18.0.0
   */
  @get:JsonGetter(JsonKey.RevisionHistory)
  val revisionHistory: List<VDIDatasetRevision>

  /**
   * Timestamp for when the dataset was created.
   *
   * @since 7.0.0
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
    const val OriginalID       = "originalId"
    const val Owner            = "owner"
    const val Projects         = "projects"
    const val Publications     = "publications"
    const val RevisionHistory  = "revisionHistory"
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
 * @param shortName An optional abbreviated name for the dataset.
 *
 * @param summary Optional short summary of the dataset.
 *
 * @param description Optional long-form description of the dataset.
 *
 * @param shortAttribution An optional short attribution value for the dataset.
 *
 * @param category An optional category for the dataset.
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
 * @param publications Collection of publications relevant to this VDI dataset.
 *
 * @param hyperlinks Collection of hyperlinks relevant to this VDI dataset.
 *
 * @param organisms Collection of organisms relevant to this VDI dataset.
 *
 * @param contacts Collection of contacts for this VDI dataset.
 *
 * @param originalID ID of the original VDI dataset, if this dataset is a
 * revision.
 *
 * @param revisionHistory List of revisions that have been made to the dataset
 * represented by [originalID].
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
  originalID: DatasetID? = null,
  revisionHistory: List<VDIDatasetRevision> = emptyList(),
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
    originalID       = originalID,
    revisionHistory  = revisionHistory,
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
  override val dependencies: Collection<VDIDatasetDependency> = emptyList(),

  @JsonProperty(VDIDatasetMeta.JsonKey.Publications)
  override val publications: Collection<VDIDatasetPublication> = emptyList(),

  @JsonProperty(VDIDatasetMeta.JsonKey.Hyperlinks)
  override val hyperlinks: Collection<VDIDatasetHyperlink> = emptyList(),

  @JsonProperty(VDIDatasetMeta.JsonKey.Organisms)
  override val organisms: Collection<String> = emptyList(),

  @JsonProperty(VDIDatasetMeta.JsonKey.Contacts)
  override val contacts: Collection<VDIDatasetContact> = emptyList(),

  @JsonProperty(VDIDatasetMeta.JsonKey.OriginalID)
  override val originalID: DatasetID?,

  @JsonProperty(VDIDatasetMeta.JsonKey.RevisionHistory)
  override val revisionHistory: List<VDIDatasetRevision>,
) : VDIDatasetMeta {
  init {
    if (projects.isEmpty())
      throw IllegalArgumentException("projects must not be empty")
    if (origin.isBlank())
      throw IllegalArgumentException("origin must not be blank")
    if (name.isBlank())
      throw IllegalArgumentException("name must not be blank")
    if (originalID == null && revisionHistory.isNotEmpty())
      throw IllegalArgumentException("original ID must be null if a revision history is present")
    if (originalID != null && revisionHistory.isEmpty())
      throw IllegalArgumentException("revision history must not be empty if an original dataset ID is provided")
    if (publications.isNotEmpty() && publications.count { it.isPrimary } != 1)
      throw IllegalArgumentException("publication list must contain exactly 1 primary publication")
  }
}
