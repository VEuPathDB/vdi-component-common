package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonProperty

data class VDIDatasetShareOffer(@JsonProperty("action") val action: VDIShareOfferAction)
