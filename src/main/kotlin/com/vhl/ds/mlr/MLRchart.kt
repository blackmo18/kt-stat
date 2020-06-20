package com.vhl.ds.mlr

import com.vhl.ds.model.Company
import com.vhl.ds.util.dataClassFromCsv
import javafx.scene.chart.NumberAxis
import tornadofx.*

@ExperimentalStdlibApi
class GraphView: View() {

    val dataSet = dataClassFromCsv<Company>("/Company.csv")
    override val root = borderpane {

        val xAxis  = NumberAxis(0.0,200000.0, 10000.0 )
        val yAxis  = NumberAxis(0.0,200000.0, 10000.0 )
        center = stackpane {
           scatterchart("", xAxis, yAxis) {
               series("R&D") {
                   dataSet.forEach {
                       data(it.rnd, it.profit)
                   }
               }
               series("Marketing") {
                   dataSet.forEach {
                       data(it.marketing, it.profit)
                   }
               }
               series("Admin") {
                   dataSet.forEach {
                       data(it.admin, it.profit)
                   }
               }
           }
        }
    }
}

@ExperimentalStdlibApi
class MLRchart: App(GraphView::class)