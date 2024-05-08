package ubb.taveeh.softwarevisualizationplugin.processor.classProcessor

import com.intellij.psi.PsiClass
import com.intellij.psi.search.searches.ClassInheritorsSearch

class NumberOfChildClasses(psiClass: PsiClass) : ClassProcessor(psiClass) {

    override fun process(): Int {
        val childrenOfClass = ClassInheritorsSearch.search(psiClass).findAll()
        childrenOfClass.forEach {
            println(it.text)
        }
        return childrenOfClass.size
    }

    override fun getName(): String = "Number of Child Classes"
}