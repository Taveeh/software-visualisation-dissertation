package ubb.taveeh.softwarevisualizationplugin.processor.methodProcessor

import com.intellij.psi.PsiMethod

class NumberOfStatementMetricProcessor(psiMethod: PsiMethod): MethodProcessor(psiMethod) {
    override fun process(): Int {
        psiMethod.body?.let {
            println("Method has a body")
            it.statements.forEach {
                println("Statement ---> ${it}")
            }
        }
        return psiMethod.body?.statements?.size ?: 0
    }

    override fun getName(): String = "Number of Statements"
}