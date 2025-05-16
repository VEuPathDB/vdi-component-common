package org.veupathdb.vdi.lib.config

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.veupathdb.vdi.lib.config.serde.HostAddressListDeserializer

data class LDAPConfig(
  @JsonDeserialize(using = HostAddressListDeserializer::class)
  val servers: List<PartialHostAddress>,
  @param:JsonProperty("baseDn")
  @field:JsonProperty("baseDn")
  val baseDN: String,
)

