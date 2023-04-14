package org.veupathdb.vdi.lib.common.env

/**
 * Global Registry of all environment variables declared by the VDI project.
 *
 * This listing does not include environment variables that are declared by the
 * container core library.
 */
object EnvKey {

  object ImportTriggerHandler {

    /**
     * Type: UInt
     * Required: no
     */
    const val WorkerPoolSize = "IMPORT_HANDLER_WORKER_POOL_SIZE"
  }

  object UpdateMetaTriggerHandler {

    /**
     * Type: UInt
     * Required: no
     */
    const val WorkerPoolSize = "UPDATE_META_HANDLER_WORKER_POOL_SIZE"
  }

  object InstallDataTriggerHandler {

    /**
     * Type: UInt
     * Required: no
     */
    const val WorkerPoolSize = "INSTALL_DATA_HANDLER_WORKER_POOL_SIZE"
  }

  object ShareTriggerHandler {

    /**
     * Type: UInt
     * Required: no
     */
    const val WorkerPoolSize = "SHARE_HANDLER_WORKER_POOL_SIZE"
  }

  object SoftDeleteTriggerHandler {

    /**
     * Type: UInt
     * Required: no
     */
    const val WorkerPoolSize = "SOFT_DELETE_HANDLER_WORKER_POOL_SIZE"
  }

  object HardDeleteTriggerHandler {

    /**
     * Type: UInt
     * Required: no
     */
    const val WorkerPoolSize = "HARD_DELETE_HANDLER_WORKER_POOL_SIZE"
  }

  /**
   * Handler Environment Key Components.
   *
   * ```
   * PLUGIN_HANDLER_<NAME>_NAME
   * PLUGIN_HANDLER_<NAME>_ADDRESS
   * PLUGIN_HANDLER_<NAME>_PROJECT_IDS
   * ```
   *
   * Unlike most of the other environment key values defined in the [EnvKey]
   * object, these constants define components of wildcard environment keys
   * which may be specified with any arbitrary `<NAME>` value between the
   * defined prefix value and suffix options.
   *
   * The environment variables set using the prefix and suffixes defined below
   * must appear in groups that contain all suffixes.  For example, given the
   * `<NAME>` value `"RNASEQ"` the following environment variables must all be
   * present:
   *
   * ```
   * PLUGIN_HANDLER_RNASEQ_NAME
   * PLUGIN_HANDLER_RNASEQ_ADDRESS
   * PLUGIN_HANDLER_RNASEQ_PROJECT_IDS
   * ```
   *
   * The types of each of the variables is defined in the comment attached to
   * each of the suffix variables in the [Handlers] object below.
   */
  object Handlers {

    const val Prefix = "PLUGIN_HANDLER_"

    /**
     * Represents the environment variable `PLUGIN_HANDLER_{NAME}_NAME` where
     * `{NAME}` is a wildcard matching any string.
     *
     * This environment variable declares the name for a dataset type.
     *
     * Type: String
     * Required: yes
     */
    const val NameSuffix = "_NAME"

    /**
     * Represents the environment variable `PLUGIN_HANDLER_{NAME}_ADDRESS` where
     * `{NAME}` is a wildcard matching any string.
     *
     * This environment variable declares the host address (hostname and port)
     * of the plugin handler service for datasets of the type named by the
     * wildcard environment variable `PLUGIN_HANDLER_{NAME}_NAME`.
     *
     * Type: HostAddress (`"host:port"`)
     * Required: yes
     */
    const val AddressSuffix = "_ADDRESS"

    /**
     * Represents the environment variable `PLUGIN_HANDLER_{NAME}_PROJECT_IDS`
     * where `{NAME}` is a wildcard matching any string.
     *
     * This environment variable declares the valid projects for a dataset of
     * the type named by the wildcard environment variable
     * `PLUGIN_HANDLER_{NAME}_NAME`.
     *
     * While this environment variable is required, it may be blank.  A blank
     * value means datasets of the defined type are applicable to all projects.
     *
     * Type: List<String> (`"project1,project2,project3"`)
     * Required: no
     */
    const val ProjectIDsSuffix = "_PROJECT_IDS"
  }

