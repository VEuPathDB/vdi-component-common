package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.veupathdb.vdi.lib.common.field.DatasetID
import org.veupathdb.vdi.lib.common.field.UserID
import java.time.OffsetDateTime

@JsonDeserialize(`as` = VDIReconcilerTargetRecordImpl::class)
interface VDIReconcilerTargetRecord : VDISyncControlRecord {
  @get:JsonIgnore
  @Deprecated("shifting to ownerID for clarity", replaceWith = ReplaceWith("ownerID"))
  val owner get() = ownerID

  @get:JsonGetter(JsonKey.OwnerID)
  val ownerID: UserID

  @get:JsonGetter(JsonKey.Type)
  val type: VDIDatasetType

  @JsonIgnore
  fun getComparableID(): String

  object JsonKey {
    const val OwnerID = "ownerId"
    const val Type = "type"
  }
}

@Deprecated("kept for backwards compatibility, shift usages to the full constructor", replaceWith = ReplaceWith("VDIReconcilerTargetRecord"))
fun VDIReconcilerTargetRecord(syncControlRecord: VDISyncControlRecord, owner: UserID, type: VDIDatasetType): VDIReconcilerTargetRecord =
  VDIReconcilerTargetRecordImpl(
    ownerID = owner,
    datasetID = syncControlRecord.datasetID,
    sharesUpdated = syncControlRecord.sharesUpdated,
    dataUpdated = syncControlRecord.dataUpdated,
    metaUpdated = syncControlRecord.metaUpdated,
    type = type,
  )

fun VDIReconcilerTargetRecord(
  ownerID: UserID,
  datasetID: DatasetID,
  sharesUpdated: OffsetDateTime,
  dataUpdated: OffsetDateTime,
  metaUpdated: OffsetDateTime,
  type: VDIDatasetType,
): VDIReconcilerTargetRecord =
  VDIReconcilerTargetRecordImpl(ownerID, datasetID, sharesUpdated, dataUpdated, metaUpdated, type)

private data class VDIReconcilerTargetRecordImpl(
  @JsonProperty(VDIReconcilerTargetRecord.JsonKey.OwnerID)
  override val ownerID: UserID,

  @JsonProperty(VDISyncControlRecord.JsonKey.DatasetID)
  override val datasetID: DatasetID,

  @JsonProperty(VDISyncControlRecord.JsonKey.SharesUpdated)
  override val sharesUpdated: OffsetDateTime,

  @JsonProperty(VDISyncControlRecord.JsonKey.DataUpdated)
  override val dataUpdated: OffsetDateTime,

  @JsonProperty(VDISyncControlRecord.JsonKey.MetaUpdated)
  override val metaUpdated: OffsetDateTime,

  @JsonProperty(VDIReconcilerTargetRecord.JsonKey.Type)
  override val type: VDIDatasetType,
) : VDIReconcilerTargetRecord {
  override fun getComparableID() = "$ownerID/$datasetID"
}
