package org.veupathdb.vdi.lib.common.fs

import java.nio.file.Path
import java.util.UUID
import kotlin.io.path.exists

@Deprecated("use TempFiles")
object Tmp {
  private val tmpDir = Path.of("/tmp")

  fun newPath(): Path {
    var path = tmpDir.resolve(UUID.randomUUID().toString())

    while (path.exists())
      path = tmpDir.resolve(UUID.randomUUID().toString())

    return path
  }

  inline fun withPath(fn: (tmp: Path) -> Unit) {
    newPath().useThenDelete(fn)
  }

}