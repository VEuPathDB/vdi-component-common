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

  private var miniCount: UShort = 0u

  /**
   * Generates a 14 digit time-based "mini" identifier encoded in base-64.
   *
   * @return A new random identifier string.
   */
  @JvmStatic
  fun generate(): String {
    var uid = System.currentTimeMillis()

    miniLock.withLock {
      if (uid == now) {
        if (miniCount == UShort.MAX_VALUE) {
          Thread.sleep(1)
          uid = System.currentTimeMillis()
          now = uid
          miniCount = 0u
        } else {
          miniCount++
        }
      } else {
        now = uid
        miniCount = 0u
      }
    }

    uid = uid shl 24
    uid = uid or (random.nextLong() and 0xFFFFFF)

    val raw = ByteArray(10)
    val buf = ByteBuffer.wrap(raw)

    buf.putLong(uid)
    buf.putShort(miniCount.toShort())

    return Base64.getUrlEncoder()
      .encodeToString(raw)
      .trimEnd('=')
  }
}