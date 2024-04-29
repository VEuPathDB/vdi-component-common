package org.veupathdb.vdi.lib.common.intra

import com.fasterxml.jackson.annotation.JsonProperty
import org.veupathdb.vdi.lib.common.field.DatasetID
import org.veupathdb.vdi.lib.common.field.ProjectID
import org.veupathdb.vdi.lib.common.model.VDIDatasetMeta

/**
 * Represents the JSON body of a dataset data install request made by the core
 * VDI service to a plugin server.
 *
 * ```json
 * {
 *   "vdiID": "W7GY9DuVdxv",
 *   "projectID": "PlasmoDB"
 * }
 * ```
 *
 * @since v10.1.0
 */
data class InstallDataRequest(
  @JsonProperty(JSONKeys.VDIID)     val vdiID: DatasetID,
  @JsonProperty(JSONKeys.ProjectID) val projectID: ProjectID,
)

/**
 * Represents the JSON body of a dataset meta install request made by the core
 * VDI service to a plugin server.
 *
 * ```json
 * {
 *   "vdiID": "W7GY9DuVdxv",
 *   "projectID": "PlasmoDB",
 *   "meta": {
 *     ... see VDIDatasetMeta ...
 *   }
 * }
 * ```
 *
 * @since v10.1.0
 */
data class InstallMetaRequest(
  @JsonProperty(JSONKeys.VDIID)     val vdiID: DatasetID,
  @JsonProperty(JSONKeys.ProjectID) val projectID: ProjectID,
  @JsonProperty(JSONKeys.Meta)      val meta: VDIDatasetMeta,
)
