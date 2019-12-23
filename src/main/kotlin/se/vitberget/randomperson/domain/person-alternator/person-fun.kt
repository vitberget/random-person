package se.vitberget.randomperson.domain.`person-alternator`

import se.vitberget.randomperson.domain.Person

fun modifyPeople(
    modderFn: PPL2PPL,
    filterFn: (Person) -> Boolean,
    removeThem: Boolean,
    persons: List<Person>,
    times: Int
): List<Person> {
    val modPersons = persons.toMutableList()
    val unModded = persons.filter(filterFn).toMutableList()
    val moddedByMe = mutableListOf<Person>()

    (1..times).forEach {
        if (it % 100 == 0) println("processed $it/$times")

        val p1 = unModded.random()

        if (removeThem) unModded.remove(p1)

        val p2 = unModded.random()

        if (removeThem) {
            unModded.remove(p2)
            modPersons.remove(p1)
            modPersons.remove(p2)
        }

        moddedByMe.addAll(modderFn(listOf(p1, p2)))
    }

    return modPersons + moddedByMe
}


fun <TYPE1, TYPE2, TYPE3, TYPE4, TYPE5, RETURN_TYPE>
        curryFun(theFunction: (TYPE1, TYPE2, TYPE3, TYPE4, TYPE5) -> RETURN_TYPE):
            (TYPE1) -> (TYPE2) -> (TYPE3) -> (TYPE4, TYPE5) -> RETURN_TYPE =
    { param1 ->
        { param2 ->
            { param3 ->
                { param4, param5 ->
                    theFunction(param1, param2, param3, param4, param5)
                }
            }
        }
    }


val curryPeople = curryFun(::modifyPeople)

val marryPpl = curryPeople(::marryCouple)(::isUnmarried)(true)

val marryTraditionalPpl = curryPeople(traditionalCouple)(::isUnmarried)(true)

val makeBabies = curryPeople(::makeBaby)(::isParentMaterial)(false)
