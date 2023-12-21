package com.ukg.bozr.bozrplugin.runconfiguration

import com.intellij.execution.Location
import com.intellij.execution.PsiLocation
import com.intellij.execution.testframework.sm.runner.SMTestLocator
import com.intellij.json.psi.JsonArray
import com.intellij.json.psi.JsonFile
import com.intellij.json.psi.JsonObject
import com.intellij.json.psi.JsonStringLiteral
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager
import com.intellij.psi.search.GlobalSearchScope
import java.nio.file.Paths

class BozrTestLocator(private val testsPath: String?) : SMTestLocator, DumbAware {
    companion object {
        const val TEST_SUITE_PROTOCOL = "bozr:testSuite"
        const val TEST_PROTOCOL = "bozr:test"
    }

    override fun getLocation(
        protocol: String,
        path: String,
        project: Project,
        scope: GlobalSearchScope,
    ): List<Location<PsiElement>> =
        when (protocol) {
            TEST_SUITE_PROTOCOL -> getSuiteLocation(path, project)
            TEST_PROTOCOL -> getTestLocation(path, project)
            else -> emptyList()
        }

    private fun getSuiteLocation(
        path: String,
        project: Project,
    ): List<Location<PsiElement>> {
        if (testsPath == null) return emptyList()
        val vFile = VfsUtil.findFile(Paths.get("${testsPath}/${path}"), false)
        return vFile?.let { PsiManager.getInstance(project).findFile(it) }
            ?.let { PsiLocation.fromPsiElement(it as PsiElement) }?.let { listOf(it) }
            ?: emptyList()
    }

    private fun getTestLocation(
        path: String,
        project: Project,
    ): List<Location<PsiElement>> {
        if (testsPath == null) return emptyList()
        val (suite, test) = path.split("|")
        val vFile = VfsUtil.findFile(Paths.get("${testsPath}/${suite}"), false)
        return vFile?.let { PsiManager.getInstance(project).findFile(it) }
            ?.let { it as? JsonFile }
            ?.let { it.topLevelValue as? JsonArray }
            ?.valueList
            ?.asSequence()
            ?.mapNotNull { it as? JsonObject }
            ?.mapNotNull { it.findProperty("name") }
            ?.mapNotNull { it.value as? JsonStringLiteral }
            ?.filter { it.value.replace(" ", "") == test }
            ?.map { PsiLocation.fromPsiElement(it as PsiElement) }
            ?.toList()
            ?: emptyList()
    }

}