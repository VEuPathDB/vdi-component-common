package org.veupathdb.vdi.lib.common.util

import java.nio.ByteBuffer
import java.util.Base64
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.random.Random

object ShortID {
  private val random = Random(System.nanoTime())

  private val miniLock = ReentrantLock()

  private var now = System.currentTimeMillis()

  private var miniCount: UByte = 0u

  /**
   * Generates a 14 digit time-based "mini" identifier encoded in base-64.
   *
   * This identifier is constructed of 3 parts:
   *
   * 1. 32 bits of random data.  This is to prevent conflicts between IDs
   *    generated by different service stacks on the same millisecond.
   * 2. 8 bits of "tie-breaker".  This is a counter that increments when more
   *    than one id is generated within a single millisecond.  If more than 255
   *    IDs are generated in a millisecond, this method will pause for one
   *    millisecond to reset the tie-break counter.
   * 3. 40 bits of timestamp milliseconds.  This is to prevent ID conflicts over
   *    time for up to 35 years.
   *
   * @return A new random identifier string.
   */
  @JvmStatic
  fun generate(): String {
    var time = System.currentTimeMillis()

    miniLock.withLock {
      if (time == now) {
        if (miniCount == UByte.MAX_VALUE) {
          while (time == now) {
            Thread.sleep(1)
            time = System.currentTimeMillis()
          }
          now = time
          miniCount = 0u
        } else {
          miniCount++
        }
      } else {
        now = time
        miniCount = 0u
      }
    }

    val raw = ByteArray(10)
    val buf = ByteBuffer.wrap(raw)

    buf.appendBytes(Random.nextInt().toULong() shl 40, 4)
    buf.put(miniCount.toByte())
    buf.appendBytes(time.toULong() shl 24, 5)

    return Base64.getUrlEncoder()
      .encodeToString(raw)
      .trimEnd('=')
  }

  private fun ByteBuffer.appendBytes(v: ULong, count: Int) {
    if (count > 8)
      throw IllegalArgumentException()

    var mask = 0xFF00000000000000uL

    for (i in 1 .. count) {
      val shift = (8 - i) * 8
      put((((v and mask) shr shift) and 0xFFuL).toUByte().toByte())
      mask = mask shr 8
    }
  }
}