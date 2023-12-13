package com.ukg.bozr.bozrplugin.runconfiguration

import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.ConfigurationType
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.openapi.components.BaseState
import com.intellij.openapi.project.Project

class BozrConfigurationFactory(type: ConfigurationType) : ConfigurationFactory(type) {
    override fun getId(): String = BozrRunConfigurationType.ID

    override fun createTemplateConfiguration(project: Project): RunConfiguration =
            BozrRunConfiguration(project, this, "Bozr")

    override fun getOptionsClass(): Class<out BaseState> = BozrRunConfigurationOptions::class.java
}