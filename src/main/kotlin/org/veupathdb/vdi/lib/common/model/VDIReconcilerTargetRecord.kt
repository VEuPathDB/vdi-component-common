package org.veupathdb.vdi.lib.common.model

import org.veupathdb.vdi.lib.common.field.UserID

data class VDIReconcilerTargetRecord(
  val syncControlRecord: VDISyncControlRecord,
  val owner: UserID,
  val type: VDIDatasetType
) {
  fun getComparableID(): String {
    return "$owner/${syncControlRecord.datasetID}"
  }
}
