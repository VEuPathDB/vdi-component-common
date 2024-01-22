package org.veupathdb.vdi.lib.common.model

data class VDIReconcilerTargetRecord(
  val syncControlRecord: VDISyncControlRecord,
  val owner: String,
  val type: VDIDatasetType,
)
