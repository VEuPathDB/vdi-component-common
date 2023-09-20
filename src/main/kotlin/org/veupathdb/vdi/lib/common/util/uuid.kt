package org.veupathdb.vdi.lib.common.util

import java.nio.ByteBuffer
import java.util.UUID
import java.util.Base64

object ShortUUID {
  @JvmStatic
  fun generate(): String {
    // Generate the UUID
    val uuid = UUID.randomUUID()

    // Copy its value to a byte array.
    val raw  = ByteArray(16)
    val buff = ByteBuffer.wrap(raw)
    buff.putLong(uuid.mostSignificantBits)
    buff.putLong(uuid.leastSignificantBits)

    return Base64.getUrlEncoder().encodeToString(raw).trimEnd('=')
  }
}