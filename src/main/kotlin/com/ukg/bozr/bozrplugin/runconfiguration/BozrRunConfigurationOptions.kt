package com.ukg.bozr.bozrplugin.runconfiguration

import com.intellij.execution.configurations.RunConfigurationOptions

class BozrRunConfigurationOptions : RunConfigurationOptions() {
    private val testsPath = string().provideDelegate(this, "testsPath")
    private val host = string().provideDelegate(this, "host")
    private val showInfo = property(true).provideDelegate(this, "showInfo")

    fun getTestsPath(): String? = testsPath.getValue(this)

    fun setTestsPath(testsPath: String?) {
        this.testsPath.setValue(this, testsPath)
    }

    fun getHost(): String? = host.getValue(this)

    fun setHost(host: String?) {
        this.host.setValue(this, host)
    }

    fun getShowInfo(): Boolean = showInfo.getValue(this)

    fun setShowInfo(showInfo: Boolean) {
        this.showInfo.setValue(this, showInfo)
    }
}