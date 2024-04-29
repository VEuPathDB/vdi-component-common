/**
 * Math extension functions that Kotlin sadly didn't come bundled with as of
 * v1.9.23.
 */
package org.veupathdb.vdi.lib.common.util


internal operator fun UShort.plus(rhs: UShort): UShort = toShort().plus(rhs.toShort()).toUShort()
