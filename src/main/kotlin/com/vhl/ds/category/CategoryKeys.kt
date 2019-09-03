package com.vhl.ds.category

import com.practice.ds.annotation.Category
import com.practice.ds.annotation.DependentVar
import kotlin.reflect.full.declaredMemberProperties

class CategoryKeys<T: ScientificData>(private val dataList: List<T>) {
    val mappedKeys = HashMap<String, Set<String>>()
    private val dependentValues = mutableListOf<Double>()
    private val categoryKeys = mutableMapOf<String, MutableSet<String>>()
    private val numericKeys = mutableSetOf<String>()
    private val categorizedData = mutableListOf<CategorizedData>()

    fun addCategory(key: String, cat: (T)-> String): CategoryKeys<T> {
        mappedKeys[key] = dataList.map(cat).toSet()
        return this
    }

    fun initCategoryData(): CategoryKeys<T> {
        dataList.categorizedAnnotatedData()
        return this
    }

    fun getDependentValues() = dependentValues.toDoubleArray()
    fun getCategorizedData() = categorizedData

    /**
     * todo:list down numeric and category variable for identification
     * for easily identifying which column to be removed
     */
    fun getCategoryKeys() = categoryKeys
    fun getNumericKeys() = numericKeys

    private fun <T: ScientificData>List<T>.categorizedAnnotatedData(): List<CategorizedData> {
        forEach { entry ->
            val numericalVariables  = mutableListOf<Double>()
            val categoricalVariable = mutableMapOf<String, String>()

            entry.javaClass.kotlin.declaredMemberProperties.forEach { prop ->
                if (prop.annotations.isEmpty()) {
                    val data = prop.get(entry) as? Double ?: throw RuntimeException("Variable ${prop.name} is not a Double, or is  a Categorical Variable")
                    numericKeys.add(prop.name)
                    numericalVariables.add(data)
                } else {
                    prop.annotations.forEach {
                        when(it) {
                            is Category -> {
                                categoricalVariable[prop.name] =  prop.get(entry).toString()
                                if (categoryKeys.containsKey(prop.name)) {
                                    val data = prop.get(entry).toString()
                                    categoryKeys[prop.name]!!.add(data)
                                }
                                else {
                                    val newSet = mutableSetOf<String>()
                                    newSet.add(prop.get(entry).toString())
                                    categoryKeys[prop.name] = newSet
                                }
                            }
                            is DependentVar -> dependentValues.add(prop.get(entry) as Double)
                        }
                    }
                }
            }
            val data = CategorizedData(categoricalVariable, numericalVariables.toDoubleArray())
            categorizedData.add(data)
        }
        return categorizedData
    }
}
