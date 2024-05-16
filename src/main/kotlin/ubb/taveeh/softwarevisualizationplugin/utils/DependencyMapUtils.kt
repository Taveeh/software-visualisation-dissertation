package ubb.taveeh.softwarevisualizationplugin.utils

import com.intellij.psi.*
import io.ktor.util.reflect.*

class DependencyMapUtils {

    fun computeDependencies(currentClass: PsiClass): Set<PsiClass> {
        val dependencies: MutableSet<PsiClass> = mutableSetOf();
        dependencies.addAll(computeFieldDependencies(currentClass))
        println("Dependency size fields ---> ${dependencies.size}")

        dependencies.addAll(computeMethodDependencies(currentClass))
        println("Dependency size methods ---> ${dependencies.size}")
        dependencies.forEach {
            println(it.name)
        }
        return dependencies
    }

    private fun computeFieldDependencies(currentClass: PsiClass): Set<PsiClass> {
        val dependencies: MutableSet<PsiClass> = mutableSetOf();
        dependencies.addAll(currentClass.allFields.map {
            addDependencyForType(dependencies, it.type, currentClass)
        }.flatten())

        return dependencies;
    }

    private fun computeMethodDependencies(currentClass: PsiClass): Set<PsiClass> {
        val dependencies: MutableSet<PsiClass> = mutableSetOf();

        dependencies.addAll(currentClass.allMethods.map {
            computeDependenciesForMethod(dependencies, it, currentClass)
        }.flatten())

        return dependencies
    }

    private fun computeDependenciesForMethod(
        dependencies: Set<PsiClass>,
        method: PsiMethod,
        currentClass: PsiClass
    ): Set<PsiClass> {
        val currentDependencies = dependencies.toMutableSet()
        method.returnType
            ?.let {
                addDependencyForType(
                    currentDependencies,
                    it, currentClass
                )
            }?.let { currentDependencies.addAll(it) }
        currentDependencies.addAll(
            method.parameterList.parameters.map {
                addDependencyForType(currentDependencies, it.type, currentClass)
            }.flatten()
        )

        currentDependencies.addAll(
            method.throwsList.referencedTypes.map {
                addDependencyForType(currentDependencies, it, currentClass)
            }.flatten()
        )

        method.body?.statements?.map {
            if (it.instanceOf(PsiExpressionStatement::class)) {
                addDependenciesForExpression(
                    currentDependencies,
                    (it as PsiExpressionStatement).expression,
                    currentClass
                )
            }
            setOf<PsiClass>()
        }?.let {
            currentDependencies.addAll(
                it.flatten()
            )
        }
        return currentDependencies
    }

    private fun addDependenciesForExpression(
        dependencies: Set<PsiClass>,
        expression: PsiExpression,
        currentClass: PsiClass
    ): Set<PsiClass> {
        val currentDependencies = dependencies.toMutableSet()
        if (expression.instanceOf(PsiMethodCallExpression::class)) {
            currentDependencies.addAll(
                addDependencyForMethodCallExpression(
                    currentDependencies,
                    expression as PsiMethodCallExpression,
                    currentClass
                )
            )
        } else if (expression.instanceOf(PsiNewExpression::class)) {
            currentDependencies.addAll(
                addDependencyForNewExpression(
                    currentDependencies,
                    expression as PsiNewExpression,
                    currentClass
                )
            )
        } else if (expression.instanceOf(PsiAssignmentExpression::class)) {
            currentDependencies.addAll(
                addDependencyForAssignmentExpression(
                    currentDependencies,
                    expression as PsiAssignmentExpression,
                    currentClass
                )
            )
        } else if (expression.instanceOf(PsiReferenceExpression::class)) {
            currentDependencies.addAll(
                addDependencyForReferenceExpression(
                    currentDependencies,
                    expression as PsiReferenceExpression,
                    currentClass
                )
            )
        } else if (expression.instanceOf(PsiClassObjectAccessExpression::class)) {
            currentDependencies.addAll(
                addDependencyForClassObjectAccessExpression(
                    currentDependencies,
                    expression as PsiClassObjectAccessExpression,
                    currentClass
                )
            )
        } else if (expression.instanceOf(PsiInstanceOfExpression::class)) {
            currentDependencies.addAll(
                addDependencyForInstanceOfExpression(
                    currentDependencies,
                    expression as PsiInstanceOfExpression,
                    currentClass
                )
            )
        } else if (expression.instanceOf(PsiTypeCastExpression::class)) {
            currentDependencies.addAll(
                addDependencyForTypeCastExpression(
                    currentDependencies,
                    expression as PsiTypeCastExpression,
                    currentClass
                )
            )
        } else if (expression.instanceOf(PsiLambdaExpression::class)) {
            currentDependencies.addAll(
                addDependencyForLambdaExpression(
                    currentDependencies,
                    expression as PsiLambdaExpression,
                    currentClass
                )
            )
        } else {
            println("Some other type of expression ---> ")
        }

        return currentDependencies
    }


    private fun addDependencyForMethodCallExpression(
        dependencies: Set<PsiClass>,
        psiExpression: PsiMethodCallExpression,
        currentClass: PsiClass
    ): Set<PsiClass> {
        val currentDependencies = dependencies.toMutableSet()
        val method = psiExpression.resolveMethod() ?: return currentDependencies

        method.containingClass?.let {
            addDependencyForClass(
                currentDependencies,
                it,
                currentClass
            )
        }?.let {
            currentDependencies.addAll(it)
        }
        return currentDependencies
    }

