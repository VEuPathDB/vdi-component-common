package org.veupathdb.vdi.lib.common.compression

import org.veupathdb.vdi.lib.common.io.UncloseableInputStream
import java.io.FileNotFoundException
import java.io.InputStream
import java.nio.file.Path
import java.nio.file.StandardOpenOption
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream
import kotlin.io.path.*

/**
 * Zip file operations.
 *
 * @since 1.0.0
 */
object Zip {
  private val SingleZipHeader  = byteArrayOf(0x50, 0x4B, 0x03, 0x04)
  private val EmptyZipHeader   = byteArrayOf(0x50, 0x4B, 0x05, 0x06)
  private val SpannedZipHeader = byteArrayOf(0x50, 0x4B, 0x07, 0x08)

  /**
   * Zip compression level.
   *
   * Must be a value from 0-9.
   *
   * @since 6.3.0
   */
  @JvmInline
  value class Level(val value: UByte) {
    init {
      if (value > 9u) {
        throw IllegalArgumentException("Zip level must be 0-9, $value was given.")
      }
    }
  }

  /**
   * Compresses the files at the given paths ([files]) into a new zip file at
   * the target [zipPath].
   *
   * @param zipPath Path for the zip file that will be created.
   *
   * @param files Paths to the target files that should be included in the built
   * zip file.
   *
   * @param zipLevel Compression level for the output zip.  Defaults to 9.
   *
   * @since 6.3.0
   */
  fun compress(zipPath: Path, files: Iterable<Path>, zipLevel: Level = Level(9u)) {
    val zip = ZipOutputStream(zipPath.toFile().outputStream())
    zip.setLevel(zipLevel.value.toInt())

    for (file in files) {
      val entry = ZipEntry(file.name)
      zip.putNextEntry(entry)

      file.inputStream().transferTo(zip)
      zip.closeEntry()
    }

    zip.close()
  }

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

    if (into.listDirectoryEntries().isNotEmpty())
      throw IllegalStateException("refusing to unzip $this into non-empty directory $into")

    val unzipped = ArrayList<Path>(2)

    zipEntries(this).forEach { (entry, zipStream) ->
      val target = into.resolve(entry.name)

      if (entry.isDirectory) {
        target.createDirectories()
      } else {
        if (target.parent != into)
          target.parent.createDirectories()

        target.outputStream().use { outStream -> zipStream.transferTo(outStream) }

        unzipped.add(target)
      }
    }

    return unzipped
  }

  /**
   * Returns a sequence of [ZipEntry] instances paired with an [InputStream]
   * that may be used to stream out the contents of the zip entry itself.
   *
   * **WARNING**: The [InputStream]s in the returned sequence must be consumed
   * or skipped entirely before processing the next entry in the sequence.  This
   * is due to the way the underlying [ZipInputStream] functions.  If an
   * [InputStream] is consumed _after_ proceeding to the next sequence entry, it
   * will return the contents of the next [InputStream] rather than the contents
   * of the stream that was originally returned.
   *
   * @param zip Path to the zip file whose entries should be sequenced.
   *
   * @return A sequence of [ZipEntry] and [InputStream] instances contained in
   * the target zip file.
   */
  fun zipEntries(zip: Path): Sequence<Pair<ZipEntry, InputStream>> = sequence {
    if (!zip.exists())
      throw FileNotFoundException("cannot open non-existent zip file $zip")

    if (zip.isDirectory())
      throw IllegalStateException("cannot open target zip file $zip as it is a directory")

    if (!zip.isReadable())
      throw IllegalStateException("cannot open unreadable zip file $zip")

    zip.inputStream().use { raw ->
      val stream = ZipInputStream(raw)
      var entry  = stream.nextEntry

      while (entry != null) {
        yield(entry to UncloseableInputStream(stream))
        entry = stream.nextEntry
      }
    }
  }
}
