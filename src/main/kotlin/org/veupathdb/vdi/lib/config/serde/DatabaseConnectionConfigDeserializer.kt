package org.veupathdb.vdi.lib.config.serde

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.contains
import org.veupathdb.vdi.lib.common.field.SecretString
import org.veupathdb.vdi.lib.config.DatabaseConnectionConfig
import org.veupathdb.vdi.lib.config.DirectDatabaseConnectionConfig
import org.veupathdb.vdi.lib.config.LDAPDatabaseConnectionConfig
import org.veupathdb.vdi.lib.config.PartialHostAddress

class DatabaseConnectionConfigDeserializer: StdDeserializer<DatabaseConnectionConfig>(DirectDatabaseConnectionConfig::class.java) {
  override fun deserialize(p: JsonParser, ctxt: DeserializationContext): DatabaseConnectionConfig {
    return p.codec.readTree<ObjectNode>(p).deserialize()
  }

  companion object {
    fun ObjectNode.deserialize(): DatabaseConnectionConfig {
      val user = get("username").textValue()
      val pass = SecretString(get("password").textValue())

      val poolSize = get("poolSize")?.intValue()?.toUByte()

      return if ("lookupCn" in this) {
        LDAPDatabaseConnectionConfig(
          username = user,
          password = pass,
          poolSize = poolSize,
          lookupCN = get("lookupCn").textValue(),
          schema   = get("schema")?.textValue(),
        )
      } else {
        val server = get("server") as ObjectNode

        DirectDatabaseConnectionConfig(
          username = user,
          password = pass,
          platform = get("platform").textValue(),
          poolSize = poolSize,
          server   = PartialHostAddress(server["host"].textValue(), server["port"]?.intValue()?.toUShort()),
          dbName   = get("dbName").textValue(),
          schema   = get("schema")?.textValue(),
        )
      }
    }
  }
}
