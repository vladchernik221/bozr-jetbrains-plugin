package com.ukg.bozr.bozrplugin.runconfiguration

import com.intellij.execution.configurations.RunConfigurationOptions

class BozrRunConfigurationOptions : RunConfigurationOptions() {
    private val testsPath = string().provideDelegate(this, "testsPath")
    private val host = string().provideDelegate(this, "host")
    private val infoMode = property(true).provideDelegate(this, "infoMode")

    fun getTestsPath(): String? = testsPath.getValue(this)

    fun setTestsPath(testsPath: String?) {
        this.testsPath.setValue(this, testsPath)
    }

    fun getHost(): String? = host.getValue(this)

    fun setHost(host: String?) {
        this.host.setValue(this, host)
    }

    fun getInfoMode(): Boolean = infoMode.getValue(this)

    fun setInfoMode(infoMode: Boolean) {
        this.infoMode.setValue(this, infoMode)
    }
}