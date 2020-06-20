package com.vhl.ds.util

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.vhl.blackmo.grass.dsl.grass
import com.vhl.ds.category.CategorizedData
import koma.create
import koma.matrix.Matrix

@ExperimentalStdlibApi
inline fun <reified T>dataClassFromCsv(path: String): List<T> {
    val start = System.currentTimeMillis()
    val file = String::class.java.getResourceAsStream(path)
    val contents  = csvReader().readAllWithHeader(file)
    return   grass<T>().harvest(contents)
}

inline fun <T>List<T>.categorized(crossinline category: (T)-> HashMap<String, Any>, crossinline numeric: (T)-> DoubleArray): List<CategorizedData> {
    val catList = mutableListOf<CategorizedData>()

    forEach {
        val cat = CategorizedData(category(it), numeric(it))
        catList.add(cat)
    }

    return catList
}

/**
 * remove specific index of columns
 */
fun <T> Matrix<T>.removeColumns(vararg args :Int): Matrix<Double> {

    val rows = numRows()
    val colsMat = mutableListOf<DoubleArray>()
    val outArray = arrayListOf<DoubleArray>()

    (0 until this.numCols())
        .filterNot { args.contains(it) }
        .mapTo(colsMat) { this.getCol(it).getDoubleData() }

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
