@file:Suppress("ConstPropertyName")

package org.veupathdb.vdi.lib.common.env

/**
 * Global Registry of all environment variables declared by the VDI project.
 *
 * This listing does not include environment variables that are declared by the
 * container core library.
 */
object EnvKey {

  /**
   * Service administration related environment variables.
   */
  object Admin {

    /**
     * Administration endpoint secret key value.
     *
     * Type: String
     * Required: yes
     */
    const val SecretKey = "ADMIN_AUTH_TOKEN"
  }

  /**
   * Environment keys for LDAP related settings.
   */
  object LDAP {

    /**
     * Type: List<HostAddress>
     * Required: yes
     */
    const val LDAPServers = "LDAP_SERVER"

    /**
     * Type: String
     * Required: yes
     */
    const val OracleBaseDN = "ORACLE_BASE_DN"
  }

  /**
   * Application Database Key Components
   *
   * ```
   * DB_CONNECTION_ENABLED_<NAME>
   * DB_CONNECTION_NAME_<NAME>
   * DB_CONNECTION_LDAP_<NAME>
   * DB_CONNECTION_PASS_<NAME>
   * DB_CONNECTION_CONTROL_SCHEMA_<NAME>
   * DB_CONNECTION_DATA_SCHEMA_<NAME>
   * DB_CONNECTION_POOL_SIZE_<NAME>
   * ```
   *
   * Unlike most of the other environment key values defined in the [EnvKey]
   * object, these constants define components of wildcard environment keys
   * which may be specified with any arbitrary `<NAME>` value following the
   * defined prefix option.
   *
   * The environment variables set using the prefixes defined below must appear
   * in groups that contain all prefixes.  For example, given the `<NAME>` value
   * `"PLASMO"`, the following environment variables must all be present:
   *
   * ```
   * DB_CONNECTION_ENABLED_PLASMO
   * DB_CONNECTION_NAME_PLASMO
   * DB_CONNECTION_LDAP_PLASMO
   * DB_CONNECTION_PASS_PLASMO
   * DB_CONNECTION_CONTROL_SCHEMA_PLASMO
   * DB_CONNECTION_DATA_SCHEMA_PLASMO
   * DB_CONNECTION_POOL_SIZE_PLASMO
   * ```
   *
   * The types of each of the variables is defined in the comment attached to
   * each of the prefix variables in the [AppDB] object below.
   */
  object AppDB {

    const val CommonPrefix = "DB_CONNECTION_"

    /**
     * Type: Boolean
     * Required: no
     */
    const val DBEnabledPrefix = "${CommonPrefix}ENABLED_"

    /**
     * Type: String
     * Required: no
     */
    const val DBHostPrefix = "${CommonPrefix}HOST_"

    /**
     * Type: UByte
     * Required: no
     */
    const val DBPortPrefix = "${CommonPrefix}PORT_"

    /**
     * Type: String
     * Required: yes
     */
    const val DBControlSchemaPrefix = "${CommonPrefix}CONTROL_SCHEMA_"

    /**
     * Type: String
     * Required: yes
     */
    const val DBDataSchemaPrefix = "${CommonPrefix}DATA_SCHEMA_"

    /**
     * Type: String
     * Required: yes
     */
    const val DBConnectionNamePrefix = "${CommonPrefix}NAME_"

    /**
     * Type: String
     * Required: yes
     */
    const val DBLDAPPrefix = "${CommonPrefix}LDAP_"

    /**
     * Type: String
     * Required: yes
     */
    const val DBPassPrefix = "${CommonPrefix}PASS_"

    /**
     * Type: UByte
     * Required: no
     */
    const val DBPoolPrefix = "${CommonPrefix}POOL_SIZE_"

    /**
     * Type: String
     * Required: no
     */
    const val DBPlatformPrefix = "${CommonPrefix}PLATFORM_"

    /**
     * Type: String
     * Required: no
     */
    const val DBNamePrefix = "${CommonPrefix}DB_NAME_"

    /**
     * Type: List<String>
     * Required: no
     */
    const val DBConnectionDataTypes = "DB_CONNECTION_DATA_TYPES_"
  }
}
