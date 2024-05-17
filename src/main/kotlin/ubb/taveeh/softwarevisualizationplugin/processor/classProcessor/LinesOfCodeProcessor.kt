package ubb.taveeh.softwarevisualizationplugin.processor.classProcessor

import com.intellij.psi.PsiClass
import com.intellij.psi.PsiClassOwner
import com.intellij.psi.PsiFile
import io.ktor.util.reflect.*
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import ubb.taveeh.softwarevisualizationplugin.LineUtils
import ubb.taveeh.softwarevisualizationplugin.processor.ClassMetricProcessor

class LinesOfCodeProcessor(psiClass: PsiClass) : ClassProcessor(psiClass = psiClass) {
    override fun process(): Int {
        var linesOfCode = LineUtils().countLines(psiClass.text)

        psiClass.innerClasses.forEach {
            linesOfCode -= LineUtils().countLines(it.text)
        }
        return linesOfCode
    }

    override fun getName(): String = "Lines of Code"
}