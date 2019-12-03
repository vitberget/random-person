package se.vitberget.randomperson

data class Person(
    val pn: Long,
    val married: Long? = null,
    val firstNames: List<String>,
    val surName: String,
    val streetName: String,
    val streetNumber: Int,
    val city: City,
    val born: Int
) {

    override fun toString(): String {
        return """
            |Person: $pn, born $born, ${if (married != null) "married to $married" else ""}
            |${firstNames.joinToString(separator = " ")} $surName
            |$streetName $streetNumber
            |${city.code} ${city.name}
        """.trimMargin()
    }
}
