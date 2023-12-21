package com.ukg.bozr.bozrplugin.runconfiguration

import com.intellij.execution.lineMarker.ExecutorAction
import com.intellij.execution.lineMarker.RunLineMarkerContributor
import com.intellij.icons.AllIcons
import com.intellij.json.psi.JsonArray
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.ukg.bozr.bozrplugin.service.BozrFileService

class BozrRunLineMarkerContributor : RunLineMarkerContributor() {
    private val bozrFileService = BozrFileService.getInstance()

    override fun getInfo(element: PsiElement): Info? =
        element.takeIf { it.parent is JsonArray }
            ?.takeIf { it.parent.parent  is PsiFile }
            ?.takeIf { PsiManager.getInstance(it.project).areElementsEquivalent(it.parent.firstChild, it) }
            ?.containingFile
            ?.takeIf { bozrFileService.isFileBozrTest(it) }
            ?.let {
                Info(AllIcons.RunConfigurations.TestState.Run, ExecutorAction.getActions()) {
                    "Run Test"
                }
            }
}