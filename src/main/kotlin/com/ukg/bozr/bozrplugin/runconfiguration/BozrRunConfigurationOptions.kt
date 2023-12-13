package com.ukg.bozr.bozrplugin.runconfiguration

import com.intellij.execution.configurations.RunConfigurationOptions

class BozrRunConfigurationOptions : RunConfigurationOptions() {
    private val testsPath = string("").provideDelegate(this, "bozrTestsPath")


    fun getTestsPath(): String? = testsPath.getValue(this)

    fun setTestsPath(testsPath: String?) {
        this.testsPath.setValue(this, testsPath)
    }
}