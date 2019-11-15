package se.vitberget.randomperson

data class Person(
    val pn : String,
    var married : String? = null,
    val firstNames : List<String>,
    val surName : String,
    val streetName : String,
    val streetNumber : Int,
    val city: City) {
    override fun toString(): String {

        return """
            Person: $pn ${if(married !=null) "married to $married" else ""} 
            ${firstNames.joinToString(separator = " ")} $surName
            $streetName $streetNumber
            ${city.code} ${city.name}
        """.trimIndent()

//        return "Person(firstNames=$firstNames, surName='$surName', streetName='$streetName', streetNumber=$streetNumber, city=$city)"
    }
}