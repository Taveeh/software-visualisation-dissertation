package ubb.taveeh.softwarevisualizationplugin.processor

import com.intellij.psi.*
import io.ktor.util.reflect.*
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import ubb.taveeh.softwarevisualizationplugin.processor.methodProcessor.CyclomaticComplexityMethodProcessor
import ubb.taveeh.softwarevisualizationplugin.processor.methodProcessor.MethodProcessor
import ubb.taveeh.softwarevisualizationplugin.processor.methodProcessor.NumberOfParametersMethodProcessor
import ubb.taveeh.softwarevisualizationplugin.processor.methodProcessor.NumberOfStatementMetricProcessor

class MethodMetricProcessor(
    private val psiFile: PsiFile
) {

    companion object {
        private val log: Logger = LogManager.getLogger(MethodMetricProcessor::class)
    }

    private val processors: MutableList<MethodProcessor> = mutableListOf()
    fun init() {
        if (psiFile.isPhysical && psiFile.instanceOf(PsiClassOwner::class)) {
            (psiFile as PsiClassOwner).classes.forEach {
                if (!it.isValid  || it.instanceOf(PsiAnonymousClass::class) || it.instanceOf(
                        PsiTypeParameter::class) || it.parent.instanceOf(PsiDeclarationStatement::class) || it.isInterface) {
                    return@forEach
                }
                it.allMethods.forEach {method ->
                    processors.add(NumberOfParametersMethodProcessor(method))
                    processors.add(NumberOfStatementMetricProcessor(method))
                    processors.add(CyclomaticComplexityMethodProcessor(method))
                }

            }
        }
    }

    fun process() {
        processors.forEach {
            log.info("Method ${it.getMethod()} has the metric ${it.getName()} evaluated as ${it.process()}")
        }
    }

}