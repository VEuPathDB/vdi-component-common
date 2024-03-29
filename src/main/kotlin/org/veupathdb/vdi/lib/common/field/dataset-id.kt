package org.veupathdb.vdi.lib.common.field

import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import org.veupathdb.vdi.lib.common.util.ShortID

/**
 * Dataset Identifier
 *
 * An opaque identifier that is unique to a single VDI dataset.
 *
 * @since 1.0.0
 * @author Elizabeth Paige Harper - https://github.com/foxcapades
 */
@JsonDeserialize(using = DatasetIDDeserializer::class)
@JsonSerialize(using = DatasetIDSerializer::class)
sealed interface DatasetID {
  @JsonValue
  override fun toString(): String
}

@JvmInline
private value class StringDatasetID(val value: String) : DatasetID {
  override fun toString() = value
}

/**
 * Attempts to construct a new [DatasetID] instance from the given string value.
 *
 * @param raw Raw string value that should be used as a [DatasetID] instance.
 *
 * @return A [DatasetID] instance wrapping the given [raw] string.
 */
fun DatasetID(raw: String): DatasetID {
  return StringDatasetID(raw)
}

/**
 * Generates a new dataset, random dataset ID.
 *
 * Generated IDs are not guaranteed to be 100% unique, however the chance of
 * collision with another dataset ID is 1 in 2.71 quintillion, and thus may be
 * safely considered unique for most applications.
 *
 * @return A new random dataset ID.
 */
fun DatasetID(): DatasetID {
  return DatasetID(ShortID.generate())
}

class DatasetIDDeserializer : JsonDeserializer<DatasetID>() {
  override fun deserialize(p: JsonParser, ctxt: DeserializationContext): DatasetID {
    val node = p.codec.readTree<JsonNode>(p)
    if (node.isTextual) {
      return DatasetID(node.textValue())
    } else {
      throw JsonParseException(p, "expected node to be textual, it was not")
    }
  }
}

class DatasetIDSerializer : JsonSerializer<DatasetID>() {
  override fun serialize(value: DatasetID, gen: JsonGenerator, serializers: SerializerProvider) {
    gen.writeString(value.toString())
  }
}

fun String.toDatasetID() = DatasetID(this)
fun String.toDatasetIDOrNull() = try { DatasetID(this) } catch (e: Throwable) { null }
