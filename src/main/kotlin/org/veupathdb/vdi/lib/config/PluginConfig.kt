package org.veupathdb.vdi.lib.config

interface PluginConfig {
  val server: PartialHostAddress
  val displayName: String
  val installRoot: String?
  val customPath: String?
  val scripts: PluginScriptConfigs?
}
