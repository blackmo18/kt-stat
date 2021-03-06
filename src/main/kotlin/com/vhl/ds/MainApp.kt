package com.vhl.ds

import com.vhl.ds.category.CategoryKeys
import com.vhl.ds.mlr.DoubleEQ
import com.vhl.ds.mlr.OLSML
import com.vhl.ds.model.Company
import com.vhl.ds.model.Salary
import com.vhl.ds.util.*
import koma.create

@ExperimentalStdlibApi
fun main() {
    //-- parsing data
    val data = dataClassFromCsv<Company>("/Company.csv").toList()

    //-- Multi linear regression without ScientificData class and annotation
    val category1 = CategoryKeys(data)
        .addCategory(key ="state", cat = {it.state!!} )
        .addCategory(key="tech", cat= {it.tech!!})

    val categorizedData = data.categorized(
        category = {
            categorizeByVariable { map ->
                map["state"] = it.state!!
                map["tech"] = it.tech!!
            }
        },
        numeric = {
            doubleArrayOf(it.rnd!!, it.admin!!, it.marketing!!)
        }
    )

    //-- creating array of array of doubles
    val doubleEQ = DoubleEQ(category1.mappedKeys)
    val xD = doubleEQ.createEQ(categorizedData) // independent
    val yD = data.mapNotNull { it.profit }.toDoubleArray() // dependent

    //-- Multi linear regression with ScientificData class and annotation
    val category2 = CategoryKeys(data).initCategoryData()

    //-- creating array of doubles and dependent variable
    val doubleEQ2 = DoubleEQ(category2.getCategoryKeys())

    /**
     * resulting array, array of doubles are arranged alphabetically according to data class property name
     */
    val xW = doubleEQ2.createEQ(category2.getCategorizedData())
    val yW = category2.getDependentValues()

    //-- removing columns for p values not in accepted values
    val matProcessed = create(xD)
    val removedCol = matProcessed.removeColumns(1,0,3)


    val olsml = OLSML(yW, xW)
    val summary = olsml.summary()
    println(summary)
}