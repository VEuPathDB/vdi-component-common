package org.veupathdb.vdi.lib.common.intra

import com.fasterxml.jackson.annotation.JsonProperty

data class SimpleErrorResponse(@JsonProperty(JSONKeys.Message) val message: String)

data class WarningResponse(@JsonProperty(JSONKeys.Warnings) val warnings: Collection<String>)