    private fun addDependencyForReferenceExpression(
        dependencies: Set<PsiClass>,
        psiExpression: PsiReferenceExpression,
        currentClass: PsiClass
    ): Set<PsiClass> {
        val currentDependencies = dependencies.toMutableSet()
        val element = psiExpression.resolve() ?: return currentDependencies

        (if (element.instanceOf(PsiField::class)) {
            (element as PsiField).containingClass?.let {
                addDependencyForClass(currentDependencies, it, currentClass)
            }
        } else if (element.instanceOf(PsiClass::class)) {
            addDependencyForClass(
                currentDependencies,
                element as PsiClass,
                currentClass
            )
        } else {
            setOf()
        })?.let {
            currentDependencies.addAll(
                it
            )
        }
        return currentDependencies
    }

    private fun addDependencyForLambdaExpression(
        dependencies: Set<PsiClass>,
        psiExpression: PsiLambdaExpression,
        currentClass: PsiClass
    ): Set<PsiClass> {
        val currentDependencies = dependencies.toMutableSet()
        psiExpression.functionalInterfaceType
            ?.let { addDependencyForType(currentDependencies, it, currentClass) }
            ?.let { currentDependencies.addAll(it) }

        psiExpression.body?.children?.map {
            if (it.instanceOf(PsiExpressionStatement::class)) {
                addDependenciesForExpression(dependencies, (it as PsiExpressionStatement).expression, currentClass)
            }
            setOf<PsiClass>()
        }?.let {
            currentDependencies.addAll(
                it.flatten()
            )
        }

        return currentDependencies
    }

    private fun addDependencyForTypeCastExpression(
        dependencies: Set<PsiClass>,
        psiExpression: PsiTypeCastExpression,
        currentClass: PsiClass
    ): Set<PsiClass> {
        val currentDependencies = dependencies.toMutableSet()

        psiExpression.type?.let {
            addDependencyForType(dependencies, it, currentClass)
        }?.let {
            currentDependencies.addAll(it)
        }

        psiExpression.operand?.let {
            addDependenciesForExpression(dependencies, it, currentClass)
        }?.let {
            currentDependencies.addAll(it)
        }

        return currentDependencies
    }

    private fun addDependencyForInstanceOfExpression(
        dependencies: Set<PsiClass>,
        psiExpression: PsiInstanceOfExpression,
        currentClass: PsiClass
    ): Set<PsiClass> {
        val currentDependencies = dependencies.toMutableSet()

        psiExpression.type?.let {
            addDependencyForType(dependencies, it, currentClass)
        }?.let {
            currentDependencies.addAll(it)
        }

        currentDependencies.addAll(
            addDependenciesForExpression(dependencies, psiExpression.operand, currentClass)
        )

        return currentDependencies
    }

    private fun addDependencyForClassObjectAccessExpression(
        dependencies: Set<PsiClass>,
        psiExpression: PsiClassObjectAccessExpression,
        currentClass: PsiClass
    ): Set<PsiClass> {
        val currentDependencies = dependencies.toMutableSet()
        currentDependencies.addAll(addDependencyForType(currentDependencies, psiExpression.operand.type, currentClass))
        return currentDependencies
    }

    private fun addDependencyForNewExpression(
        dependencies: Set<PsiClass>,
        psiExpression: PsiNewExpression,
        currentClass: PsiClass
    ): Set<PsiClass> {
        val currentDependencies = dependencies.toMutableSet()

        psiExpression.type?.let {
            addDependencyForType(currentDependencies, it, currentClass)
        }?.let {
            currentDependencies.addAll(it)
        }

        currentDependencies.addAll(psiExpression.typeArguments.map {
            addDependencyForType(currentDependencies, it, currentClass)
        }.flatten())

        return currentDependencies
    }

    private fun addDependencyForAssignmentExpression(
        dependencies: Set<PsiClass>,
        psiExpression: PsiAssignmentExpression,
        currentClass: PsiClass
    ): Set<PsiClass> {
        val currentDependencies = dependencies.toMutableSet()

        psiExpression.type?.let {
            addDependencyForType(currentDependencies,
                it, currentClass)
        }?.let { currentDependencies.addAll(it) }

        currentDependencies.addAll(addDependenciesForExpression(currentDependencies, psiExpression.lExpression, currentClass))
        psiExpression.rExpression?.let {
            addDependenciesForExpression(currentDependencies,
                it, currentClass)
        }?.let { currentDependencies.addAll(it) }
        return currentDependencies
    }

    private fun addDependencyForType(
        dependencies: Set<PsiClass>,
        type: PsiType,
        currentClass: PsiClass
    ): Set<PsiClass> {
        val currentDependencies = dependencies.toMutableSet()
        if (!type.instanceOf(PsiClassType::class)) {
            if (type.instanceOf(PsiWildcardType::class)) {
                (type as PsiWildcardType).bound
                    ?.let {
                        addDependencyForType(currentDependencies, it, currentClass)
                    }
                    ?.let { currentDependencies.addAll(it) }
            }
            return dependencies
        }
        val classType = type as PsiClassType

        currentDependencies.addAll(classType.parameters.map {
            addDependencyForType(dependencies, it, currentClass)
        }.flatten())
        classType.resolve()?.let {
            addDependencyForClass(currentDependencies, it, currentClass)
        }?.let {
            currentDependencies.addAll(it)
        }

        return currentDependencies
    }

    private fun addDependencyForClass(
        dependencies: Set<PsiClass>,
        classType: PsiClass,
        currentClass: PsiClass
    ): Set<PsiClass> {
        if (classType == currentClass) {
            return dependencies
        }
        if (classType.instanceOf(PsiCompiledElement::class) || classType.instanceOf(PsiAnonymousClass::class) || classType.instanceOf(
                PsiTypeParameter::class
            )
        ) {
            return dependencies
        }

        val currentDependencies = dependencies.toMutableSet()

        currentDependencies.add(classType)
        return currentDependencies
    }
}