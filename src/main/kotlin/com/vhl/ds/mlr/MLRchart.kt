package com.vhl.ds.mlr

import com.vhl.ds.model.Company
import com.vhl.ds.util.dataClassFromCSV
import javafx.scene.chart.NumberAxis
import tornadofx.*

class GraphView: View() {

    val dataSet = dataClassFromCSV("/Company.csv", Company::class.java)
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

class MLRchart: App(GraphView::class)