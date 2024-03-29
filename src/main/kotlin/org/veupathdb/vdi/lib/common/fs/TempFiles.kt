package org.veupathdb.vdi.lib.common.fs

import java.nio.file.Path
import java.util.UUID
import kotlin.io.path.*

object TempFiles {

  private inline val TEMP_ROOT
    get() = Path.of("/tmp")

  fun makeTempPath(): Path =
    TEMP_ROOT.resolve(UUID.randomUUID().toString())

  fun makeTempDirectory() =
    makeTempPath().also { it.createDirectories() }

  /**
   * Creates a temporary file with the given file name in a new temp directory.
   *
   * Returns a pair containing the temp directory path and the temp file path.
   *
   * This directory must be manually deleted after it has served its purpose.
   *
   * @param fileName Name of the file to create.
   *
   * @return A pair containing the path to the temp directory as well as the
   * path to the temp file in that directory.
   */
  fun makeTempPath(fileName: String): Pair<Path, Path> {
    val dir = makeTempDirectory()
    val file = dir.resolve(fileName)
    return dir to file
  }

  fun makeTempFile() =
    makeTempPath().also { it.createFile() }

  @OptIn(ExperimentalPathApi::class)
  inline fun <T> withTempDirectory(fn: (directory: Path) -> T): T {
    val dir = makeTempDirectory()
    val out = fn(dir)
    dir.deleteRecursively()
    return out
  }

  inline fun <T> withTempFile(fn: (file: Path) -> T): T {
    val file = makeTempFile()
    val out  = fn(file)
    file.deleteIfExists()
    return out
  }

  @OptIn(ExperimentalPathApi::class)
  inline fun <T> withTempPath(fn: (path: Path) -> T): T {
    val path = makeTempPath()
    val out  = fn(path)

    if (path.exists()) {
      if (path.isDirectory())
        path.deleteRecursively()
      else
        path.deleteIfExists()
    }

    return out
  }
}