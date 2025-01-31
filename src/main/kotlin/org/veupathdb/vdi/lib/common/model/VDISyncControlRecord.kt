package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.veupathdb.vdi.lib.common.field.DatasetID
import java.time.OffsetDateTime

@JsonDeserialize(`as` = VDISyncControlRecordImpl::class)
interface VDISyncControlRecord {

  @get:JsonGetter(JsonKey.DatasetID)
  val datasetID: DatasetID

  @get:JsonGetter(JsonKey.SharesUpdated)
  val sharesUpdated: OffsetDateTime

  @get:JsonGetter(JsonKey.DataUpdated)
  val dataUpdated: OffsetDateTime

  @get:JsonGetter(JsonKey.MetaUpdated)
  val metaUpdated: OffsetDateTime

  object JsonKey {
    const val DatasetID = "datasetId"
    const val SharesUpdated = "sharesUpdated"
    const val DataUpdated = "dataUpdated"
    const val MetaUpdated = "metaUpdated"
  }
}


@Suppress("unused")
fun VDISyncControlRecord(
  datasetID: DatasetID,
  sharesUpdated: OffsetDateTime,
  dataUpdated: OffsetDateTime,
  metaUpdated: OffsetDateTime
): VDISyncControlRecord = VDISyncControlRecordImpl(datasetID, sharesUpdated, dataUpdated, metaUpdated)


private data class VDISyncControlRecordImpl @JsonCreator constructor(
  @JsonProperty(VDISyncControlRecord.JsonKey.DatasetID)
  override val datasetID: DatasetID,

  @JsonProperty(VDISyncControlRecord.JsonKey.SharesUpdated)
  override val sharesUpdated: OffsetDateTime,

  @JsonProperty(VDISyncControlRecord.JsonKey.DataUpdated)
  override val dataUpdated: OffsetDateTime,

  @JsonProperty(VDISyncControlRecord.JsonKey.MetaUpdated)
  override val metaUpdated: OffsetDateTime,
) : VDISyncControlRecord
