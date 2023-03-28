package org.veupathdb.vdi.lib.common.field

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonDeserialize(using = SecretStringDeserializer::class)
sealed interface SecretString {
  fun unwrap(): String
}

@JvmInline
private value class SecretStringImpl(private val value: String) : SecretString {
  override fun unwrap() = value
  override fun toString() = "***"
}

class SecretStringDeserializer : JsonDeserializer<SecretString>() {
  override fun deserialize(p: JsonParser, ctxt: DeserializationContext): SecretString {
    val node = p.codec.readTree<JsonNode>(p)
    if (node.isTextual)
      return SecretStringImpl(node.textValue())
    else
      throw JsonParseException(p, "expected node to be textual but it was not")
  }
}