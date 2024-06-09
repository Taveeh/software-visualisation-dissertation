package ubb.taveeh.softwarevisualizationplugin.processor.visitors

import com.intellij.psi.*

class MethodCounterVisitor(
    private val calledMethods: MutableSet<PsiMethod>
) : JavaRecursiveElementVisitor() {
    override fun visitClass(aClass: PsiClass) {
        // do nothing
    }

    override fun visitCallExpression(callExpression: PsiCallExpression) {
        super.visitCallExpression(callExpression)
        print("Call expression: ${callExpression.text}\n")
        callExpression.resolveMethod()?.let {
            calledMethods.add(it)
        }
    }

    override fun visitLambdaExpression(expression: PsiLambdaExpression) {
        super.visitLambdaExpression(expression)
        expression.body?.accept(this)
    }
}