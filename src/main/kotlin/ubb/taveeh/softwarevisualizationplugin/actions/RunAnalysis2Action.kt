package ubb.taveeh.softwarevisualizationplugin.actions

import com.intellij.ide.BrowserUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.PsiFile
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.ProjectScope
import com.intellij.ui.PlatformIcons
import com.intellij.util.indexing.FileBasedIndex
import java.util.*

class RunAnalysis2Action(
) : AnAction() {


    override fun actionPerformed(actionEvent: AnActionEvent) {
        BrowserUtil.open("https://www.google.com/");
        val psiFile = Optional.ofNullable<PsiFile>(actionEvent.getData(LangDataKeys.PSI_FILE))

        val project = actionEvent.project!!;
        val scope = ProjectScope.getProjectScope(project);

        psiFile.map { it.fileType }.ifPresent { println(it.name) }

        val result = FileTypeIndex.getFiles(psiFile.map { it.fileType }.get(), scope);
        result.forEach {
            println(it)
        }
        psiFile.map { it.text }.ifPresent {
            println(it)
        }
    }

}