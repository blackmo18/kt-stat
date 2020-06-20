**Data Science Project in Kotlin**
* Mutli Linear Regression
* Simple  Linear Regression

**Libraries Used**
* [Kotlin-grass](https://github.com/blackmo18/kotlin-grass) - kotlin library for parsing csv file to data class
* [Kotlin-csv](https://github.com/doyaaaaaken/kotlin-csv) - kotlin library for reading csv file
* [Koma](https://github.com/kyonifer/koma) - library for scientific computing
* [Apache Commons Math](http://commons.apache.org/proper/commons-math/) - OLM Multi Linear Regression
* [Kotlin-Statistics](https://github.com/thomasnield/kotlin-statistics) - Idiomatic math and statistical extensions for Kotlin   

**Flavors**
* Idiomatic Categorization
* Annotation Categorization

Usage:

*extend data class with **DataScience class***

*mark class property with annotation*

@Category 
- identifies that the property is a category variable

@Dependent 
- mark the property as dependent variable

- make sure that there is only one dependent variable annotated

creating data science class
```kotlin
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
```

Parsing data to data science class from resource Folder
```kotlin
    val data = dataClassFromCsv<Company>("/Company.csv").toList()
```

using idiomatic approach
```kotlin
    //-- Multi linear regression without ScientificData class and annotation
    val category1 = CategoryKeys(data)
        .addCategory(key ="state", cat = {it.state!!} )

    val categorizedData = data.categorized(
        category = {
            categorizeByVariable { map ->
                map["state"] = it.state!!
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
    
    //-- solving multi linear regression
    val olsml = OLSML(yD, xD)
    val summary = olsml.summary()
```
using class annotation
```kotlin
    val category2 = CategoryKeys(data).initCategoryData()

    //-- creating array of doubles and dependent variable
    val doubleEQ2 = DoubleEQ(category2.getCategoryKeys())

    //-- resulting array, array of doubles are arranged alphabetically according to data class property name
    val xW = doubleEQ2.createEQ(category2.getCategorizedData())
    val yW = category2.getDependentValues()

    //-- removing columns for p values not in accepted values
    val matProcessed = create(xD)
    val removedCol = matProcessed.removeColumns(1,0,3)

    val olsml = OLSML(yW, xW)
    val summary = olsml.summary()
```
removing columns from double array eg. backward elimination approach
```kotlin
    val matProcessed = create(arrayDoubleArray)
    val removedCol = matProcessed.removeColumns(1,0,3)
    val arrayVal = removedCol.to2DArray()
```





