package ubb.taveeh.softwarevisualizationplugin.processor.display

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.table.JBTable
import ubb.taveeh.softwarevisualizationplugin.processor.ResultsContainer

class MetricsToolWindowFactory: ToolWindowFactory {
    private var resultsContainer: ResultsContainer? = null

    fun setResultsContainer(resultsContainer: ResultsContainer) {
        this.resultsContainer = resultsContainer
    }

    override fun createToolWindowContent(p0: Project, p1: ToolWindow) {
        val jbTable = JBTable(resultsContainer)
        println(jbTable)
        val scrollPane = JBScrollPane(jbTable)
        p1.contentManager.addContent(
            p1.contentManager.factory.createContent(scrollPane, "Metrics", false)
        )
    }
}