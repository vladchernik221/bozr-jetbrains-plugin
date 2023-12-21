package com.ukg.bozr.bozrplugin.runconfiguration

data class BozrRunConfigurationUiSettings(
    var testsPath: String = "",
    var host: String = "",
    var infoMode: Boolean = true,
    var workersCount: Int = 1,
)
