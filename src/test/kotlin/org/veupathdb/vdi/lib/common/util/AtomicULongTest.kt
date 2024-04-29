package org.veupathdb.vdi.lib.common.util

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AtomicULongTest {

  @Nested
  @DisplayName("+=")
  inner class PlusAssign {
    @Test
    fun test1() {
      val tgt = AtomicULong()

      runBlocking { tgt += 3uL }

      assertEquals(3uL, runBlocking { tgt.get() })
    }
  }
}