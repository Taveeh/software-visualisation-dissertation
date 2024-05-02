package ubb.taveeh.softwarevisualizationplugin.actions;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class RunAnalysisAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        System.out.println("BlaBlaBla Started");
        BrowserUtil.browse("https://www.google.com");

        Optional<PsiFile> psiFile = Optional.ofNullable(event.getData(LangDataKeys.PSI_FILE));

        psiFile.map(PsiElement::getText).ifPresent(it -> System.out.println(it));
    }
}
