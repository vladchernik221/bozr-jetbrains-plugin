package com.ukg.bozr.bozrplugin.runconfiguration

import com.intellij.execution.configurations.ConfigurationTypeBase
import com.intellij.openapi.util.NotNullLazyValue
import com.ukg.bozr.bozrplugin.icons.BozrIcons

class BozrRunConfigurationType : ConfigurationTypeBase(
        ID,
        "Bozr",
        "Bozr run tests",
        NotNullLazyValue.createValue { BozrIcons.BozrIcon }) {
    companion object {
        const val ID = "BozrRunConfiguration"
    }

    init {
        addFactory(BozrConfigurationFactory(this))
    }
}