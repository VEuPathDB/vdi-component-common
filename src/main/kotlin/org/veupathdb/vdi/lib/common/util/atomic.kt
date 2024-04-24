package org.veupathdb.vdi.lib.common.util

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class AtomicBool(initialValue: Boolean) {
  private val lock = Mutex()
  private var value = initialValue

  suspend fun get() = lock.withLock { value }

  suspend fun set(new: Boolean) = lock.withLock { value = new }

  suspend operator fun compareTo(other: AtomicBool) = get().compareTo(other.get())
  suspend operator fun compareTo(other: Boolean) = get().compareTo(other)

  suspend operator fun not() = !get()

  override fun toString() = runBlocking { get() }.toString()

  override fun equals(other: Any?) = other is AtomicBool && runBlocking { other.get() == get() }

  override fun hashCode() = runBlocking { get() }.hashCode()
}

class AtomicUInt(initialValue: UInt = 0u) {
  private val lock = Mutex()
  private var value = initialValue

  suspend operator fun inc() = lock.withLock { value++; this }
  suspend operator fun dec() = lock.withLock { value--; this }

  suspend operator fun plusAssign(other: UInt) { lock.withLock { value += other } }
  suspend operator fun minusAssign(other: UInt) { lock.withLock { value -= other } }
  suspend operator fun divAssign(other: UInt) { lock.withLock { value /= other } }
  suspend operator fun timesAssign(other: UInt) { lock.withLock { value *= other } }
  suspend operator fun remAssign(other: UInt) { lock.withLock { value %= other } }

  suspend operator fun compareTo(other: AtomicUInt) = get().compareTo(other.get())
  suspend operator fun compareTo(other: UInt) = get().compareTo(other)

  suspend fun get() = lock.withLock { value }

  suspend fun set(new: UInt) = lock.withLock { value = new }

  override fun toString() = runBlocking { get() }.toString()

  override fun equals(other: Any?) = other is AtomicUInt && runBlocking { other.get() == get() }

  override fun hashCode() = runBlocking { get() }.hashCode()
}

class AtomicULong(initialValue: ULong = 0uL) {
  private val lock = Mutex()
  private var value = initialValue

  suspend operator fun inc() = lock.withLock { value++; this }
  suspend operator fun dec() = lock.withLock { value--; this }

  suspend operator fun plusAssign(other: ULong) { lock.withLock { value += other } }
  suspend operator fun minusAssign(other: ULong) { lock.withLock { value -= other } }
  suspend operator fun divAssign(other: ULong) { lock.withLock { value /= other } }
  suspend operator fun timesAssign(other: ULong) { lock.withLock { value *= other } }
  suspend operator fun remAssign(other: ULong) { lock.withLock { value %= other } }

  suspend operator fun compareTo(other: AtomicULong) = get().compareTo(other.get())
  suspend operator fun compareTo(other: ULong) = get().compareTo(other)

  suspend fun get() = lock.withLock { value }

  suspend fun set(new: ULong) = lock.withLock { value = new }

  override fun toString() = runBlocking { get() }.toString()

  override fun equals(other: Any?) = other is AtomicULong && runBlocking { other.get() == get() }

  override fun hashCode() = runBlocking { get() }.hashCode()
}

