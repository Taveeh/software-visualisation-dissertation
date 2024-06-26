package ubb.taveeh.softwarevisualizationplugin.processor

import com.intellij.psi.*
import io.ktor.util.reflect.*
import ubb.taveeh.softwarevisualizationplugin.processor.methodProcessor.CyclomaticComplexityMethodProcessor
import ubb.taveeh.softwarevisualizationplugin.processor.methodProcessor.MethodProcessor
import ubb.taveeh.softwarevisualizationplugin.processor.methodProcessor.NumberOfParametersMethodProcessor
import ubb.taveeh.softwarevisualizationplugin.processor.methodProcessor.NumberOfStatementMetricProcessor
import org.jetbrains.kotlin.asJava.elements.KtLightMethod
class MethodMetricProcessor(
    private val psiFile: PsiFile
) {


    /**
     * it.methods.map { met ->
     *     if (met.instanceOf(KtLightMethod::class)) {
     *         val kt = (met as KtLightMethod).kotlinOrigin
     *         if (kt is KtNamedFunction) {
     *             val be = kt.bodyExpression
     *             if (be is KtBlockExpression) {
     *                 be.statements.map { stmt ->
     *                     stmt.
     *                 }
     *             } else null
     *         } else null
     *     } else {
     *         null
     *     }
     * }
     */
    private val processors: MutableList<MethodProcessor> = mutableListOf()
    fun init() {
        if (psiFile.isPhysical && psiFile.instanceOf(PsiClassOwner::class)) {
            (psiFile as PsiClassOwner).classes.forEach {
                if (!it.isValid || it.instanceOf(PsiAnonymousClass::class) || it.instanceOf(
                        PsiTypeParameter::class
                    ) || it.parent.instanceOf(PsiDeclarationStatement::class) || it.isInterface
                ) {
                    return@forEach
                }

                it.methods.forEach { method ->
                    processors.add(NumberOfParametersMethodProcessor(method))
                    processors.add(NumberOfStatementMetricProcessor(method))
                    processors.add(CyclomaticComplexityMethodProcessor(method))
                }

            }
        }
    }

    fun process(): Map<String, Map<String, Int>> {
        val resultMap: MutableMap<String, MutableMap<String, Int>> = mutableMapOf()
        processors.forEach {
            println("Method ${it.getMethod()}: For the metric ${it.getName()} we have metric evaluated as: ${it.process()}\n")
            if (resultMap[it.getMethod()] == null) {
                resultMap[it.getMethod()] = mutableMapOf()
            }
            resultMap[it.getMethod()]?.put(it.getName(), it.process())
        }

        return resultMap
    }

}