package ubb.taveeh.softwarevisualizationplugin.processor.classProcessor

import com.intellij.psi.PsiClass

class NumberOfClassMembersProcessor(psiClass: PsiClass) : ClassProcessor(psiClass = psiClass) {
    override fun process(): Int {
        return psiClass.allFields.size
    }

    override fun getName(): String = "Number of Class Members"
}