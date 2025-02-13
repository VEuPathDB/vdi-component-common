package org.veupathdb.vdi.lib.common.model

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.veupathdb.vdi.lib.json.JSON


@DisplayName("VDIDatasetFileInfo")
class VDIDatasetFileInfoTest {
  @Test
  @DisplayName("Compatibility JSON deserialization success")
  fun jsonDeserializationCompatibility() {
    val inputs = """{"filename":"foo","size":1234}"""

    val result = JSON.readValue<VDIDatasetFileInfo>(inputs)

    assertEquals("foo", result.filename)
    assertEquals(1234UL, result.size)
  }

  @Test
  @DisplayName("Standard JSON deserialization success")
  fun jsonDeserializationNormal() {
    val inputs = """{"filename":"foo","fileSize":1234}"""

    val result = JSON.readValue<VDIDatasetFileInfo>(inputs)

    assertEquals("foo", result.filename)
    assertEquals(1234UL, result.size)
  }

  @Test
  @DisplayName("JSON deserialization error: missing file name")
  fun jsonDeserializationErrorNoFilename() {
    val inputs = """{"fileSize":1234}"""

    assertThrows<JsonParseException> { JSON.readValue<VDIDatasetFileInfo>(inputs) }
  }

  @Test
  @DisplayName("JSON deserialization error: wrong type file name")
  fun jsonDeserializationErrorFilenameType() {
    val inputs = """{"filename":1234,"fileSize":1234}"""

    assertThrows<JsonParseException> { JSON.readValue<VDIDatasetFileInfo>(inputs) }
  }

  @Test
  @DisplayName("JSON deserialization error: missing file size")
  fun jsonDeserializationErrorNoFileSize() {
    val inputs = """{"filename":"foo"}"""

    assertThrows<JsonParseException> { JSON.readValue<VDIDatasetFileInfo>(inputs) }
  }
}
