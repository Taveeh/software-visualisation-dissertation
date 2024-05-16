package ubb.taveeh.softwarevisualizationplugin.processor.visitors

import com.intellij.psi.*
import io.ktor.util.reflect.*

class MethodComplexityVisitor: JavaRecursiveElementWalkingVisitor() {
    var complexity: Int = 1
    override fun visitForStatement(statement: PsiForStatement) {
        super.visitForStatement(statement)
        complexity++
    }

    override fun visitForeachStatement(statement: PsiForeachStatement) {
        super.visitForeachStatement(statement)
        complexity++
    }

    override fun visitForeachPatternStatement(statement: PsiForeachPatternStatement) {
        super.visitForeachPatternStatement(statement)
        complexity++
    }

    override fun visitIfStatement(statement: PsiIfStatement) {
        super.visitIfStatement(statement)
        complexity++
    }

    override fun visitDoWhileStatement(statement: PsiDoWhileStatement) {
        super.visitDoWhileStatement(statement)
        complexity++
    }

    override fun visitConditionalExpression(expression: PsiConditionalExpression) {
        super.visitConditionalExpression(expression)
        complexity++
    }

    override fun visitWhileStatement(statement: PsiWhileStatement) {
        super.visitWhileStatement(statement)
        complexity++
    }

    override fun visitSwitchStatement(statement: PsiSwitchStatement) {
        super.visitSwitchStatement(statement)
        visitSwitch(statement)
    }

    override fun visitSwitchExpression(expression: PsiSwitchExpression) {
        super.visitSwitchExpression(expression)
        visitSwitch(expression)
    }

    private fun visitSwitch(switchBlock: PsiSwitchBlock) {
        switchBlock.body?.let {
            it.statements.forEach {
                if (it.instanceOf(PsiSwitchLabelStatement::class) || it.instanceOf(PsiSwitchLabeledRuleStatement::class)) {
                    complexity++
                }
            }
        }
    }
}