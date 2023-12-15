package com.ukg.bozr.bozrplugin.runconfiguration

import com.intellij.execution.Executor
import com.intellij.execution.configurations.CommandLineState
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.execution.configurations.RunConfigurationBase
import com.intellij.execution.configurations.RunProfileState
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.process.ProcessHandlerFactory
import com.intellij.execution.process.ProcessTerminatedListener
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project

class BozrRunConfiguration(project: Project, factory: ConfigurationFactory, name: String) :
    RunConfigurationBase<BozrRunConfigurationOptions>(project, factory, name) {

    override fun getOptions(): BozrRunConfigurationOptions =
        super.getOptions() as BozrRunConfigurationOptions

    fun getTestsPath(): String? = options.getTestsPath()

    fun setTestsPath(testsPath: String?) {
        options.setTestsPath(testsPath)
    }

    override fun getConfigurationEditor(): SettingsEditor<out RunConfiguration> = BozrSettingsEditor()

    override fun getState(executor: Executor, environment: ExecutionEnvironment): RunProfileState =
        object : CommandLineState(environment) {
            override fun startProcess(): ProcessHandler {
                val commandLine = GeneralCommandLine(
                    "bozr",
                    "-H",
                    "http://localhost:8080/",
                    options.getTestsPath()
                )

                val processHandler = ProcessHandlerFactory.getInstance()
                    .createColoredProcessHandler(commandLine)
                ProcessTerminatedListener.attach(processHandler)
                return processHandler
            }
        }
}