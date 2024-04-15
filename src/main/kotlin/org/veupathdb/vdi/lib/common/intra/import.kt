package org.veupathdb.vdi.lib.common.intra

import com.fasterxml.jackson.annotation.JsonProperty
import org.veupathdb.vdi.lib.common.field.DatasetID
import org.veupathdb.vdi.lib.common.model.VDIDatasetMeta

data class ImportRequest(
  @JsonProperty(JSONKeys.VDIID) val vdiID: DatasetID,
  @JsonProperty(JSONKeys.JobID) val jobID: ULong,
  @JsonProperty(JSONKeys.Meta)  val meta: VDIDatasetMeta,
)
