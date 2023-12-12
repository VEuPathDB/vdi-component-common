package org.veupathdb.vdi.lib.common

import java.time.*

/**
 * Project Origin Timestamp
 */
inline val OriginTimestamp
  get() = ZonedDateTime.of(LocalDate.of(2022, Month.JULY, 1), LocalTime.MIN, ZoneId.systemDefault()).toOffsetDateTime()

const val DatasetMetaFilename = "vdi-meta.json"

const val DatasetManifestFilename = "vdi-manifest.json"