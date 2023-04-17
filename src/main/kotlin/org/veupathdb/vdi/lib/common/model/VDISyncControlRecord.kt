package org.veupathdb.vdi.lib.common.model

import org.veupathdb.vdi.lib.common.field.DatasetID
import java.time.OffsetDateTime

data class VDISyncControlRecord(
  val datasetID: DatasetID,
  val sharesUpdated: OffsetDateTime,
  val dataUpdated: OffsetDateTime,
  val metaUpdated: OffsetDateTime,
)
