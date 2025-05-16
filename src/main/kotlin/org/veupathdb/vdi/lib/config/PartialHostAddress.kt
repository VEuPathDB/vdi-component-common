package org.veupathdb.vdi.lib.config

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.veupathdb.vdi.lib.common.util.HostAddress
import org.veupathdb.vdi.lib.config.serde.HostAddressDeserializer

@JsonDeserialize(using = HostAddressDeserializer::class)
data class PartialHostAddress(val host: String, val port: UShort?) {
  fun toHostAddress(fallbackPort: UShort) =
    HostAddress(host, port ?: fallbackPort)
}
