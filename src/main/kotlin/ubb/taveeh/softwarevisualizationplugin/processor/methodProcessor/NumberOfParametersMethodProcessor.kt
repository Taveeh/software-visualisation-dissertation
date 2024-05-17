package ubb.taveeh.softwarevisualizationplugin.processor.methodProcessor

import com.intellij.psi.PsiMethod

class NumberOfParametersMethodProcessor(psiMethod: PsiMethod) : MethodProcessor(psiMethod) {
    override fun process(): Int {
        return psiMethod.parameterList.parametersCount
    }

    override fun getName(): String = "Number of Parameters"
}