package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.annotation.JsonProperty

data class VDIDatasetShareReceipt(@JsonProperty("action") val action: VDIShareReceiptAction)
