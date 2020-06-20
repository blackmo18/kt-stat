package com.vhl.ds.model

import com.practice.ds.annotation.Category
import com.practice.ds.annotation.DependentVar
import com.vhl.ds.category.ScientificData

data class Company(
    val rnd: Double?,
    val admin: Double?,
    val marketing: Double?,
    @Category
    val state: String?,
    @DependentVar
    val profit: Double?,
    @Category
    val tech: String?
): ScientificData()