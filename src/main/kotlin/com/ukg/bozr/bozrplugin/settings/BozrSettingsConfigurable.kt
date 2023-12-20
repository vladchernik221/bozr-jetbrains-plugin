package com.ukg.bozr.bozrplugin.settings

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.observable.properties.AtomicProperty
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.Align
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import com.intellij.util.containers.getIfSingle
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import javax.swing.JComponent


class BozrSettingsConfigurable : Configurable {
    private val uiSettings = BozrUiSettings()
    private val bozrExecutableResults = AtomicProperty("")
    private val settingsState = BozrSettingsState.getInstance()
    private val panel: DialogPanel =
        panel {
            group("Tool Location") {
                row("Path to Bozr Executable") {
                    textFieldWithBrowseButton(
                        "Select Bozr Executable",
                        null,
                        FileChooserDescriptorFactory.createSingleFileOrFolderDescriptor(),
                    ).bindText(uiSettings::executableLocation).align(Align.FILL)
                }

                row {
                    button("Test") { verifyBozrExecutable() }
                    text(bozrExecutableResults.get()).bindText(bozrExecutableResults)
                }
            }

            group("Defaults") {
                row("Default Host") {
                    textField().bindText(uiSettings::defaultHost).align(Align.FILL)
                }
            }
        }

    private fun verifyBozrExecutable() {
        val taskContainer = Runnable {
            try {
                val processes = ProcessBuilder.startPipeline(
                    listOf(
                        ProcessBuilder(
                            uiSettings.executableLocation,
                            "-v"
                        )
                    )
                )
                processes[0].inputStream.use { `is` ->
                    InputStreamReader(`is`).use { isr ->
                        BufferedReader(isr).use { r ->
                            val output = r.lines().getIfSingle()?.takeIf { it.startsWith("bozr version") }
                                ?: "<code><icon src='AllIcons.General.Error'></code>&nbsp;bozr not found"
                            bozrExecutableResults.set(output)
                        }
                    }
                }
            } catch (e: IOException) {
                bozrExecutableResults.set("<code><icon src='AllIcons.General.BalloonError'></code>&nbsp;${e.message ?: ""}")
            }
        }


        ProgressManager.getInstance().runProcessWithProgressSynchronously(
            taskContainer,
            "Identifying Bozr Version",
            true,
            null,
        )
    }

    override fun getDisplayName(): String = "Bozr Settings"

    override fun createComponent(): JComponent = panel

    override fun isModified(): Boolean {
        panel.apply()
        var modified = uiSettings.executableLocation != settingsState.executableLocation
        modified = modified || uiSettings.defaultHost != settingsState.defaultHost
        return modified
    }

    override fun apply() {
        panel.apply()
        settingsState.executableLocation = uiSettings.executableLocation
        settingsState.defaultHost = uiSettings.defaultHost
    }

    override fun reset() {
        uiSettings.executableLocation = settingsState.executableLocation
        uiSettings.defaultHost = settingsState.defaultHost
        panel.reset()
    }
}