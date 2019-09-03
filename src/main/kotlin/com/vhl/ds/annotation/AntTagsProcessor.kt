package com.practice.ds.annotation

import com.vhl.ds.category.ScientificData
import com.vhl.ds.category.CategorizedData
import kotlin.reflect.full.declaredMemberProperties


fun <T: ScientificData>List<T>.categorizedAnnotatedData(): List<CategorizedData> {
    val categorizedData = mutableListOf<CategorizedData>()
    forEach { entry ->
        val numericalVariables  = mutableListOf<Double>()
        val categoricalVariable = mutableMapOf<String, String>()

        entry.javaClass.kotlin.declaredMemberProperties.forEach { prop ->
            if (prop.annotations.isEmpty()) {
                val data = prop.get(entry) as? Double ?: throw RuntimeException("Variable ${prop.name} is not a Double, or is  a Categorical Variable")
                numericalVariables.add(data)
            } else {
                prop.annotations.forEach {
                    when(it) {
                        is Category -> categoricalVariable[prop.name] = prop.get(entry).toString()
                    }
                }
            }
        }
        val data = CategorizedData(categoricalVariable, numericalVariables.toDoubleArray())
        categorizedData.add(data)
    }
    return categorizedData
}

fun <T: ScientificData>List<T>.getCategoryKeys(): MutableMap<String, MutableSet<String>> {
    val categoryKeys = mutableMapOf<String, MutableSet<String>>()
    forEach { entry ->
        entry.javaClass.kotlin.declaredMemberProperties.forEach { prop ->
            prop.annotations.forEach {
                when(it) {
                    is Category -> if (categoryKeys.containsKey(prop.name)) {
                        val data = prop.get(entry).toString()
                        categoryKeys[prop.name]!!.add(data)
                    }
                    else {
                        val newSet = mutableSetOf<String>()
                        newSet.add(prop.get(entry).toString())
                        categoryKeys[prop.name] = newSet
                    }
                }
            }
        }
    }
    return categoryKeys
}

fun <T: ScientificData>List<T>.getDependentVariables(): DoubleArray {
    val dependentValues = mutableListOf<Double>()
    forEach { entry ->
        entry.javaClass.kotlin.declaredMemberProperties.forEach { prop ->
            prop.annotations.forEach {
                when(it) {
                    is DependentVar -> dependentValues.add(prop.get(entry) as Double)
                }
            }
        }
    }
    return dependentValues.toDoubleArray()
}


