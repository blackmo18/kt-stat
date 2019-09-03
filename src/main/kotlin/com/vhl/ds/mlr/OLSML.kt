package com.vhl.ds.mlr

import org.apache.commons.math3.distribution.TDistribution
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression
import kotlin.math.abs

class OLSML(y: DoubleArray, x: Array<out DoubleArray>) {


   private val multiLinearRegression = OLSMultipleLinearRegression()
    var betas           : DoubleArray
    var residual        : DoubleArray
    var pValue          : DoubleArray
    var standardErrors  : DoubleArray
    var tStat           : DoubleArray
    var tDist           : TDistribution

    init {
        multiLinearRegression.newSampleData(y, x)
        betas = multiLinearRegression.estimateRegressionParameters()
        residual = multiLinearRegression.estimateResiduals()
        standardErrors = multiLinearRegression.estimateRegressionParametersStandardErrors()

        //todo: check if the residual size and  beta( total number of coefficients are equal
        val residualDF = residual.size - betas.size
        tDist = TDistribution(residualDF.toDouble())

        var arrOB = DoubleArray(betas.size)
        tStat = arrOB.copyOf()

        pValue = arrOB.copyOf().also {
            betas.forEachIndexed { index, value ->
                tStat[index] = value / standardErrors[index]
                it[index] = tDist.cumulativeProbability(-abs(tStat[index])) * 2
            }
        }
    }

    fun summary(): String {
        var summary = ""
        betas.forEachIndexed { index, value ->
           summary += "\n x$index || coefficient: $value, stdE: ${standardErrors[index]}, tStat: ${tStat[index]}, pValue: ${pValue[index]} "
        }
        return summary
    }
}