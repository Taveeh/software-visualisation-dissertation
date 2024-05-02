package ubb.taveeh.softwarevisualizationplugin.actions

import com.intellij.analysis.AnalysisScope
import com.intellij.analysis.BaseAnalysisAction
import com.intellij.ide.BrowserUtil
import com.intellij.openapi.application.ReadAction
import com.intellij.openapi.project.BaseProjectDirectories.Companion.getBaseDirectories
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.testFramework.utils.vfs.getPsiFile
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import ubb.taveeh.softwarevisualizationplugin.processor.ClassMetricProcessor

class MetricAnalysis: BaseAnalysisAction(
    "Analyze Metrics", "Choose Scope for Metric Analysis"
) {

    companion object {
        private val log: Logger = LogManager.getLogger(RunAnalysis2Action::class)
    }
    override fun analyze(project: Project, analysisScope: AnalysisScope) {
        log.info("Something something please display something")
        log.info("Scope ----> " + analysisScope.displayName)
        log.info("Project ----> " + project.name)
        val psiManager: PsiManager = PsiManager.getInstance(project)


        analysisScope.accept {virtualFile ->
            ReadAction.run<Exception> {
                val psiFile: PsiFile = psiManager.findFile(virtualFile) ?: return@run
                if (psiFile.name.endsWith(".class")) {
                    return@run
                }
                log.info(psiFile.name)

                val classMetricProcessor = ClassMetricProcessor(psiFile)
                classMetricProcessor.init()
                classMetricProcessor.process()
            }
            return@accept true;
        }
    }
}