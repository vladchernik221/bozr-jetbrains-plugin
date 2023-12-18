package com.ukg.bozr.bozrplugin.runconfiguration

import com.intellij.execution.configurations.RunConfigurationOptions

class BozrRunConfigurationOptions : RunConfigurationOptions() {
    private val testsPath = string("").provideDelegate(this, "testsPath")
    private val showInfo = property(true).provideDelegate(this, "showInfo")

    fun getTestsPath(): String? = testsPath.getValue(this)

    fun setTestsPath(testsPath: String?) {
        this.testsPath.setValue(this, testsPath)
    }
    fun getShowInfo(): Boolean = showInfo.getValue(this)

    fun setShowInfo(showInfo: Boolean) {
        this.showInfo.setValue(this, showInfo)
    }
}