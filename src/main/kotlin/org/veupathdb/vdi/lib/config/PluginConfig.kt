package org.veupathdb.vdi.lib.config

interface PluginConfig {
  val installRoot: String?
  val customPath: String?
  val scripts: PluginScriptConfigs?
}
