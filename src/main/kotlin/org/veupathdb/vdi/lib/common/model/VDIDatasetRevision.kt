package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.veupathdb.vdi.lib.common.field.DatasetID
import java.time.OffsetDateTime

/**
 * @since 18.0.0
 */
@JsonDeserialize(`as` = VDIDatasetRevisionImpl::class)
interface VDIDatasetRevision {
  /**
   * The action that was performed by the revision.
   */
  @get:JsonGetter(JsonKey.Action)
  val action: VDIDatasetRevisionAction

  /**
   * The timestamp of when the revision was submitted.
   */
  @get:JsonGetter(JsonKey.Timestamp)
  val timestamp: OffsetDateTime

  /**
   * The ID of the new revision.
   *
   * @see [VDIDatasetMeta.originalID]
   */
  @get:JsonGetter(JsonKey.RevisionID)
  val revisionID: DatasetID

  /**
   * The dataset owner provided note explaining the reason for the revision.
   */
  @get:JsonGetter(JsonKey.RevisionNote)
  val revisionNote: String

  object JsonKey {
    const val Action       = "action"
    const val RevisionID   = "revisionId"
    const val RevisionNote = "revisionNote"
    const val Timestamp    = "timestamp"
  }
}

/**
 * Creates a new [VDIDatasetRevision] instance from the given arguments.
 *
 * @param action The action that was performed by the revision.
 *
 * @param timestamp The timestamp of when the revision was submitted.
 *
 * @param revisionID The ID of the new revision.
 *
 * @param revisionNote The dataset owner provided note explaining the reason for
 * the revision.
 *
 * @return The newly created [VDIDatasetRevision] instance.
 *
 * @since 18.0.0
 */
fun VDIDatasetRevision(
  action: VDIDatasetRevisionAction,
  timestamp: OffsetDateTime,
  revisionID: DatasetID,
  revisionNote: String,
): VDIDatasetRevision =
  VDIDatasetRevisionImpl(
    action,
    timestamp,
    revisionID,
    revisionNote,
  )

private data class VDIDatasetRevisionImpl @JsonCreator constructor(
  @JsonProperty(VDIDatasetRevision.JsonKey.Action)
  override val action: VDIDatasetRevisionAction,

  @JsonProperty(VDIDatasetRevision.JsonKey.Timestamp)
  override val timestamp: OffsetDateTime,

  @JsonProperty(VDIDatasetRevision.JsonKey.RevisionID)
  override val revisionID: DatasetID,

  @JsonProperty(VDIDatasetRevision.JsonKey.RevisionNote)
  override val revisionNote: String
): VDIDatasetRevision
