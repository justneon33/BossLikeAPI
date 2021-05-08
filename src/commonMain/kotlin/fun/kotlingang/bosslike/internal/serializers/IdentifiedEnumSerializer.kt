package `fun`.kotlingang.bosslike.internal.serializers

import `fun`.kotlingang.bosslike.internal.interfaces.Identified
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

internal inline fun <reified E> identifiedEnumSerializer() where E : Enum<E>, E : Identified =
    IdentifiedEnumSerializer<E>(enumValues())

internal class IdentifiedEnumSerializer<E : Enum<E>> internal constructor(
    private val values: Array<E>
) : KSerializer<E> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("identifiedEnumSerializer")
    override fun deserialize(decoder: Decoder): E {
        val id = decoder.decodeInt()
        return values.first { (it as Identified).id == id }
    }

    override fun serialize(encoder: Encoder, value: E) {
        encoder.encodeInt((value as Identified).id)
    }
}