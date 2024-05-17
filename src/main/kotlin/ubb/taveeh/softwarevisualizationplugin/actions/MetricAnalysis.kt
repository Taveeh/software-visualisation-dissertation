package ubb.taveeh.softwarevisualizationplugin.actions

import com.intellij.analysis.AnalysisScope
import com.intellij.analysis.BaseAnalysisAction
import com.intellij.ide.BrowserUtil
import com.intellij.openapi.application.ReadAction
import com.intellij.openapi.project.BaseProjectDirectories.Companion.getBaseDirectories
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.RegisterToolWindowTask
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.testFramework.utils.vfs.getPsiFile
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import ubb.taveeh.softwarevisualizationplugin.processor.ClassMetricProcessor
import ubb.taveeh.softwarevisualizationplugin.processor.MethodMetricProcessor
import ubb.taveeh.softwarevisualizationplugin.processor.ResultsContainer
import ubb.taveeh.softwarevisualizationplugin.processor.display.MetricsToolWindowFactory

class MetricAnalysis : BaseAnalysisAction(
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
        val results: MutableMap<String, MutableMap<String, Int>> = mutableMapOf()
        var analyzedComponents = ""
        analysisScope.accept { virtualFile ->
            ReadAction.run<Exception> {
                val psiFile: PsiFile = psiManager.findFile(virtualFile) ?: return@run
                if (!psiFile.name.endsWith(".java") && !psiFile.name.endsWith(".kt")) {
                    return@run
                }
                log.info(psiFile.name)
                val classResults = if (analysisScope.scopeType == AnalysisScope.PROJECT) {
                    analyzedComponents = "Class"
                    val classMetricProcessor = ClassMetricProcessor(psiFile)
                    classMetricProcessor.init()
                    classMetricProcessor.process()
                } else if (analysisScope.scopeType == AnalysisScope.FILE) {
                    analyzedComponents = "Method"
                    val methodMetricProcessor = MethodMetricProcessor(psiFile)
                    methodMetricProcessor.init()
                    methodMetricProcessor.process()
                } else {
                    mapOf()
                }


                classResults.forEach {
                    val className = it.key
                    if (!results.containsKey(className)) {
                        results[className] = mutableMapOf()
                    }
                    results[className]?.putAll(it.value)
                }

            }
            return@accept true;
        }
        val toolwindow: ToolWindow = try {
            ToolWindowManager.getInstance(project).registerToolWindow(RegisterToolWindowTask("SoftwareMetrics"))
        } catch (e: Exception) {
            ToolWindowManager.getInstance(project).getToolWindow("SoftwareMetrics")!!
        }
        val metricsToolWindowFactory = MetricsToolWindowFactory()
        metricsToolWindowFactory.setResultsContainer(ResultsContainer(results, analyzedComponents))
        metricsToolWindowFactory.createToolWindowContent(project, toolwindow)
    }
}