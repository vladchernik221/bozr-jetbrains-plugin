package com.ukg.bozr.bozrplugin.service

import com.intellij.json.psi.JsonArray
import com.intellij.json.psi.JsonFile
import com.intellij.json.psi.JsonObject
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.psi.PsiFile

@Service
class BozrFileService {
    companion object {
        @JvmStatic
        fun getInstance(): BozrFileService = service()
    }

    fun isFileBozrTest(file: PsiFile): Boolean =
            file.let { it as? JsonFile }
                    ?.let { it.topLevelValue as? JsonArray }
                    ?.valueList
                    ?.asSequence()
                    ?.mapNotNull { it as? JsonObject }
                    ?.any { it.findProperty("calls") != null } ?: false
}