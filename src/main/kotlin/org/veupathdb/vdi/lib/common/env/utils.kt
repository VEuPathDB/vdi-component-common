package org.veupathdb.vdi.lib.common.env

import org.veupathdb.vdi.lib.common.util.HostAddress
import java.lang.NumberFormatException
import kotlin.NumberFormatException
import kotlin.time.Duration

typealias Environment = Map<String, String>

/**
 * Requires that a target environment variable is set and non-blank.
 *
 * @param key Key of the target environment variable to require.
 *
 * @return The raw string value of the target environment variable.
 *
 * @throws IllegalStateException If the target environment variable is blank or
 * unset.
 */
fun Environment.require(key: String): String =
  get(key)
    ?.takeIf { it.isNotBlank() }
    ?: throwAbsent(key)

/**
 * Requires that a target environment variable is set, non-blank, and is a valid
 * map of key:value pairs.
 *
 * Key/value pairs are defined as a string value in the following format:
 * ```
 * key1:value,key2:value,key3:value
 * ```
 *
 * Note: Keys may not contain a `:` character, however values may.
 *
 * @param key Key of the target environment variable to require.
 *
 * @return A map of the raw string values present in the environment variable.
 *
 * @throws IllegalStateException If the environment variable is blank, absent,
 * or is not in the correct map format.
 */
fun Environment.reqMap(key: String): Map<String, String> =
  optMap(key) ?: throwAbsent(key)

/**
 * Requires that a target environment variable is set to a non-blank list of
 * [HostAddress] values.
 *
 * `HostAddress` values are encoded as a map string value in the following
 * format:
 * ```
 * host:port,host:port
 * ```
 *
 * In this format, the port value must be a valid `uint16` value.
 *
 * @param key Key of the target environment variable to require.
 *
 * @return A list of [HostAddress] instances.
 *
 * @throws IllegalStateException If the environment variable is blank, absent,
 * or is not in the correct format.
 *
 * @throws NumberFormatException If any of the host address port values are not
 * valid `uint16` values.
 */
fun Environment.reqHostAddresses(key: String): List<HostAddress> =
  optPairSequence(key)
    ?.map { HostAddress(it.first, it.second.toUShort()) }
    ?.toList()
    ?: throwAbsent(key)

/**
 * Requires that a target environment variable is set to a [HostAddress] value.
 *
 * `HostAddress` values are encoded as a string pair in the following format:
 * ```
 * host:port
 * ```
 *
 * In this format, the port value must be a valid `uint16` value.
 *
 * @param key Key of the target environment variable to require.
 *
 * @return The parsed [HostAddress] value.
 *
 * @throws IllegalStateException If the environment variable is blank, absent,
 * or could not be parsed as a `HostAddress` value.
 *
 * @throws NumberFormatException If the host address port value is not a valid
 * `uint16` value.
 */
fun Environment.reqHostAddress(key: String): HostAddress =
  require(key)
    .splitToPair(key)
    .let { HostAddress(it.first, it.second.toUShort()) }

/**
 * Retrieves an optional target environment variable or `null` if the target
 * variable is blank or absent.
 *
 * @param key Key of the target environment variable to look up.
 *
 * @return The raw string value of the environment variable or `null` if the
 * variable is blank or absent.
 */
fun Environment.optional(key: String): String? =
  get(key)?.takeIf { it.isNotBlank() }

/**
 * Retrieves an optional target boolean environment variable or `null` if the
 * target variable is blank or absent.
 *
 * Valid boolean values must align with a value in the following mapping table:
 *
 * | Raw Value | Parsed Value |
 * |-----------|--------------|
 * | `"true"`  | `true`       |
 * | `"yes"`   | `true`       |
 * | `"on"`    | `true`       |
 * | `"1"`     | `true`       |
 * | `"false"` | `false`      |
 * | `"no"`    | `false`      |
 * | `"off"`   | `false`      |
 * | `"0"`     | `false`      |
 *
 * @param key Key of the target environment variable to look up.
 *
 * @return The parsed boolean value from the environment or `null` if the
 * variable was blank or absent.
 *
 * @throws IllegalStateException If the target environment variable could not be
 * parsed as a boolean value according to the table above.
 */
fun Environment.optBool(key: String) =
  when (optional(key)?.lowercase()) {
    null                      -> null
    "true", "yes", "on", "1"  -> true
    "false", "no", "off", "0" -> false
    else                      -> throw IllegalStateException("environment variable $key could not be parsed as a boolean value")
  }

