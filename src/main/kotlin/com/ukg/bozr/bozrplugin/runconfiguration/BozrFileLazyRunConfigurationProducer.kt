package com.ukg.bozr.bozrplugin.runconfiguration

import com.intellij.execution.actions.ConfigurationContext
import com.intellij.execution.actions.LazyRunConfigurationProducer
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.openapi.util.Ref
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileSystemItem
import com.ukg.bozr.bozrplugin.service.BozrFileService

class BozrFileLazyRunConfigurationProducer : LazyRunConfigurationProducer<BozrRunConfiguration>() {
    private val bozrFileService = BozrFileService.getInstance()

    override fun getConfigurationFactory(): ConfigurationFactory = BozrConfigurationFactory(BozrRunConfigurationType())

    override fun setupConfigurationFromContext(configuration: BozrRunConfiguration, context: ConfigurationContext, ref: Ref<PsiElement>): Boolean {
        val psiLocation = context.psiLocation as? PsiFileSystemItem ?: return false

        if (psiLocation is PsiFile && !bozrFileService.isFileBozrTest(psiLocation)) {
            return false
        }

        configuration.setTestsPath(psiLocation.virtualFile?.canonicalPath)
        configuration.name = psiLocation.name
        return true
    }

    override fun isConfigurationFromContext(configuration: BozrRunConfiguration, context: ConfigurationContext): Boolean = true
}