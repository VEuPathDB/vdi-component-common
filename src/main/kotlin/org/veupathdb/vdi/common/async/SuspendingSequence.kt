package org.veupathdb.vdi.common.async

interface SuspendingSequence<T> : AutoCloseable {
  fun iterator(): SuspendingIterator<T>
}