/**
 * Retrieves an optional target `int32` environment variable or `null` if the
 * target variable is blank or absent.
 *
 * @param key Key of the target environment variable to look up.
 *
 * @return The parsed `int32` value from the environment or `null` if the
 * variable was blank or absent.
 *
 * @throws IllegalStateException If the target environment variable could not be
 * parsed as an `int32` value.
 */
fun Environment.optInt(key: String) =
  try {
    optional(key)?.toInt()
  } catch (e: Throwable) {
    throw IllegalStateException("environment variable $key could not be parsed as an int32 value")
  }

/**
 * Retrieves an optional target `uint32` environment variable or `null` if the
 * target variable is blank or absent.
 *
 * @param key Key of the target environment variable to look up.
 *
 * @return The parsed `uint32` value from the environment or `null` if the
 * variable was blank or absent.
 *
 * @throws IllegalStateException If the target environment variable could not be
 * parsed as an `uint32` value.
 */
fun Environment.optUInt(key: String) =
  try {
    optional(key)
      ?.toLong()
      ?.let {
        if (it < 0)
          throw NumberFormatException()
        if (it > UInt.MAX_VALUE.toLong())
          throw NumberFormatException()

        it.toUInt()
      }
  } catch (e: Throwable) {
    throw IllegalStateException("environment variable $key could not be parsed as a uint32 value")
  }

/**
 * Retrieves an optional target `int64` environment variable or `null` if the
 * target variable is blank or absent.
 *
 * @param key Key of the target environment variable to look up.
 *
 * @return The parsed `int64` value from the environment or `null` if the
 * variable was blank or absent.
 *
 * @throws IllegalStateException If the target environment variable could not be
 * parsed as `int64` value.
 */
fun Environment.optLong(key: String) =
  try {
    optional(key)?.toLong()
  } catch (e: Throwable) {
    throw IllegalStateException("environment variable $key could not be parsed as a long value")
  }

/**
 * Retrieves an optional target [Duration] environment variable or `null` if the
 * target variable is blank or absent.
 *
 * Valid duration values consist of one or more numeric components with time
 * unit identifiers. Each component is a number followed by the unit abbreviated
 * name: `d`, `h`, `m`, `s`, `ms`, `us`, `ns`: `"5h"`, `"1d 12h"`,
 * `"1h 0m 30.340s"`, `"-(1h 30m)"`. The last component can be a number with a
 * fractional part.
 *
 * Valid time unit identifiers are:
 * * `d` - Days
 * * `h` - Hours
 * * `m` - Minutes
 * * `s` - Seconds
 * * `ms` - Milliseconds
 * * `us` - Microseconds
 * * `ns` - Nanoseconds
 *
 * @param key Key of the target environment variable to look up.
 *
 * @return The parsed [Duration] value from the environment or `null` if the
 * variable was blank or absent.
 *
 * @throws IllegalStateException If the target environment variable could not be
 * parsed as a [Duration] value.
 */
fun Environment.optDuration(key: String) =
  try {
    optional(key)?.let(Duration.Companion::parse)
  } catch (e: Throwable) {
    throw IllegalStateException("environment variable $key could not be parsed as a duration value")
  }

/**
 * Retrieves an optional target [Map] environment variable or `null` if the
 * target variable is blank or absent.
 *
 * Map environment variables are defined as key/value pairs in a string value
 * in the following format:
 * ```
 * key1:value,key2:value,key3:value
 * ```
 *
 * Note: Keys may not contain a `:` character, however values may.
 *
 * @param key Key of the target environment variable to look up.
 *
 * @return A map of the raw string values present in the environment variable.
 *
 * @throws IllegalStateException If the environment variable is not in the
 * correct map format.
 */
fun Environment.optMap(key: String) =
  optPairSequence(key)
    ?.toMap()

private fun Environment.optPairSequence(key: String): Sequence<Pair<String, String>>? =
  optional(key)
    ?.splitToSequence(',')
    ?.map { it.splitToPair(key) }

private fun String.splitToPair(key: String): Pair<String, String> {
  if (isBlank())
    throw IllegalStateException("malformed map in environment variable $key")

  val it = splitToSequence(':', limit = 2)
    .iterator()

  if (!it.hasNext())
    throw IllegalStateException("malformed map in environment variable $key")

  val first = it.next()

  if (!it.hasNext())
    throw IllegalStateException("malformed map in environment variable $key")

  val second = it.next()

  return first to second
}

private fun throwAbsent(key: String): Nothing =
  throw IllegalStateException("required environment variable $key is blank or absent")