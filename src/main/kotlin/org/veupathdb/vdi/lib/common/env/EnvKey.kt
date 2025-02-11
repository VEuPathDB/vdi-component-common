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
     * * Type: `String`
     * * Required: **yes**
     */
    const val SecretKey = "ADMIN_AUTH_TOKEN"
  }

  /**
   * Environment keys for LDAP related settings.
   */
  object LDAP {

    /**
     * * Type: `List<[HostAddress]>`
     * * Required: **yes**
     */
    const val LDAPServers = "LDAP_SERVER"

    /**
     * * Type: `String`
     * * Required: **yes**
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
     * Environment variable prefix for the database enabled/disabled flag.
     *
     * * Type: `Boolean`
     * * Required: no
     */
    const val DBEnabledPrefix = "${CommonPrefix}ENABLED_"

    /**
     * Environment variable prefix for the database hostname value.
     *
     * This environment variable would not typically be used at the same time as
     * [DBLDAPPrefix] prefixed variables.
     *
     * * Type: `String`
     * * Required: no
     */
    const val DBHostPrefix = "${CommonPrefix}HOST_"

    /**
     * Environment variable prefix for the database host port value.
     *
     * This environment variable would not typically be used at the same time as
     * [DBLDAPPrefix] prefixed variables.
     *
     * * Type: `UByte`
     * * Required: no
     */
    const val DBPortPrefix = "${CommonPrefix}PORT_"

    /**
     * Environment variable prefix for the database schema containing the VDI
     * control tables.
     *
     * * Type: `String`
     * * Required: **yes**
     */
    const val DBControlSchemaPrefix = "${CommonPrefix}CONTROL_SCHEMA_"

    /**
     * Environment variable prefix for the database schema containing the VDI
     * installed-data tables.
     *
     * * Type: `String`
     * * Required: **yes**
     */
    const val DBDataSchemaPrefix = "${CommonPrefix}DATA_SCHEMA_"

    /**
     * Environment variable prefix for the unique database identifier.
     *
     * * Type: `String`
     * * Required: **yes**
     */
    const val DBConnectionNamePrefix = "${CommonPrefix}NAME_"

    /**
     * Environment variable prefix for the LDAP service name used to lookup
     * database connection details.
     *
     * This environment variable would not typically be used at the same time as
     * the [DBHostPrefix], [DBPortPrefix], or [DBNamePrefix] prefixed variables.
     *
     * * Type: `String`
     * * Required: no
     */
    const val DBLDAPPrefix = "${CommonPrefix}LDAP_"

    /**
     * Environment variable prefix for the database credentials password.
     *
     * * Type: `String`
     * * Required: **yes**
     */
    const val DBPassPrefix = "${CommonPrefix}PASS_"

    /**
     * Environment variable prefix for the database connection pool size.
     *
     * * Type: `UByte`
     * * Required: no
     */
    const val DBPoolPrefix = "${CommonPrefix}POOL_SIZE_"

    /**
     * Environment variable prefix for the database platform type.
     *
     * * Type: `String`
     * * Required: no
     */
    const val DBPlatformPrefix = "${CommonPrefix}PLATFORM_"

    /**
     * Environment variable prefix for the target database name.
     *
     * This environment variable would not typically be used at the same time as
     * [DBLDAPPrefix] prefixed variables.
     *
     * * Type: `String`
     * * Required: no
     */
    const val DBNamePrefix = "${CommonPrefix}DB_NAME_"

    /**
     * Environment variable prefix for a list of data types that the database
     * represented by the relevant configuration block accepts.
     *
     * * Type: `List<String>`
     * * Required: no
     */
    const val DBConnectionDataTypes = "DB_CONNECTION_DATA_TYPES_"
  }
}
