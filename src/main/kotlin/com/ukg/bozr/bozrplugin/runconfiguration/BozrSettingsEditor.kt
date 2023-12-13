package com.ukg.bozr.bozrplugin.runconfiguration

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent
import javax.swing.JPanel

class BozrSettingsEditor : SettingsEditor<BozrRunConfiguration>() {
    private var myPanel: JPanel
    private var scriptPathField: TextFieldWithBrowseButton = TextFieldWithBrowseButton()

    init {
        scriptPathField.addBrowseFolderListener(
                "Select Test Files",
                null,
                null,
                FileChooserDescriptorFactory.createSingleFileOrFolderDescriptor())
        myPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent("Test files", scriptPathField)
                .panel
    }

    override fun resetEditorFrom(bozrRunConfiguration: BozrRunConfiguration) {
        scriptPathField.text = bozrRunConfiguration.getTestsPath() ?: ""
    }

    override fun applyEditorTo(bozrRunConfiguration: BozrRunConfiguration) {
        bozrRunConfiguration.setTestsPath(scriptPathField.text)
    }

    override fun createEditor(): JComponent = myPanel
}