  /**
   * Application Database Key Components
   *
   * ```
   * DB_CONNECTION_NAME_<NAME>
   * DB_CONNECTION_LDAP_<NAME>
   * DB_CONNECTION_USER_<NAME>
   * DB_CONNECTION_PASS_<NAME>
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
   * DB_CONNECTION_NAME_PLASMO
   * DB_CONNECTION_LDAP_PLASMO
   * DB_CONNECTION_USER_PLASMO
   * DB_CONNECTION_PASS_PLASMO
   * DB_CONNECTION_POOL_SIZE_PLASMO
   * ```
   *
   * The types of each of the variables is defined in the comment attached to
   * each of the prefix variables in the [AppDB] object below.
   */
  object AppDB {

    /**
     * Type: String
     * Required: yes
     */
    const val DBNamePrefix = "DB_CONNECTION_NAME_"

    /**
     * Type: String
     * Required: yes
     */
    const val DBLDAPPrefix = "DB_CONNECTION_LDAP_"

    /**
     * Type: String
     * Required: yes
     */
    const val DBUserPrefix = "DB_CONNECTION_USER_"

    /**
     * Type: String
     * Required: yes
     */
    const val DBPassPrefix = "DB_CONNECTION_PASS_"

    /**
     * Type: String
     * Required: yes
     */
    const val DBPoolPrefix = "DB_CONNECTION_POOL_SIZE_"
  }

  object CacheDB {

    /**
     * Type: String
     * Required: yes
     */
    const val Host = "CACHE_DB_HOST"

    /**
     * Type: UShort
     * Required: no
     */
    const val Port = "CACHE_DB_PORT"

    /**
     * Type: String
     * Required: yes
     */
    const val Name = "CACHE_DB_NAME"

    /**
     * Type: String
     * Required: yes
     */
    const val Username = "CACHE_DB_USERNAME"

    /**
     * Type: String
     * Required: yes
     */
    const val Password = "CACHE_DB_PASSWORD"

    /**
     * Type: UByte
     * Required: no
     */
    const val PoolSize = "CACHE_DB_POOL_SIZE"
  }

  object Kafka {

    /**
     * Type: List<HostAddress> (`"host:port,host:port"`)
     * Required: yes
     */
    const val Servers = "KAFKA_SERVERS"

    object Consumer {

      /**
       * Type: Duration
       * Required: no
       */
      const val AutoCommitInterval = "KAFKA_CONSUMER_AUTO_COMMIT_INTERVAL"

      /**
       * Type: Enum("earliest"|"latest"|"none")
       * Required: no
       */
      const val AutoOffsetReset = "KAFKA_CONSUMER_AUTO_OFFSET_RESET"

      /**
       * Type: String
       * Required: yes
       */
      const val ClientID = "KAFKA_CONSUMER_CLIENT_ID"

      /**
       * Type: Duration
       * Required: no
       */
      const val ConnectionsMaxIdle = "KAFKA_CONSUMER_CONNECTIONS_MAX_IDLE"

      /**
       * Type: Duration
       * Required: no
       */
      const val DefaultAPITimeout = "KAFKA_CONSUMER_DEFAULT_API_TIMEOUT"

      /**
       * Type: Boolean
       * Required: no
       */
      const val EnableAutoCommit = "KAFKA_CONSUMER_ENABLE_AUTO_COMMIT"

      /**
       * Type: UInt
       * Required: no
       */
      const val FetchMaxBytes = "KAFKA_CONSUMER_FETCH_MAX_BYTES"

      /**
       * Type: UInt
       * Required: no
       */
      const val FetchMinBytes = "KAFKA_CONSUMER_FETCH_MIN_BYTES"

      /**
       * Type: String
       * Required: yes
       */
      const val GroupID = "KAFKA_CONSUMER_GROUP_ID"

      /**
       * Type: String
       * Required: no
       */
      const val GroupInstanceID = "KAFKA_CONSUMER_GROUP_INSTANCE_ID"

      /**
       * Type: Duration
       * Required: no
       */
      const val HeartbeatInterval = "KAFKA_CONSUMER_HEARTBEAT_INTERVAL"

      /**
       * Type: Duration
       * Required: no
       */
      const val MaxPollInterval = "KAFKA_CONSUMER_MAX_POLL_INTERVAL"

      /**
       * Type: UInt
       * Required: no
       */
      const val MaxPollRecords = "KAFKA_CONSUMER_MAX_POLL_RECORDS"

      /**
       * Type: Duration
       * Required: no
       */
      const val PollDuration = "KAFKA_CONSUMER_POLL_DURATION"

