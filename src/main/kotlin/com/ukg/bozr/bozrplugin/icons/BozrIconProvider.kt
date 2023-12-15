package com.ukg.bozr.bozrplugin.icons

import com.intellij.ide.IconProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.ukg.bozr.bozrplugin.service.BozrFileService
import javax.swing.Icon

class BozrIconProvider : IconProvider() {
    private val bozrFileService = BozrFileService.getInstance()

    override fun getIcon(psiElement: PsiElement, flag: Int): Icon? =
        psiElement.let { it as? PsiFile }
            ?.takeIf { bozrFileService.isFileBozrTest(it) }
            ?.let { BozrIcons.BozrIcon }

}