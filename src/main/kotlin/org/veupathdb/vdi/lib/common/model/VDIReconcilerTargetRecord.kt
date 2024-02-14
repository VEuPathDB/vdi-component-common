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
  @get:JsonGetter(JsonKey.OwnerID)
  val ownerID: UserID

  @get:JsonGetter(JsonKey.Type)
  val type: VDIDatasetType

  @get:JsonGetter(JsonKey.IsUninstalled)
  val isUninstalled: Boolean

  @JsonIgnore
  fun getComparableID(): String

  object JsonKey {
    const val OwnerID = "ownerId"
    const val Type = "type"
    const val IsUninstalled = "isUninstalled"
  }
}

fun VDIReconcilerTargetRecord(
  ownerID: UserID,
  datasetID: DatasetID,
  sharesUpdated: OffsetDateTime,
  dataUpdated: OffsetDateTime,
  metaUpdated: OffsetDateTime,
  type: VDIDatasetType,
  isUninstalled: Boolean,
): VDIReconcilerTargetRecord =
  VDIReconcilerTargetRecordImpl(ownerID, datasetID, sharesUpdated, dataUpdated, metaUpdated, type, isUninstalled)

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

  @JsonProperty(VDIReconcilerTargetRecord.JsonKey.IsUninstalled)
  override val isUninstalled: Boolean,
) : VDIReconcilerTargetRecord {
  override fun getComparableID() = "$ownerID/$datasetID"
}
