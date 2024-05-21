package ubb.taveeh.softwarevisualizationplugin.utils

class LineUtils {
    fun countLines(text: String?): Int {
        if (text == null) {
            return 0
        }
        var lines = 0
        var onEmptyLine = true
        val chars = text.toCharArray()
        for (aChar in chars) {
            if (aChar == '\n' || aChar == '\r') {
                if (!onEmptyLine) {
                    lines++
                    onEmptyLine = true
                }
            } else if (aChar != ' ' && aChar != '\t') {
                onEmptyLine = false
            }
        }
        if (!onEmptyLine) {
            lines++
        }
        return lines
    }
}