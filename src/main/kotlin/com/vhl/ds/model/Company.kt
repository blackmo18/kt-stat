package com.vhl.ds.model

import com.opencsv.bean.CsvBindByName
import com.practice.ds.annotation.Category
import com.practice.ds.annotation.DependentVar
import com.vhl.ds.category.ScientificData

data class Company(
    @CsvBindByName
    val rnd: Double? = null,

    @CsvBindByName
    val admin: Double? = null,

    @CsvBindByName
    val marketing: Double? = null,

    @CsvBindByName @Category
    val state: String? = null,

    @CsvBindByName @DependentVar
    val profit: Double? = null,

    @CsvBindByName @Category
    val tech: String? = null
): ScientificData()