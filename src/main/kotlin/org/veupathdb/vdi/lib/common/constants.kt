package org.veupathdb.vdi.lib.common

import java.time.*

/**
 * Project Origin Timestamp
 */
val OriginTimestamp
  get() = ZonedDateTime.of(LocalDate.of(2022, Month.JULY, 1), LocalTime.MIN, ZoneId.systemDefault()).toOffsetDateTime()