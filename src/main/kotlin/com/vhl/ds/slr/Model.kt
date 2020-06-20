package com.vhl.ds.slr

import org.nield.kotlinstatistics.SimpleRegression
import tornadofx.launch

class Model

@ExperimentalStdlibApi
fun launch() {
    launch<Chartted>()
}

fun <T>Iterable<T>.splitSet(percentage: Double): DataSet<T> {
    val train = this.toList().subList(0, (this.count() * (1 - percentage)).toInt()-1)
    val test = this.toList().subList((this.count() * (1 - percentage)).toInt(), this.count())

    return DataSet(train, test, this.toList())
}

data class DataSet<T>(val train: List<T>, val test: List<T>, val all: List<T>)

data class EmployeeSalary(
    var yearOfExp : Double? = null,
    var salary   : Double? = null
)

fun SimpleRegression.calcYCoordinate(x: Double): Double {
   return this.slope*x + this.intercept
}
