package org.veupathdb.vdi.lib.common.env

import org.veupathdb.vdi.lib.common.field.SecretString

data class DBEnvGroup(
  val enabled: Boolean?,
  val platform: String?,
  val connectionName: String?,
  val pass: SecretString?,
  val host: String?,
  val port: UShort?,
  val ldap: String?,
  val controlSchema: String?,
  val dataSchema: String?,
  val poolSize: UByte?,
  val dbName: String?,
) {
  companion object {
    @JvmStatic
    fun fromEnvironment(env: Environment) =
      sequence {
        val found = HashSet<String>()

        env.keys.forEach { key ->
          val id = key.popIdentifier()

          if (id.isNullOrBlank() || id in found)
            return@forEach

          found.add(id)

          yield(DBEnvGroup(
            env.optBool(EnvKey.AppDB.DBEnabledPrefix + id),
            env.optional(EnvKey.AppDB.DBPlatformPrefix + id),
            env.optional(EnvKey.AppDB.DBConnectionNamePrefix + id),
            env.optional(EnvKey.AppDB.DBPassPrefix + id)?.let(::SecretString),
            env.optional(EnvKey.AppDB.DBHostPrefix + id),
            env.optUShort(EnvKey.AppDB.DBPortPrefix + id),
            env.optional(EnvKey.AppDB.DBLDAPPrefix + id),
            env.optional(EnvKey.AppDB.DBControlSchemaPrefix + id),
            env.optional(EnvKey.AppDB.DBDataSchemaPrefix + id),
            env.optUByte(EnvKey.AppDB.DBPoolPrefix + id),
            env.optional(EnvKey.AppDB.DBNamePrefix + id),
          ))
        }
      }
  }
}

private fun String.popIdentifier() =
  when {
    startsWith(EnvKey.AppDB.DBEnabledPrefix)        -> substring(EnvKey.AppDB.DBEnabledPrefix.length)
    startsWith(EnvKey.AppDB.DBHostPrefix)           -> substring(EnvKey.AppDB.DBHostPrefix.length)
    startsWith(EnvKey.AppDB.DBPortPrefix)           -> substring(EnvKey.AppDB.DBPortPrefix.length)
    startsWith(EnvKey.AppDB.DBControlSchemaPrefix)  -> substring(EnvKey.AppDB.DBControlSchemaPrefix.length)
    startsWith(EnvKey.AppDB.DBDataSchemaPrefix)     -> substring(EnvKey.AppDB.DBDataSchemaPrefix.length)
    startsWith(EnvKey.AppDB.DBConnectionNamePrefix) -> substring(EnvKey.AppDB.DBConnectionNamePrefix.length)
    startsWith(EnvKey.AppDB.DBLDAPPrefix)           -> substring(EnvKey.AppDB.DBLDAPPrefix.length)
    startsWith(EnvKey.AppDB.DBPassPrefix)           -> substring(EnvKey.AppDB.DBPassPrefix.length)
    startsWith(EnvKey.AppDB.DBPoolPrefix)           -> substring(EnvKey.AppDB.DBPoolPrefix.length)
    startsWith(EnvKey.AppDB.DBPlatformPrefix)       -> substring(EnvKey.AppDB.DBPlatformPrefix.length)
    startsWith(EnvKey.AppDB.DBNamePrefix)           -> substring(EnvKey.AppDB.DBNamePrefix.length)
    else                                            -> null
  }
