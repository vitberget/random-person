package se.vitberget.randomperson

data class Person(
    val pn: Long,
    val married: Long? = null,
    val parents: List<Long>? = null,
    val firstNames: List<String>,
    val surName: String,
    val streetName: String,
    val streetNumber: Int,
    val city: City,
    val born: Int
) {

    override fun toString(): String {
        return """
            |Person: $pn, born $born${if (married != null) " married to $married" else ""}${if (parents!=null) " child of "+parents.joinToString() else ""}
            |${firstNames.joinToString(separator = " ")} $surName
            |$streetName $streetNumber
            |${city.code} ${city.name}
        """.trimMargin()
    }
}
