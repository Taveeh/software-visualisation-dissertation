package ubb.taveeh.softwarevisualizationplugin.display

import java.awt.BorderLayout
import java.util.UUID
import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.JPanel

class ImageViewerPanel(uuid: UUID): JPanel() {
    private var imageLabel: JLabel? = null

    init {
        layout = BorderLayout()
        imageLabel = JLabel()
        imageLabel?.let { add(it, BorderLayout.CENTER) }

        val imageIcon = ImageIcon("C:\\Users\\ocustura\\Documents\\Dissertation\\Advanced-Programming-Methods\\plot-${uuid}.png")
        imageLabel?.icon = imageIcon
    }
}