package ubb.taveeh.softwarevisualizationplugin.processor.classProcessor

import com.intellij.psi.PsiClass
import com.intellij.psi.PsiMethod
import ubb.taveeh.softwarevisualizationplugin.processor.visitors.MethodCounterVisitor

class ResponseForClassProcessor(psiClass: PsiClass) : ClassProcessor(psiClass = psiClass) {

    override fun process(): Int {
        val calledMethods: MutableSet<PsiMethod> = mutableSetOf()

        calledMethods.addAll(
            psiClass.allMethods
        )

        psiClass.acceptChildren(MethodCounterVisitor(calledMethods))
        calledMethods.addAll(psiClass.allMethods)
        calledMethods.forEach {
            println("Called Method -----> ${it.name}")
        }
        return calledMethods.size
    }

    override fun getName() = "Response for Class"
}