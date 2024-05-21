package ubb.taveeh.softwarevisualizationplugin.processor

import javax.swing.table.AbstractTableModel

class ResultsContainer(
    private val results: Map<String, Map<String, Int>>,
    private val analyzedComponents: String
): AbstractTableModel() {
    fun getResultsForElement(element: String): Map<String, Int> {
        return results[element] ?: emptyMap()
    }

    fun getResults(): Map<String, Map<String, Int>> {
        return results
    }

    private fun getResultsForElementAndMetric(element: String, metric: String): Int {
        return results[element]?.get(metric) ?: 0
    }

    private fun getClasses(): Set<String> {
        return results.keys
    }

    private fun getMetrics(): Set<String> {
        return results.values.flatMap { it.keys }.toSet()
    }

    override fun getRowCount(): Int {
        return getClasses().size
    }

    override fun getColumnCount(): Int {
        return getMetrics().size + 1
    }

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any {
        if (columnIndex == 0) {
            return getClasses().elementAt(rowIndex)
        }
        return getResultsForElementAndMetric(getClasses().elementAt(rowIndex), getMetrics().elementAt(columnIndex - 1))
    }

    override fun getColumnName(column: Int): String {
        if (column == 0) {
            return analyzedComponents
        }
        return getMetrics().elementAt(column - 1)
    }




}