package ubb.taveeh.softwarevisualizationplugin.display

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import org.jetbrains.kotlinx.kandy.dsl.plot
import org.jetbrains.kotlinx.kandy.letsplot.export.save
import org.jetbrains.kotlinx.kandy.letsplot.layers.tiles
import ubb.taveeh.softwarevisualizationplugin.processor.ResultsContainer
import java.util.*

class DrawingToolWindowFactory(private val uuid: UUID): ToolWindowFactory {
    private var resultsContainer: ResultsContainer? = null

    fun setResultsContainer(resultsContainer: ResultsContainer) {
        this.resultsContainer = resultsContainer
    }

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        println("Drawing tool window content created")
        plot {
            if (resultsContainer == null) {
                throw IllegalStateException("Results container should not be null")
            }
            val resultsContainer = resultsContainer!!


            tiles {

            }
        }.save("C:\\Users\\ocustura\\Documents\\Dissertation\\Advanced-Programming-Methods\\plot-${uuid}.png") // Save the plot as a JPG file

        val imageViewerPanel = ImageViewerPanel(uuid)
        toolWindow.contentManager.addContent(
            toolWindow.contentManager.factory.createContent(imageViewerPanel, "Visualization", false)
        )
    }
}