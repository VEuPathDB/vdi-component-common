package org.veupathdb.vdi.lib.common.compression

import java.nio.file.Path
import java.nio.file.StandardOpenOption
import kotlin.io.path.inputStream
import kotlin.io.path.isRegularFile

object Zip {
  private val SingleZipHeader  = byteArrayOf(0x50, 0x4B, 0x03, 0x04)
  private val EmptyZipHeader   = byteArrayOf(0x50, 0x4B, 0x05, 0x06)
  private val SpannedZipHeader = byteArrayOf(0x50, 0x4B, 0x07, 0x08)

  /**
   * Returns the type of Zip archive the target path points to.
   *
   * If the target points to a file that is not a valid zip archive, the value
   * [ZipType.Invalid] will be returned.
   *
   * @receiver Target path to test.
   *
   * @return Zip type for the target path, or [ZipType.Invalid] if the target
   * path is not a valid Zip archive.
   */
  fun Path.getZipType(): ZipType {
    if (!isRegularFile())
      return ZipType.Invalid

    return inputStream(StandardOpenOption.READ).use { bs ->
      val buffer = ByteArray(4)

      if (bs.read(buffer) != 4)
        ZipType.Invalid
      else if (buffer.contentEquals(SingleZipHeader))
        ZipType.Standard
      else if (buffer.contentEquals(EmptyZipHeader))
        ZipType.Empty
      else if (buffer.contentEquals(SpannedZipHeader))
        ZipType.Spanned
      else
        ZipType.Invalid
    }
  }
}

