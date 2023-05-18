package org.veupathdb.vdi.lib.common.util

/**
 * `or` infix function for elvis operator like functionality with executable
 * blocks.
 *
 * This function returns the receiver value if it is not `null`, otherwise
 * returns the result of executing the given block ([fn]).
 *
 * @receiver A nullable value of type [T].
 *
 * @param T Type of value that will be returned.
 *
 * @param fn Block that will be executed to retrieve a value if and only if the
 * receiver target is `null`.
 *
 * @return Either the value of the receiver, if it was not `null`, or the value
 * returned by executing the block [fn].
 */
inline infix fun <T> T?.or(fn: () -> T): T = this ?: fn()

/**
 * Tests if the target object reference is null.
 */
inline val <T> T?.isNull: Boolean
  get() = this == null