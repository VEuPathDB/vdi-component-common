package org.veupathdb.vdi.lib.common.util

import java.nio.ByteBuffer
import java.util.Base64
import kotlin.random.Random

private val random = Random(System.nanoTime())

/**
 * Generates an 11 digit time-based random identifier encoded in base-64.
 *
 * IDs generated with this method follow the following structure:
 * ```
 * 63     23       0
 * +------+--------+
 * | Time | Random |
 * +------+--------+
 * ```
 *
 * The "Time" value is the 40 least significant bits of a Unix Epoch timestamp
 * in milliseconds.
 *
 * The "Random" value is 40 random bits giving 16777216 possible distinct
 * random values.
 *
 * @return A new random identifier string.
 */
internal fun generateShortID(): String {
  var uid = System.currentTimeMillis()

  uid = uid shl 24
  uid = uid or (random.nextLong() and 0xFFFFFF)

  val raw = ByteArray(8)
  val buf = ByteBuffer.wrap(raw)

  buf.putLong(uid)

  return Base64.getUrlEncoder()
    .encodeToString(raw)
    .trimEnd('=')
}
