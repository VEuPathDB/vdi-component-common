package org.veupathdb.vdi.lib.config

data class PluginScriptConfigs(
  val checkCompatibility: PluginScriptConfig?,
  val dataCleaning: PluginScriptConfig?,
  val dataInstall: PluginScriptConfig?,
  val metaUpdate: PluginScriptConfig?,
  val uninstall: PluginScriptConfig?,
)
