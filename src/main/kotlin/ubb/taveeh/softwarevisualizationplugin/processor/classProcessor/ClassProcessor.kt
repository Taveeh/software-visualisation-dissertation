package ubb.taveeh.softwarevisualizationplugin.processor.classProcessor

import com.intellij.psi.PsiClass

open class ClassProcessor(protected val psiClass: PsiClass) {
    open fun process(): Int {
        throw NotImplementedError("Base class should not be called");
    }

    open fun getName(): String = "Base Class Processor"
}