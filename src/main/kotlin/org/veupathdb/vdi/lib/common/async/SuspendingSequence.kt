package org.veupathdb.vdi.lib.common.async

interface SuspendingSequence<T> : AutoCloseable {
  fun iterator(): SuspendingIterator<T>
}
