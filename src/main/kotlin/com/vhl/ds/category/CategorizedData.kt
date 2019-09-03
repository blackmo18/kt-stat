package com.vhl.ds.category

data class CategorizedData(
    val category: Map<String, Any>,
    val dobs: DoubleArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CategorizedData

        if (category != other.category) return false
        if (!dobs.contentEquals(other.dobs)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = category.hashCode()
        result = 31 * result + dobs.contentHashCode()
        return result
    }
}