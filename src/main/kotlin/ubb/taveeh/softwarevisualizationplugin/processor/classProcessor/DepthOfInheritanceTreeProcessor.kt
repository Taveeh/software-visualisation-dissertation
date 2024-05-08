package ubb.taveeh.softwarevisualizationplugin.processor.classProcessor

import com.intellij.psi.PsiClass

class DepthOfInheritanceTreeProcessor(psiClass: PsiClass): ClassProcessor(psiClass = psiClass) {

    override fun process(): Int {
        var count = 0
        var currentClass: PsiClass? = psiClass
        while(currentClass != null) {
            count++;

            println(currentClass.superClass)
            println(currentClass.parent)
            currentClass = currentClass.superClass
        }

        return count
    }
    override fun getName(): String = "Depth of Inheritance Tree"
}