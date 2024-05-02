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
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.*

class RunAnalysis2Action(
) : AnAction() {

    companion object {
        private val log: Logger = LogManager.getLogger(RunAnalysis2Action::class)
    }
    override fun actionPerformed(actionEvent: AnActionEvent) {
        BrowserUtil.open("https://www.google.com/");
        val psiFile = Optional.ofNullable<PsiFile>(actionEvent.getData(LangDataKeys.PSI_FILE))

        val project = actionEvent.project!!;
        val scope = ProjectScope.getProjectScope(project);

        psiFile.map { it.fileType }.ifPresent { log.info(it.name) }

        val result = FileTypeIndex.getFiles(psiFile.map { it.fileType }.get(), scope);
        result.forEach {
            log.info(it)
        }
        psiFile.map { it.text }.ifPresent {
            log.info(it)
        }
    }

}