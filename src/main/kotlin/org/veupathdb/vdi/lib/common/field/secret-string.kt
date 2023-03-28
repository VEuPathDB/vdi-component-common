package org.veupathdb.vdi.lib.common.field

sealed interface SecretString {
  fun unwrap(): String
}

@JvmInline
private value class SecretStringImpl(private val value: String) : SecretString {
  override fun unwrap() = value
  override fun toString() = "***"
}
