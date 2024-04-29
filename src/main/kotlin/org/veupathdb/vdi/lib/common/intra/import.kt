package org.veupathdb.vdi.lib.common.intra

import com.fasterxml.jackson.annotation.JsonProperty
import org.veupathdb.vdi.lib.common.field.DatasetID
import org.veupathdb.vdi.lib.common.model.VDIDatasetMeta

/**
 * Represents the JSON body of a dataset import request made by the core VDI
 * service to a plugin server.
 *
 * ```json
 * {
 *   "vdiID": "W7GY9DuVdxv",
 *   "importIndex": 22305,
 *   "meta": {
 *     ... see VDIDatasetMeta ...
 *   }
 * }
 * ```
 *
 * @since v10.1.0
 */
data class ImportRequest(
  @JsonProperty(JSONKeys.VDIID) val vdiID: DatasetID,
  @JsonProperty(JSONKeys.JobID) val importIndex: UShort,
  @JsonProperty(JSONKeys.Meta)  val meta: VDIDatasetMeta,
)
