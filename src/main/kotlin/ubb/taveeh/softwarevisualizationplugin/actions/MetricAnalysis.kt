package ubb.taveeh.softwarevisualizationplugin.actions

import com.intellij.analysis.AnalysisScope
import com.intellij.analysis.BaseAnalysisAction
import com.intellij.openapi.application.ReadAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.RegisterToolWindowTask
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ubb.taveeh.softwarevisualizationplugin.display.MetricsToolWindowFactory
import ubb.taveeh.softwarevisualizationplugin.processor.ClassMetricProcessor
import ubb.taveeh.softwarevisualizationplugin.processor.MethodMetricProcessor
import ubb.taveeh.softwarevisualizationplugin.processor.ResultsContainer
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.time.LocalDateTime
import java.util.*

class MetricAnalysis : BaseAnalysisAction(
    "Analyze Metrics", "Choose Scope for Metric Analysis"
) {

    override fun analyze(project: Project, analysisScope: AnalysisScope) {
        println("Something something please display something")
        println("Scope ----> " + analysisScope.displayName)
        println("Project ----> " + project.name)
        val psiManager: PsiManager = PsiManager.getInstance(project)
        val results: MutableMap<String, MutableMap<String, Int>> = mutableMapOf()
        var analyzedComponents = ""
        val uuid = UUID.randomUUID()
        analysisScope.accept { virtualFile ->
            ReadAction.run<Exception> {
                val psiFile: PsiFile = psiManager.findFile(virtualFile) ?: return@run
                if (!psiFile.name.endsWith(".java") && !psiFile.name.endsWith(".kt")) {
                    return@run
                }
                println(psiFile.name)
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

        println("Results: $results")
        val currentTime = LocalDateTime.now()
        val fileName =
            "C:\\Users\\ocustura\\Documents\\Dissertation\\software-visualisation-dissertation\\files\\results-${currentTime.dayOfMonth}_${currentTime.monthValue}-${currentTime.hour}_${currentTime.minute}.json"

        writeResultsToFile(results, fileName)
        runPythonScript(
            "C:\\Users\\ocustura\\Documents\\Dissertation\\software-visualisation-dissertation\\metaphorGeneration\\main.py",
            listOf(fileName)
        )
    }

    private fun writeResultsToFile(results: Map<String, Map<String, Int>>, fileName: String) {

        val file = File(fileName)
        val json = Json.encodeToString(results)
        file.writeText(json)
    }

    private fun runPythonScript(scriptPath: String, args: List<String> = listOf()) {
        val processBuilder = ProcessBuilder("py", scriptPath, *args.toTypedArray())
        val process = processBuilder.start()

        val outputReader = BufferedReader(InputStreamReader(process.inputStream))
        val errorReader = BufferedReader(InputStreamReader(process.errorStream))

        val outputThread = Thread {
            outputReader.lines().forEach { line ->
                println("OUTPUT: $line") // Handle the output as needed
            }
        }

        val errorThread = Thread {
            errorReader.lines().forEach { line ->
                System.err.println("ERROR: $line") // Handle the error output as needed
            }
        }

        outputThread.start()
        errorThread.start()

        val exitCode = process.waitFor()

        outputThread.join()
        errorThread.join()

        if (exitCode != 0) {
            println("Running python script failed with exit code $exitCode")
        } else {
            println("Python script executed successfully")
        }
    }
}