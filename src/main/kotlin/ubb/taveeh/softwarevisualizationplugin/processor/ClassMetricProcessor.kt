package ubb.taveeh.softwarevisualizationplugin.processor

import com.intellij.psi.PsiAnonymousClass
import com.intellij.psi.PsiClassOwner
import com.intellij.psi.PsiDeclarationStatement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiTypeParameter
import io.ktor.util.reflect.*
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import ubb.taveeh.softwarevisualizationplugin.processor.classProcessor.ClassProcessor
import ubb.taveeh.softwarevisualizationplugin.processor.classProcessor.LinesOfCodeProcessor
import ubb.taveeh.softwarevisualizationplugin.processor.classProcessor.NumberOfChildClasses
import ubb.taveeh.softwarevisualizationplugin.processor.classProcessor.NumberOfMethodsProcessor

class ClassMetricProcessor(
    private val psiFile: PsiFile
) {


    companion object {
        private val log: Logger = LogManager.getLogger(ClassMetricProcessor::class)
    }

    private val processors: MutableList<ClassProcessor> = mutableListOf()

    fun init() {
        if (psiFile.isPhysical && psiFile.instanceOf(PsiClassOwner::class)) {
            val psiClassOwner =
            (psiFile as PsiClassOwner).classes.forEach {
                if (!it.isValid  || it.instanceOf(PsiAnonymousClass::class) || it.instanceOf(
                        PsiTypeParameter::class) || it.parent.instanceOf(PsiDeclarationStatement::class)) {
                    return@forEach
                }

                processors.add(NumberOfChildClasses(psiClass = it))

                if (it.isInterface) {
                    return@forEach
                }
                processors.add(LinesOfCodeProcessor(psiClass = it))
                processors.add(NumberOfMethodsProcessor(psiClass = it))
            }
        }
    }
    fun process() {
        processors.forEach {
            log.info("Class ${it.getClass()}: For the metric ${it.getName()} we have metric evaluated as: ${it.process()}\n")
        }
    }
}