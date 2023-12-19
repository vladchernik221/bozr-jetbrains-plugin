package com.ukg.bozr.bozrplugin.runconfiguration

import com.intellij.execution.DefaultExecutionResult
import com.intellij.execution.ExecutionResult
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
import com.intellij.execution.runners.ProgramRunner
import com.intellij.execution.testframework.TestConsoleProperties
import com.intellij.execution.testframework.sm.SMTestRunnerConnectionUtil
import com.intellij.execution.testframework.sm.runner.SMTRunnerConsoleProperties
import com.intellij.execution.testframework.ui.BaseTestsOutputConsoleView
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import com.intellij.util.ui.UIUtil

class BozrRunConfiguration(project: Project, factory: ConfigurationFactory, name: String) :
    RunConfigurationBase<BozrRunConfigurationOptions>(project, factory, name) {

    override fun getOptions(): BozrRunConfigurationOptions =
        super.getOptions() as BozrRunConfigurationOptions

    fun getTestsPath(): String? = options.getTestsPath()

    fun setTestsPath(testsPath: String?) {
        options.setTestsPath(testsPath)
    }

    fun getShowInfo(): Boolean = options.getShowInfo()

    fun setShowInfo(showInfo: Boolean) {
        options.setShowInfo(showInfo)
    }

    override fun getConfigurationEditor(): SettingsEditor<out RunConfiguration> = BozrSettingsEditor()

    override fun getState(executor: Executor, environment: ExecutionEnvironment): RunProfileState =
        object : CommandLineState(environment) {
            override fun startProcess(): ProcessHandler {
                val commands = arrayListOf("go_build_bozr_")
                if (options.getShowInfo()) {
                    commands.add("-i")
                }
                commands.add("-H")
//                commands.add("https://wfrdev1.int.kronos.com/ta/"))
                commands.add("http://localhost:8080/ta/")
                commands.add(options.getTestsPath() ?: "")

                val commandLine = GeneralCommandLine(*commands.toTypedArray(), options.getTestsPath())

                val processHandler = ProcessHandlerFactory.getInstance()
                    .createColoredProcessHandler(commandLine)
                ProcessTerminatedListener.attach(processHandler)
                return processHandler
            }

            override fun execute(executor: Executor, runner: ProgramRunner<*>): ExecutionResult {
                val processHandler = startProcess()

                val testConsoleProperties = SMTRunnerConsoleProperties(this@BozrRunConfiguration, "bozr", executor)
                testConsoleProperties.setIfUndefined(TestConsoleProperties.HIDE_PASSED_TESTS, false)

                val console = UIUtil.invokeAndWaitIfNeeded<BaseTestsOutputConsoleView> {
                    SMTestRunnerConnectionUtil.createAndAttachConsole("bozr", processHandler, testConsoleProperties)
                }

                return DefaultExecutionResult(
                    console,
                    processHandler,
                    *createActions(console, processHandler, executor)
                )
            }
        }
}