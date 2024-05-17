package ubb.taveeh.softwarevisualizationplugin.processor.methodProcessor

import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiReturnStatement
import io.ktor.util.reflect.*
import org.jetbrains.kotlin.asJava.elements.KtLightMethod
import org.jetbrains.kotlin.psi.KtBlockExpression
import org.jetbrains.kotlin.psi.KtNamedFunction
import ubb.taveeh.softwarevisualizationplugin.processor.visitors.MethodStatementsCounterVisitor

class NumberOfStatementMetricProcessor(psiMethod: PsiMethod) : MethodProcessor(psiMethod) {
    override fun process(): Int {
//        when (psiMethod) {
//            is KtLightMethod -> {
//                val kotlinOrigin = psiMethod.kotlinOrigin
//                if (kotlinOrigin is KtNamedFunction) {
//                    val bodyExpression = kotlinOrigin.bodyExpression
//                    if (bodyExpression is KtBlockExpression) {
//
//                    }
//                }
//            }
//        }
        val methodStatementsCounterVisitor = MethodStatementsCounterVisitor()
        psiMethod.accept(methodStatementsCounterVisitor)
        return methodStatementsCounterVisitor.statementsCount
    }

    override fun getName(): String = "Number of Statements"
}