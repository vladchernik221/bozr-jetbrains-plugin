package com.ukg.bozr.bozrplugin.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.service
import com.intellij.util.xmlb.XmlSerializerUtil

@Service
@State(
    name = "com.ukg.bozr.bozrplugin.settings.BozrSettingsState",
    storages = [Storage("BozrPlugin.xml")],
)
class BozrSettingsState(
    var executableLocation: String = "bozr",
    var defaultHost: String = "",
) : PersistentStateComponent<BozrSettingsState> {
    companion object {
        fun getInstance(): BozrSettingsState = service()
    }

    override fun getState(): BozrSettingsState = this

    override fun loadState(state: BozrSettingsState) {
        XmlSerializerUtil.copyBean(state, this)
    }
}