package com.vhl.ds.slr

import com.vhl.ds.util.dataClassFromCSV
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import org.nield.kotlinstatistics.simpleRegression
import tornadofx.*

class Chartted : App(ChartView::class)

class ChartView : View() {

    val listemp = dataClassFromCSV("/Salary.csv", EmployeeSalary::class.java).shuffled()
    private val hSalary = listemp.maxBy{ it -> it.salary!! }
    private val hExperience = listemp.maxBy { it -> it.yearOfExp!! }
    private val dataSet = listemp.splitSet(0.2)
    private val sRegression = dataSet.train.simpleRegression( xSelector = {it.yearOfExp as Number}, ySelector = {it.salary as Number})

    override val root = borderpane {

        val pyh = sRegression.predict(hSalary?.yearOfExp!!)

        val endY = if (pyh > hSalary.salary!!) pyh else hSalary.salary!!
        val endX = hExperience?.yearOfExp

        val xAxis = NumberAxis(0.0, endX!!, endX!!*.1)
        val yAxis = NumberAxis(0.0,endY, endY*.2)

        center = stackpane {

            scatterchart(null, xAxis, yAxis) {
                series("") {
                    dataSet.all.forEach {
                        data(it.yearOfExp, it.salary)
                    }
                }
            }

            linechart(null, xAxis, yAxis) {
                opacity = 0.5
                series("") {

                    val startPoint = XYChart.Data<Number, Number>(0, sRegression.predict(0.0))
                    val endPoint = XYChart.Data<Number, Number>(hSalary.yearOfExp, pyh)

                    data.setAll(startPoint, endPoint)

                }

                series("") {
                   //-- line 20% above regression line
                }

                //-- showing distance from regression line
//                dataSet.all.forEach {
//                    series("") {
//                        data(it.yearOfExp, it.salary)
//                        data(it.yearOfExp, sRegression.predict(it.yearOfExp!!))
//                    }
//                }
            }
        }
    }
}

