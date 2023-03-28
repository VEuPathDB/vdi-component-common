package org.veupathdb.vdi.common.async

interface SuspendingIterator<out T> {
  suspend operator fun hasNext(): Boolean
  suspend operator fun next(): T
}

