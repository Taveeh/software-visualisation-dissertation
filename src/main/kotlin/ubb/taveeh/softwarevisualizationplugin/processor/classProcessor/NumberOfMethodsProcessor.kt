package ubb.taveeh.softwarevisualizationplugin.processor.classProcessor

import com.intellij.psi.PsiClass

class NumberOfMethodsProcessor(psiClass: PsiClass) : ClassProcessor(psiClass = psiClass) {
    override fun process(): Int {
        return psiClass.allMethods.size
    }

    override fun getName(): String = "Number of Methods"
}