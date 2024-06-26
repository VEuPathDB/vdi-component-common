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

class AtomicUShort(initialValue: UShort = 0u) {
  private val lock = Mutex()
  private var value = initialValue

  suspend operator fun inc() = lock.withLock { value++; this }

  suspend inline fun incAndGet() = incAndGet(1L)

  suspend inline fun incAndGet(amt: Short) = incAndGet(amt.toLong())

  suspend inline fun incAndGet(amt: Int) = incAndGet(amt.toLong())

  suspend fun incAndGet(amt: Long) =
    lock.withLock {
      val sub: Boolean
      val chg: UShort

      if (amt < 0) {
        sub = true
        chg = (-amt).toUShort()
      } else {
        sub = false
        chg = amt.toUShort()
      }

      if (sub) {
        if (chg > value)
          throw IllegalArgumentException("refusing to subtract $chg from $value in an unsigned context to avoid rollunder")

        value = (value - chg).toUShort()
      } else {
        if (UShort.MAX_VALUE - value < chg)
          throw IllegalArgumentException("refusing to add $chg to $value in an unsigned context to avoid rollover")

        value = (value + chg).toUShort()
      }

      value
    }


  suspend operator fun dec() = lock.withLock { value--; this }

  suspend inline fun decAndGet() = incAndGet(-1L)

  suspend inline fun decAndGet(amt: Short) = incAndGet(-amt)

  suspend inline fun decAndGet(amt: Int) = incAndGet(-amt)

  suspend inline fun decAndGet(amt: Long) = incAndGet(-amt)


  suspend operator fun plusAssign(other: UShort) { lock.withLock { value = (value + other).toUShort() } }
  suspend operator fun minusAssign(other: UShort) { lock.withLock { value = (value - other).toUShort() } }
  suspend operator fun divAssign(other: UShort) { lock.withLock { value = (value / other).toUShort() } }
  suspend operator fun timesAssign(other: UShort) { lock.withLock { value = (value * other).toUShort() } }
  suspend operator fun remAssign(other: UShort) { lock.withLock { value = (value % other).toUShort() } }

  suspend operator fun compareTo(other: AtomicUShort) = get().compareTo(other.get())
  suspend operator fun compareTo(other: UShort) = get().compareTo(other)

  suspend fun get() = lock.withLock { value }

  suspend fun set(new: UShort) = lock.withLock { value = new }

  override fun toString() = runBlocking { get() }.toString()

  override fun equals(other: Any?) = other is AtomicUShort && runBlocking { other.get() == get() }

  override fun hashCode() = runBlocking { get() }.hashCode()
}

class AtomicUInt(initialValue: UInt = 0u) {
  private val lock = Mutex()
  private var value = initialValue


  suspend operator fun inc() = lock.withLock { value++; this }

  suspend inline fun incAndGet() = incAndGet(1L)

  suspend inline fun incAndGet(amt: Short) = incAndGet(amt.toLong())

  suspend inline fun incAndGet(amt: Int) = incAndGet(amt.toLong())

  suspend fun incAndGet(amt: Long) =
    lock.withLock {
      val sub: Boolean
      val chg: UInt

      if (amt < 0) {
        sub = true
        chg = (-amt).toUInt()
      } else {
        sub = false
        chg = amt.toUInt()
      }

      if (sub) {
        if (chg > value)
          throw IllegalArgumentException("refusing to subtract $chg from $value in an unsigned context to avoid rollunder")

        value -= chg
      } else {
        if (UInt.MAX_VALUE - value < chg)
          throw IllegalArgumentException("refusing to add $chg to $value in an unsigned context to avoid rollover")

        value += chg
      }

      value
    }


  suspend operator fun dec() = lock.withLock { value--; this }

  suspend inline fun decAndGet() = incAndGet(-1L)

  suspend inline fun decAndGet(amt: Short) = incAndGet(-amt)

  suspend inline fun decAndGet(amt: Int) = incAndGet(-amt)

  suspend inline fun decAndGet(amt: Long) = incAndGet(-amt)

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

  suspend inline fun incAndGet() = incAndGet(1L)

  suspend inline fun incAndGet(amt: Short) = incAndGet(amt.toLong())

  suspend inline fun incAndGet(amt: Int) = incAndGet(amt.toLong())

  suspend fun incAndGet(amt: Long) =
    lock.withLock {
      val sub: Boolean
      val chg: ULong

      if (amt < 0) {
        sub = true
        chg = (-amt).toULong()
      } else {
        sub = false
        chg = amt.toULong()
      }

      if (sub) {
        if (chg > value)
          throw IllegalArgumentException("refusing to subtract $chg from $value in an unsigned context to avoid rollunder")

        value -= chg
      } else {
        if (ULong.MAX_VALUE - value < chg)
          throw IllegalArgumentException("refusing to add $chg to $value in an unsigned context to avoid rollover")

        value += chg
      }

      value
    }


  suspend operator fun dec() = lock.withLock { value--; this }

  suspend inline fun decAndGet() = incAndGet(-1L)

  suspend inline fun decAndGet(amt: Short) = incAndGet(-amt)

  suspend inline fun decAndGet(amt: Int) = incAndGet(-amt)

  suspend inline fun decAndGet(amt: Long) = incAndGet(-amt)

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

