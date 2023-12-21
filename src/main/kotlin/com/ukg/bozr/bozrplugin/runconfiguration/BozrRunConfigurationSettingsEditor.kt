package com.ukg.bozr.bozrplugin.runconfiguration

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.Align
import com.intellij.ui.dsl.builder.bindSelected
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

class BozrRunConfigurationSettingsEditor : SettingsEditor<BozrRunConfiguration>() {
    private val configurationUiSettings = BozrRunConfigurationUiSettings()
    private val panel: DialogPanel =
        panel {
            row("Test files") {
                textFieldWithBrowseButton(
                    "Select Test Files",
                    null,
                    FileChooserDescriptorFactory.createSingleFileOrFolderDescriptor(),
                ).align(Align.FILL).bindText(configurationUiSettings::testsPath)
            }

            row("Host") {
                textField().align(Align.FILL).bindText(configurationUiSettings::host)
            }

            row {
                checkBox("Info mode").bindSelected(configurationUiSettings::infoMode)
            }
        }

    override fun createEditor(): JComponent = panel

    override fun resetEditorFrom(bozrRunConfiguration: BozrRunConfiguration) {
        configurationUiSettings.testsPath = bozrRunConfiguration.getTestsPath() ?: ""
        configurationUiSettings.infoMode = bozrRunConfiguration.getInfoMode()
        configurationUiSettings.host = bozrRunConfiguration.getHost()
        panel.reset()
    }

    override fun applyEditorTo(bozrRunConfiguration: BozrRunConfiguration) {
        panel.apply()
        bozrRunConfiguration.setTestsPath(configurationUiSettings.testsPath)
        bozrRunConfiguration.setInfoMode(configurationUiSettings.infoMode)
        bozrRunConfiguration.setHost(configurationUiSettings.host)
    }
}