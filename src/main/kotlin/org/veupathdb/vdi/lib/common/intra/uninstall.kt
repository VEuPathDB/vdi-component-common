package org.veupathdb.vdi.lib.common.intra

import com.fasterxml.jackson.annotation.JsonProperty
import org.veupathdb.vdi.lib.common.field.DatasetID
import org.veupathdb.vdi.lib.common.field.ProjectID
import org.veupathdb.vdi.lib.common.model.VDIDatasetType

/**
 * Represents the JSON body of a dataset uninstall request made by the core VDI
 * service to a plugin server.
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
data class UninstallRequest(
  @JsonProperty(JSONKeys.VDIID)     val vdiID:     DatasetID,
  @JsonProperty(JSONKeys.ProjectID) val projectID: ProjectID,

  /**
   * Type information for the target dataset.
   *
   * The dataset type info is used to determine which database connection to use
   * for a target project.
   *
   * @since v14.0.0
   */
  @JsonProperty(JSONKeys.Type)
  val type: VDIDatasetType,
)