      /**
       * Type: UInt
       * Required: no
       */
      const val ReceiveBufferSizeBytes = "KAFKA_CONSUMER_RECEIVE_BUFFER_SIZE_BYTES"

      /**
       * Type: Duration
       * Required: no
       */
      const val ReconnectBackoffMaxTime = "KAFKA_CONSUMER_RECONNECT_BACKOFF_MAX_TIME"

      /**
       * Type: Duration
       * Required: no
       */
      const val ReconnectBackoffTime = "KAFKA_CONSUMER_RECONNECT_BACKOFF_TIME"

      /**
       * Type: Duration
       * Required: no
       */
      const val RequestTimeout = "KAFKA_CONSUMER_REQUEST_TIMEOUT"

      /**
       * Type: Duration
       * Required: no
       */
      const val RetryBackoffTime = "KAFKA_CONSUMER_RETRY_BACKOFF_TIME"

      /**
       * Type: UInt
       * Required: no
       */
      const val SendBufferSizeBytes = "KAFKA_CONSUMER_SEND_BUFFER_SIZE_BYTES"

      /**
       * Type: Duration
       * Required: no
       */
      const val SessionTimeout = "KAFKA_CONSUMER_SESSION_TIMEOUT"
    }

    object Producer {

      /**
       * Type: UInt
       * Required: no
       */
      const val BatchSize = "KAFKA_PRODUCER_BATCH_SIZE"

      /**
       * Type: UInt
       * Required: no
       */
      const val BufferMemoryBytes = "KAFKA_PRODUCER_BUFFER_MEMORY_BYTES"

      /**
       * Type: String
       * Required: yes
       */
      const val ClientID = "KAFKA_PRODUCER_CLIENT_ID"

      /**
       * Type: Enum("none"|"gzip"|"snappy"|"lz4"|"zstd")
       * Required: no
       */
      const val CompressionType = "KAFKA_PRODUCER_COMPRESSION_TYPE"

      /**
       * Type: Duration
       * Required: no
       */
      const val ConnectionsMaxIdle = "KAFKA_PRODUCER_CONNECTIONS_MAX_IDLE"

      /**
       * Type: Duration
       * Required: no
       */
      const val DeliveryTimeout = "KAFKA_PRODUCER_DELIVERY_TIMEOUT"

      /**
       * Type: Duration
       * Required: no
       */
      const val LingerTime = "KAFKA_PRODUCER_LINGER_TIME"

      /**
       * Type: Duration
       * Required: no
       */
      const val MaxBlockingTimeout = "KAFKA_PRODUCER_MAX_BLOCKING_TIMEOUT"

      /**
       * Type: UInt
       * Required: no
       */
      const val MaxRequestSizeBytes = "KAFKA_PRODUCER_MAX_REQUEST_SIZE_BYTES"

      /**
       * Type: Uint
       * Required: no
       */
      const val ReceiveBufferSizeBytes = "KAFKA_PRODUCER_RECEIVE_BUFFER_SIZE_BYTES"

      /**
       * Type: Duration
       * Required: no
       */
      const val ReconnectBackoffMaxTime = "KAFKA_PRODUCER_RECONNECT_BACKOFF_MAX_TIME"

      /**
       * Type: Duration
       * Required: no
       */
      const val ReconnectBackoffTime = "KAFKA_PRODUCER_RECONNECT_BACKOFF_TIME"

      /**
       * Type: Duration
       * Required: no
       */
      const val RequestTimeout = "KAFKA_PRODUCER_REQUEST_TIMEOUT"

      /**
       * Type: Duration
       * Required: no
       */
      const val RetryBackoffTime = "KAFKA_PRODUCER_RETRY_BACKOFF_TIME"

      /**
       * Type: UInt
       * Required: no
       */
      const val SendBufferSizeBytes = "KAFKA_PRODUCER_SEND_BUFFER_SIZE_BYTES"

      /**
       * Type: UInt
       * Required: no
       */
      const val SendRetries = "KAFKA_PRODUCER_SEND_RETRIES"
    }

    object Topic {

      /**
       * Type: String
       * Required: no
       */
      const val HardDeleteTriggers = "KAFKA_TOPIC_HARD_DELETE_TRIGGERS"

      /**
       * Type: String
       * Required: no
       */
      const val ImportResults = "KAFKA_TOPIC_IMPORT_RESULTS"

      /**
       * Type: String
       * Required: no
       */
      const val ImportTriggers = "KAFKA_TOPIC_IMPORT_TRIGGERS"

