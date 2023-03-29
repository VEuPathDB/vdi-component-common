package org.veupathdb.vdi.lib.common.field

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

/**
 * Secret String
 *
 * Wraps a string value that should be protected from accidentally printing to
 * logs or leaked through use of [toString].
 *
 * @since 1.0.0
 * @author Elizabeth Paige Harper - https://github.com/foxcapades
 */
@JsonDeserialize(using = SecretStringDeserializer::class)
sealed interface SecretString {

  /**
   * Unwraps the secret string value from this [SecretString] instance.
   */
  fun unwrap(): String
}

/**
 * Wraps the given string in a new [SecretString] instance.
 *
 * @param secret Value to wrap.
 *
 * @return A new [SecretString] instance wrapping the given 
 */
fun SecretString(secret: String): SecretString = SecretStringImpl(secret)

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