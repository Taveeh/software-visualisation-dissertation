package ubb.taveeh.softwarevisualizationplugin.processor

import javax.swing.table.AbstractTableModel

class ResultsContainer(private val results: Map<String, Map<String, Int>>): AbstractTableModel() {
    fun getResults(): Map<String, Map<String, Int>> {
        return results
    }

    fun getResultsForElement(element: String): Map<String, Int> {
        return results[element] ?: emptyMap()
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
        return getMetrics().size
    }

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any {
        return getResultsForElementAndMetric(getClasses().elementAt(rowIndex), getMetrics().elementAt(columnIndex))
    }



}