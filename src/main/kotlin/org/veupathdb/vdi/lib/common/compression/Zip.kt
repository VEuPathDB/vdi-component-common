package org.veupathdb.vdi.lib.common.compression

import java.nio.file.Path
import java.nio.file.StandardOpenOption
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import kotlin.io.path.*

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

  /**
   * Unzips the target path into the given [into] directory.
   *
   * @receiver Path to the zip file to unzip.
   *
   * @param into Directory into which the zip file contents should be unzipped.
   *
   * **NOTE** This directory must exist and be empty at the time of this method
   * call.
   *
   * @return A collection of paths to the files that were unzipped.
   */
  fun Path.unzip(into: Path): Collection<Path> {
    if (!into.exists())
      throw IllegalStateException("cannot unzip $this into non-existent path $into")

    if (!into.isDirectory())
      throw IllegalStateException("cannot unzip $this into non-directory path $into")

    if (!exists())
      throw IllegalStateException("cannot unzip non-existent file $this")

    if (!isRegularFile())
      throw IllegalStateException("cannot unzip non-regular-file $this")

    if (!isReadable())
      throw IllegalStateException("cannot unzip unreadable file $this")

    if (into.listDirectoryEntries().isNotEmpty())
      throw IllegalStateException("refusing to unzip $this into non-empty directory $into")

    val unzipped = ArrayList<Path>(2)

    inputStream().use { rawInput ->
      val archiveInput = ZipInputStream(rawInput)

      var entry: ZipEntry?

      while (true) {
        entry = archiveInput.nextEntry

        if (entry == null)
          break

        val target = into.resolve(entry.name)

        if (entry.isDirectory) {
          target.createDirectories()
        } else {
          if (target.parent != into)
            target.parent.createDirectories()

          target.outputStream().use { outStream -> archiveInput.transferTo(outStream) }

          unzipped.add(target)
        }
      }
    }

    return unzipped
  }

  /**
   * Returns a sequence of entry names for the entries in the target zip file.
   *
   * @param zip Path to the zip file whose entries should be listed.
   *
   * @return A sequence of zip entry names.  An entry name will be the path to
   * the file or folder in the zip directory.
   */
  fun zipEntryNames(zip: Path): Sequence<String> = sequence {
    if (!zip.exists())
      throw IllegalStateException("cannot scan non-existent zip file $this")

    if (!zip.isRegularFile())
      throw IllegalStateException("cannot scan non-regular zip file $this")

    if (!zip.isReadable())
      throw IllegalStateException("cannot scan unreadable zip file $this")

    zip.inputStream().use { rawInput ->
      val stream = ZipInputStream(rawInput)
      var entry: ZipEntry? = stream.nextEntry

      while (entry != null) {
        yield(entry.name)
        entry = stream.nextEntry
      }
    }
  }
}

