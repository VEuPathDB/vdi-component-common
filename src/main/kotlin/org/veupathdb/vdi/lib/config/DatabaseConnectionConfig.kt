package org.veupathdb.vdi.lib.config

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.veupathdb.vdi.lib.common.field.SecretString
import org.veupathdb.vdi.lib.config.serde.DatabaseConnectionConfigDeserializer

@JsonDeserialize(using = DatabaseConnectionConfigDeserializer::class)
sealed interface DatabaseConnectionConfig {
  val username: String
  val password: SecretString
  val poolSize: UByte?
  val schema: String?
}
