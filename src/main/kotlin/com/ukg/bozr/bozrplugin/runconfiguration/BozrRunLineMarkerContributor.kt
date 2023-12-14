package com.ukg.bozr.bozrplugin.runconfiguration

import com.intellij.execution.lineMarker.ExecutorAction
import com.intellij.execution.lineMarker.RunLineMarkerContributor
import com.intellij.icons.AllIcons
import com.intellij.json.psi.JsonArray
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.ukg.bozr.bozrplugin.service.BozrFileService

class BozrRunLineMarkerContributor : RunLineMarkerContributor() {
    private val bozrFileService = BozrFileService.getInstance()

    override fun getInfo(element: PsiElement): Info? =
            element.let { it as? JsonArray }
                    ?.let { it.parent as? PsiFile }
                    ?.takeIf { bozrFileService.isFileBozrTest(it) }
                    ?.let {
                        Info(AllIcons.RunConfigurations.TestState.Run, ExecutorAction.getActions()) {
                            "test"
                        }
                    }
}