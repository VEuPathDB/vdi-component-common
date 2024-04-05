package org.veupathdb.vdi.lib.common.env

import org.veupathdb.vdi.lib.common.field.SecretString

data class DBEnvGroup(
  val enabled: Boolean?,
  val name: String?,
  val ldap: String?,
  val pass: SecretString?,
  val controlSchema: String?,
  val dataSchema: String?,
  val poolSize: UByte?,
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
            env[EnvKey.AppDB.DBEnabledPrefix + id]?.toBoolean(),
            env[EnvKey.AppDB.DBNamePrefix + id],
            env[EnvKey.AppDB.DBLDAPPrefix + id],
            env[EnvKey.AppDB.DBPassPrefix + id]?.let(::SecretString),
            env[EnvKey.AppDB.DBControlSchemaPrefix + id],
            env[EnvKey.AppDB.DBDataSchemaPrefix + id],
            env[EnvKey.AppDB.DBPoolPrefix + id]?.toUByte(),
          ))
        }
      }
  }
}

private fun String.popIdentifier() =
  when {
    startsWith(EnvKey.AppDB.DBEnabledPrefix)       -> substring(EnvKey.AppDB.DBEnabledPrefix.length)
    startsWith(EnvKey.AppDB.DBControlSchemaPrefix) -> substring(EnvKey.AppDB.DBControlSchemaPrefix.length)
    startsWith(EnvKey.AppDB.DBDataSchemaPrefix)    -> substring(EnvKey.AppDB.DBDataSchemaPrefix.length)
    startsWith(EnvKey.AppDB.DBNamePrefix)          -> substring(EnvKey.AppDB.DBNamePrefix.length)
    startsWith(EnvKey.AppDB.DBLDAPPrefix)          -> substring(EnvKey.AppDB.DBLDAPPrefix.length)
    startsWith(EnvKey.AppDB.DBPassPrefix)          -> substring(EnvKey.AppDB.DBPassPrefix.length)
    startsWith(EnvKey.AppDB.DBPoolPrefix)          -> substring(EnvKey.AppDB.DBPoolPrefix.length)
    else                                           -> null
  }
