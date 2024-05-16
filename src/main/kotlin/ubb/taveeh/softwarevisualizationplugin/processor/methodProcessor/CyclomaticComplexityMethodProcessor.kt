package ubb.taveeh.softwarevisualizationplugin.processor.methodProcessor

import com.intellij.psi.PsiMethod
import ubb.taveeh.softwarevisualizationplugin.processor.visitors.MethodComplexityVisitor

class CyclomaticComplexityMethodProcessor (psiMethod: PsiMethod): MethodProcessor(psiMethod) {
    override fun process(): Int {
        val complexityVisitor = MethodComplexityVisitor()
        psiMethod.accept(complexityVisitor)
        return complexityVisitor.complexity
    }

    override fun getName(): String = "Cyclomatic Complexity"
}