package ubb.taveeh.softwarevisualizationplugin.processor.visitors

import com.intellij.psi.*
import io.ktor.util.reflect.*

class MethodStatementsCounterVisitor: JavaRecursiveElementVisitor() {
    var statementsCount: Int = 0

    override fun visitStatement(statement: PsiStatement) {
        super.visitStatement(statement)
        println("Statement ---> ${statement.text}")
        statementsCount++
    }

    override fun visitLambdaExpression(expression: PsiLambdaExpression) {
        super.visitLambdaExpression(expression)
        println("Lambda Expression ---> ${expression.text}")
        statementsCount++

    }

}