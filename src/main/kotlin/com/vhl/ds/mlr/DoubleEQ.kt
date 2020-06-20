package com.vhl.ds.mlr

import com.vhl.ds.category.CategorizedData
import koma.create
import koma.zeros

class DoubleEQ(private val categories: Map<String, Set<String>>) {

    private val categoryHolder = mutableMapOf<String, DoubleArray>()
    private val categoryIndexed = mutableMapOf<String, Map<String, Int>>()
    private val coefficients = mutableSetOf<String>()

    fun createEQ(listCategorizedData: List<CategorizedData>): Array<DoubleArray> {

        val sizey = listCategorizedData.size
        var sizex = 0

        categories.keys.forEach { key ->
            val map = mutableMapOf<String, Int>()
            var count = 0
            val cat = (categories[key] ?: error("Category Key \"$key\" not found in data set"))
                .sorted()
                .takeIf { it.size> 1 }
                ?.let { it.subList(1, it.size) }

            if (cat != null) {
                cat.forEach {
                    coefficients.add(it)
                    map[it] = count++
                }
                sizex += cat.size
                categoryIndexed[key] = map
                categoryHolder[key] = DoubleArray(cat.size)
            }
        }
        sizex += listCategorizedData[0].dobs.size

        val kMat = zeros(sizey, sizex)
        val eqDob = DoubleArray(0)

        listCategorizedData.withIndex().forEach { (count, data) ->
            var flatMat = eqDob.copyOf()
            for (key in data.category.keys) {
                val copDob = categoryHolder[key]!!.copyOf()
                val indexFrom = categoryIndexed[key]
                indexFrom?.get(data.category[key])?.run {
                    copDob[this] = 1.0
                }
                flatMat += copDob
            }
            flatMat += data.dobs
            kMat.setRow(count, create(flatMat))
        }
        return kMat.to2DArray()
    }
}