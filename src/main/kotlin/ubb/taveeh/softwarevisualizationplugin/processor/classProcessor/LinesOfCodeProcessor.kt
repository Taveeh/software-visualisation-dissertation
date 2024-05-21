package ubb.taveeh.softwarevisualizationplugin.processor.classProcessor

import com.intellij.psi.PsiClass
import ubb.taveeh.softwarevisualizationplugin.utils.LineUtils

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