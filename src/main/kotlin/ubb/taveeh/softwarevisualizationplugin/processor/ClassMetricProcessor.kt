package ubb.taveeh.softwarevisualizationplugin.processor

import com.intellij.psi.PsiAnonymousClass
import com.intellij.psi.PsiClassOwner
import com.intellij.psi.PsiDeclarationStatement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiTypeParameter
import io.ktor.util.reflect.*
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import ubb.taveeh.softwarevisualizationplugin.processor.classProcessor.*

class ClassMetricProcessor(
    private val psiFile: PsiFile
) {


    companion object {
        private val log: Logger = LogManager.getLogger(ClassMetricProcessor::class)
    }

    private val processors: MutableList<ClassProcessor> = mutableListOf()

    fun init() {
        if (psiFile.isPhysical && psiFile.instanceOf(PsiClassOwner::class)) {
            (psiFile as PsiClassOwner).classes.forEach {
                if (!it.isValid || it.instanceOf(PsiAnonymousClass::class) || it.instanceOf(
                        PsiTypeParameter::class
                    ) || it.parent.instanceOf(PsiDeclarationStatement::class)
                ) {
                    return@forEach
                }

                processors.add(NumberOfChildClasses(psiClass = it))
                processors.add(CouplingBetweenObjectsClassProcessor(psiClass = it))

                if (it.isInterface) {
                    return@forEach
                }
                processors.add(ResponseForClassProcessor(psiClass = it))
                processors.add(LinesOfCodeProcessor(psiClass = it))
                processors.add(NumberOfMethodsProcessor(psiClass = it))
                processors.add(NumberOfClassMembersProcessor(psiClass = it))
                processors.add(DepthOfInheritanceTreeProcessor(psiClass = it))
            }
        }
    }

    fun process(): Map<String, Map<String, Int>> {
        val resultMap: MutableMap<String, MutableMap<String, Int>>  = mutableMapOf()
        processors.forEach {
            log.info("Class ${it.getClass()}: For the metric ${it.getName()} we have metric evaluated as: ${it.process()}\n")
            if (resultMap[it.getClass()] == null) {
                resultMap[it.getClass()] = mutableMapOf()
            }
            resultMap[it.getClass()]?.put(it.getName(), it.process())
        }
        return resultMap
    }
}