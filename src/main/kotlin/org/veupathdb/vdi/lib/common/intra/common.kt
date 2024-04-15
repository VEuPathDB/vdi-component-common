package org.veupathdb.vdi.lib.common.intra

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Defines a common response body consisting of a JSON object containing a
 * single key "message" mapped to a string value containing an error message.
 *
 * ```json
 * {
 *   "message": "some error message"
 * }
 * ```
 *
 * @since 10.1.0
 */
data class SimpleErrorResponse(@JsonProperty(JSONKeys.Message) val message: String)

/**
 * Defines a common response body consisting of a JSON object containing a
 * single key "warnings" mapped to an array of strings representing warnings
 * generated during the triggering request.
 *
 * ```json
 * {
 *   "warnings": [
 *     "some warning 1",
 *     "some warning 2"
 *   ]
 * }
 * ```
 *
 * @since 10.1.0
 */
data class WarningResponse(@JsonProperty(JSONKeys.Warnings) val warnings: Collection<String>)