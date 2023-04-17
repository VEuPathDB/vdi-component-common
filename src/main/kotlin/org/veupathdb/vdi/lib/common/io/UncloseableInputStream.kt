package org.veupathdb.vdi.lib.common.io

import java.io.InputStream

data class UncloseableInputStream(private val rawStream: InputStream) : InputStream() {
  override fun read(): Int {
    return rawStream.read()
  }

  override fun read(b: ByteArray): Int {
    return rawStream.read(b)
  }

  override fun read(b: ByteArray, off: Int, len: Int): Int {
    return rawStream.read(b, off, len)
  }

  override fun close() {
    // DO NOTHING
  }
}
