package se.vitberget.randomperson

data class Person(
    val pn: Int,
    val married: Int? = null,
    val firstNames: List<String>,
    val surName: String,
    val streetName: String,
    val streetNumber: Int,
    val city: City,
    val age: Int
) {

    override fun toString(): String {

        return """
            Person: $pn ${if (married != null) "married to $married" else ""} age $age 
            ${firstNames.joinToString(separator = " ")} $surName
            $streetName $streetNumber
            ${city.code} ${city.name}
        """.trimIndent()
    }

    fun marriedTo(partner: Person) =
        Person(
            married = partner.pn,

            pn = pn,
            firstNames = firstNames,
            surName = surName,
            streetNumber = streetNumber,
            streetName = streetName,
            city = city,
            age = age
        )
}

