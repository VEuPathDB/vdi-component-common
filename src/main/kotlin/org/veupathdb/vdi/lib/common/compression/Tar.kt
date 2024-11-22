package org.veupathdb.vdi.lib.common.compression

import org.apache.commons.compress.archivers.tar.TarArchiveEntry
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream
import java.nio.file.Path
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream
import kotlin.io.path.*

object Tar {
  fun compressWithGZip(output: Path, inputs: Collection<Path>) {
    if (output.exists())
      throw IllegalStateException("target output path already exists!")

    TarArchiveOutputStream(GZIPOutputStream(output.outputStream().buffered())).use { tar ->
      inputs.forEach { file ->
        tar.putArchiveEntry(TarArchiveEntry(file, file.name))
        file.inputStream().use { inp -> inp.transferTo(tar) }
        tar.closeArchiveEntry()
      }
    }
  }

  fun decompressWithGZip(inputFile: Path, outputDir: Path) {
    if (!outputDir.exists())
      outputDir.createDirectories()
    if (!outputDir.isDirectory())
      throw IllegalStateException("target output path does not point to a directory")

    TarArchiveInputStream(GZIPInputStream(inputFile.inputStream().buffered())).use { tar ->
      tar.forEach { entry ->
        val target = outputDir.resolve(entry.name)

        if (entry.isDirectory) {
          target.createDirectories()
        } else if (entry.isFile) {
          if (target.exists()) {
            if (target.isDirectory())
              throw IllegalStateException("unable to unpack the file $target due to a directory already existing at that path")
            else
              throw IllegalStateException("unable to unpack the file $target due to a conflicting file already existing at that path")
          }

          target.parent.createDirectories()
          target.createFile()
          target.outputStream().use { os -> tar.transferTo(os) }
        }
      }
    }
  }

  private inline fun TarArchiveInputStream.forEach(fn: (entry: TarArchiveEntry) -> Unit) {
    while (true)
      fn(nextEntry ?: break)
  }
}