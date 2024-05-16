package ubb.taveeh.softwarevisualizationplugin.processor.methodProcessor

import com.intellij.psi.PsiMethod

open class MethodProcessor(protected val psiMethod: PsiMethod) {
    open fun process(): Int {
        throw NotImplementedError("Base class should not be called");
    }

    open fun getName(): String = "Base Method Processor"

    open fun getMethod(): String = psiMethod.name
}