      /**
       * Type: String
       * Required: no
       */
      const val InstallTriggers = "KAFKA_TOPIC_INSTALL_TRIGGERS"

      /**
       * Type: String
       * Required: no
       */
      const val ShareTriggers = "KAFKA_TOPIC_SHARE_TRIGGERS"

      /**
       * Type: String
       * Required: no
       */
      const val SoftDeleteTriggers = "KAFKA_TOPIC_SOFT_DELETE_TRIGGERS"

      /**
       * Type: String
       * Required: no
       */
      const val UpdateMetaTriggers = "KAFKA_TOPIC_UPDATE_META_TRIGGERS"
    }

    object MessageKey {
      /**
       * Type: String
       * Required: no
       */
      const val HardDeleteTriggers = "KAFKA_MESSAGE_KEY_HARD_DELETE_TRIGGERS"

      /**
       * Type: String
       * Required: no
       */
      const val ImportResults = "KAFKA_MESSAGE_KEY_IMPORT_RESULTS"

      /**
       * Type: String
       * Required: no
       */
      const val ImportTriggers = "KAFKA_MESSAGE_KEY_IMPORT_TRIGGERS"

      /**
       * Type: String
       * Required: no
       */
      const val InstallTriggers = "KAFKA_MESSAGE_KEY_INSTALL_TRIGGERS"

      /**
       * Type: String
       * Required: no
       */
      const val ShareTriggers = "KAFKA_MESSAGE_KEY_SHARE_TRIGGERS"

      /**
       * Type: String
       * Required: no
       */
      const val SoftDeleteTriggers = "KAFKA_MESSAGE_KEY_SOFT_DELETE_TRIGGERS"

      /**
       * Type: String
       * Required: no
       */
      const val UpdateMetaTriggers = "KAFKA_MESSAGE_KEY_UPDATE_META_TRIGGERS"
    }
  }

  object Rabbit {
    const val ConnectionName  = "GLOBAL_RABBIT_CONNECTION_NAME"
    const val Host            = "GLOBAL_RABBIT_HOST"
    const val Password        = "GLOBAL_RABBIT_PASSWORD"
    const val PollingInterval = "GLOBAL_RABBIT_VDI_POLLING_INTERVAL"
    const val Port            = "GLOBAL_RABBIT_PORT"
    const val Username        = "GLOBAL_RABBIT_USERNAME"

    object Exchange {
      const val Arguments  = "GLOBAL_RABBIT_VDI_EXCHANGE_ARGUMENTS"
      const val AutoDelete = "GLOBAL_RABBIT_VDI_EXCHANGE_AUTO_DELETE"
      const val Durable    = "GLOBAL_RABBIT_VDI_EXCHANGE_DURABLE"
      const val Name       = "GLOBAL_RABBIT_VDI_EXCHANGE_NAME"
      const val Type       = "GLOBAL_RABBIT_VDI_EXCHANGE_TYPE"
    }

    object Queue {
      const val Arguments  = "GLOBAL_RABBIT_VDI_QUEUE_ARGUMENTS"
      const val AutoDelete = "GLOBAL_RABBIT_VDI_QUEUE_AUTO_DELETE"
      const val Exclusive  = "GLOBAL_RABBIT_VDI_QUEUE_EXCLUSIVE"
      const val Durable    = "GLOBAL_RABBIT_VDI_QUEUE_DURABLE"
      const val Name       = "GLOBAL_RABBIT_VDI_QUEUE_NAME"
    }

    object Routing {
      const val Key       = "GLOBAL_RABBIT_VDI_ROUTING_KEY"
      const val Arguments = "GLOBAL_RABBIT_VDI_ROUTING_ARGUMENTS"
    }
  }

  object S3 {

    /**
     * Type: String
     * Required: yes
     */
    const val AccessToken = "S3_ACCESS_TOKEN"

    /**
     * Type: String
     * Required: yes
     */
    const val BucketName  = "S3_BUCKET_NAME"

    /**
     * Type: String
     * Required: yes
     */
    const val Host        = "S3_HOST"

    /**
     * Type: UShort
     * Required: yes
     */
    const val Port        = "S3_PORT"

    /**
     * Type: String
     * Required: yes
     */
    const val SecretKey   = "S3_SECRET_KEY"

    /**
     * Type: Boolean
     * Required: yes
     */
    const val UseHTTPS    = "S3_USE_HTTPS"
  }
}