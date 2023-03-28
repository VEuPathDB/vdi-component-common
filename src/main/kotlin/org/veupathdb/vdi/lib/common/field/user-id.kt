package org.veupathdb.vdi.lib.common.field

sealed interface UserID {
  fun toLong(): Long
}

fun UserID(value: Long): UserID {
  if (value < 1)
    throw IllegalArgumentException("User ID values cannot be less than 1")

  return LongUserID(value)
}

@JvmInline
private value class LongUserID(val value: Long): UserID {
  override fun toLong() = value
  override fun toString() = value.toString()
}