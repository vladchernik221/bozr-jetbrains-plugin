package com.ukg.bozr.bozrplugin.runconfiguration

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.Align
import com.intellij.ui.dsl.builder.bindSelected
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

class BozrSettingsEditor : SettingsEditor<BozrRunConfiguration>() {
    private val bozrUiSettings = BozrUiSettings()
    private val panel: DialogPanel =
        panel {
            row("Test files") {
                textFieldWithBrowseButton(
                    "Select Test Files",
                    null,
                    FileChooserDescriptorFactory.createSingleFileOrFolderDescriptor(),
                ).align(Align.FILL).bindText(bozrUiSettings::testsPath)
            }
            row {
                checkBox("Show info").bindSelected(bozrUiSettings::showInfo)
            }
        }

    override fun createEditor(): JComponent = panel

    override fun resetEditorFrom(bozrRunConfiguration: BozrRunConfiguration) {
        bozrUiSettings.testsPath = bozrRunConfiguration.getTestsPath() ?: ""
        bozrUiSettings.showInfo = bozrRunConfiguration.getShowInfo()
        panel.reset()
    }

    override fun applyEditorTo(bozrRunConfiguration: BozrRunConfiguration) {
        panel.apply()
        bozrRunConfiguration.setTestsPath(bozrUiSettings.testsPath)
        bozrRunConfiguration.setShowInfo(bozrUiSettings.showInfo)
    }
}