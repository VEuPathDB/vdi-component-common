package org.veupathdb.vdi.lib.common.intra

import com.fasterxml.jackson.annotation.JsonProperty
import org.veupathdb.vdi.lib.common.field.DatasetID
import org.veupathdb.vdi.lib.common.field.ProjectID

data class UninstallRequest(
  @JsonProperty(JSONKeys.VDIID)     val vdiID: DatasetID,
  @JsonProperty(JSONKeys.JobID)     val jobID: ULong,
  @JsonProperty(JSONKeys.ProjectID) val projectID: ProjectID,
)