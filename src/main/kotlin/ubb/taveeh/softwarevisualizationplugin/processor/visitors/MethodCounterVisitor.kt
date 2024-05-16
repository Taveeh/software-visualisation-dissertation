package ubb.taveeh.softwarevisualizationplugin.processor.visitors

import com.intellij.psi.JavaRecursiveElementVisitor
import com.intellij.psi.PsiCallExpression
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiMethod

class MethodCounterVisitor(
    private val calledMethods: MutableSet<PsiMethod>
): JavaRecursiveElementVisitor() {
    override fun visitClass(aClass: PsiClass) {
        // do nothing
    }

    override fun visitCallExpression(callExpression: PsiCallExpression) {
        super.visitCallExpression(callExpression)
        callExpression.resolveMethod()?.let {
            calledMethods.add(it)
        }
    }
}