package org.veupathdb.vdi.lib.common.util

import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

class AtomicUInt(initialValue: UInt = 0u) {
  private val lock = ReentrantReadWriteLock()
  private var value = initialValue

  operator fun inc() = lock.write { value++; this }
  operator fun dec() = lock.write { value--; this }

  operator fun plusAssign(other: UInt) { lock.write { value += other } }
  operator fun minusAssign(other: UInt) { lock.write { value -= other } }
  operator fun divAssign(other: UInt) { lock.write { value /= other } }
  operator fun timesAssign(other: UInt) { lock.write { value *= other } }
  operator fun remAssign(other: UInt) { lock.write { value %= other } }

  fun get() = lock.read { value }

  fun set(new: UInt) { lock.write { value = new } }
}

class AtomicULong(initialValue: ULong = 0uL) {
  private val lock = ReentrantReadWriteLock()
  private var value = initialValue

  operator fun inc() = lock.write { value++; this }
  operator fun dec() = lock.write { value--; this }

  operator fun plusAssign(other: ULong) { lock.write { value += other } }
  operator fun minusAssign(other: ULong) { lock.write { value -= other } }
  operator fun divAssign(other: ULong) { lock.write { value /= other } }
  operator fun timesAssign(other: ULong) { lock.write { value *= other } }
  operator fun remAssign(other: ULong) { lock.write { value %= other } }

  fun get() = lock.read { value }

  fun set(new: ULong) { lock.write { value = new } }
}
