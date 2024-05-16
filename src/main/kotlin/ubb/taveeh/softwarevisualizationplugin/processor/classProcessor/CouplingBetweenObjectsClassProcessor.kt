package ubb.taveeh.softwarevisualizationplugin.processor.classProcessor

import com.intellij.psi.PsiClass
import io.ktor.util.reflect.*
import ubb.taveeh.softwarevisualizationplugin.utils.DependencyMapUtils

class CouplingBetweenObjectsClassProcessor(psiClass: PsiClass) : ClassProcessor(psiClass = psiClass) {

    companion object {
        val dependencyMapUtils = DependencyMapUtils()
    }
    override fun process(): Int {
        val dependencies = dependencyMapUtils.computeDependencies(psiClass)
        return dependencies.size
    }

    override fun getName(): String = "Coupling Between Objects"
}