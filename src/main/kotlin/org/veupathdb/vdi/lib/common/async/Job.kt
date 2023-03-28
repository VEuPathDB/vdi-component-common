package org.veupathdb.vdi.lib.common.async

fun interface Job {
  suspend operator fun invoke()
}