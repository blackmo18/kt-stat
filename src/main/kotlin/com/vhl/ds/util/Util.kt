package com.vhl.ds.util

import com.opencsv.bean.CsvToBeanBuilder
import com.vhl.ds.category.CategorizedData
import koma.create
import koma.matrix.Matrix

inline fun <reified T>dataClassFromCSV(path: String, type: Class<out T>): List<T> {
    val br = T::class.java.getResourceAsStream(path).bufferedReader()
    val csvBeans = CsvToBeanBuilder<T>(br)
        .withType(type)
        .withIgnoreLeadingWhiteSpace(true)
        .build()
    return csvBeans.parse()
}

inline fun <T>List<T>.categorized(crossinline category: (T)-> HashMap<String, Any>, crossinline numeric: (T)-> DoubleArray): List<CategorizedData> {
    val catList = mutableListOf<CategorizedData>()

    forEach {
        val cat = CategorizedData(category(it), numeric(it))
        catList.add(cat)
    }

    return catList
}

fun <T> Matrix<T>.removeColumns(vararg args :Int): Matrix<Double> {

    val rows = numRows()
    val colsMat = mutableListOf<DoubleArray>()
    val outArray = arrayListOf<DoubleArray>()

    for (i in 0 until this.numCols()) {
        if (!args.contains(i)) {
            colsMat.add(this.getCol(i).getDoubleData())
        }
    }

    for (j in 0 until rows) {
        val output = arrayListOf<Double>()
        colsMat.forEach { doubles ->
            output.add(doubles[j])
        }
        outArray.add(output.toDoubleArray())
    }
    return create(outArray.toTypedArray())
}

inline fun categorizeByVariable(action:(map :MutableMap<String, Any>)-> Unit): HashMap<String, Any> {
    val map = HashMap<String, Any>()
    action(map)
    return map
}
