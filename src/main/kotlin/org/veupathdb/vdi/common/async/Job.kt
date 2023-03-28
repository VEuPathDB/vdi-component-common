package org.veupathdb.vdi.common.async

fun interface Job {
  suspend operator fun invoke()
}