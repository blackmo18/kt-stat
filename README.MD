# Data Science Project in Kotlin
* Mutli Linear Regression
* Simple  Linear Regression

**Libraries Used**
* [OpenCSV](http://opencsv.sourceforge.net/) - library for parsing csv file to classes
* [Koma](https://github.com/kyonifer/koma) - library for scientific computing
* [Apache Commons Math](http://commons.apache.org/proper/commons-math/) - OLM Multi Linear Regression
* [Kotlin-Statistics](https://github.com/thomasnield/kotlin-statistics) - Idiomatic math and statistical extensions for Kotlin   

**Flavors**
* Idiomatic Categorization
* Annotation Categorization

**Usage:**
**a.** Idiomatic Appraoch
1.  *create data class*
2. *parse csv file with data class*
3. *categorized using extension function*
4. *create category keys*
5. *create array of **doublearray(matrix equation)** for independent variables*
6. *create array of double for independent variables*
7. *feed arrays to **OLSML***

**b.** Class Annotation
1. *create a data class*
2. *extend data class with **ScientificData class***
3. *mark class property with **annotation***
4. *parse csv file with data class*
5. *categorize and create keys by instantiating and initializing **CategoryKey***
6. *retrieve category keys, and dependent, indepedent array of:  **doubles, array of doubles***
7. *feed arrays to **OLSML***

**c.** Annoations
1. @Category 
    - identifies that the property is a category variable

2. @Dependent 
    - mark the property as dependent variable
    - make sure that there is only one dependent variable annotated

Creating ScientificData class
```kotlin
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
```

Parsing data to ScientificData class from resource Folder
```kotlin
val data = dataClassFromCSV("/Company.csv", Company::class.java)
```

using idiomatic approach
```kotlin
    //-- Multi linear regression without ScientificData class and annotation
    val category1 = CategoryKeys(data)
        .addCategory(key ="state", cat = {it.state!!} )
    // categorizing data sets
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
    //-- categorizing data sets
    val category2 = CategoryKeys(data).initCategoryData()

    //-- creating array of array of doubles
    val doubleEQ2 = DoubleEQ(category2.getCategoryKeys())

    //-- resulting array, array of doubles are arranged alphabetically according to data class property name
    val xW = doubleEQ2.createEQ(category2.getCategorizedData())
    val yW = category2.getDependentValues()

    val olsml = OLSML(yW, xW)
    val summary = olsml.summary()
```
removing columns from double array eg. backward elimination approach
```kotlin
    val matProcessed = create(arrayDoubleArray)
    val removedCol = matProcessed.removeColumns(1,0,3)
    val arrayVal = removedCol.to2DArray()
```
