package se.vitberget.randomperson.mariadb

import se.vitberget.randomperson.City
import se.vitberget.randomperson.Person
import java.sql.Connection
import java.sql.Statement

fun insertIntoDB(person: Person, connection: Connection) {
    val id = insertIntoDBPeople(person, connection)
    insertIntoDBName(id, person, connection)
    insertIntoDBAdress(id, person, connection)
    insertIntoDBMarried(id, person, connection)
    insertIntoDBParents(id, person, connection)
}

fun insertIntoDBMarried(id: Long, person: Person, connection: Connection) {
    if (person.married != null) {
        connection.prepareStatement(
            """insert into relation
               (people_id, relation_pn, relation_type)
               values (?,?,?)"""
        ).use {
            it.setLong(1, id)
            it.setLong(2, person.married)
            it.setString(3, "M")

            it.executeUpdate()
        }

    }
}

fun insertIntoDBParents(id: Long, person: Person, connection: Connection) {
    if (person.parents != null) {
        connection.prepareStatement(
            """insert into relation
               (people_id, relation_pn, relation_type)
               values (?,?,?)"""
        ).use {
            it.setLong(1, id)
            it.setString(3, "P")

            person.parents.forEach {
                p -> it.setLong(2, p)
                it.executeUpdate()
            }
        }
    }
}

fun insertIntoDBName(id: Long, person: Person, connection: Connection) {
    connection.prepareStatement(
        """insert into name
           (people_id, first_names, sur_name)
           values (?,?,?) """
    ).use {
        it.setLong(1, id)
        it.setString(2, person.firstNames.joinToString(separator = " "))
        it.setString(3, person.surName)

        it.execute()
    }
}

fun insertIntoDBAdress(id: Long, person: Person, connection: Connection) {
    connection.prepareStatement(
        """insert into adress 
           (people_id, street_name, street_number, city_name, city_number)
           values (?,?,?,?,?) """
    ).use {
        it.setLong(1, id)
        it.setString(2, person.streetName)
        it.setInt(3, person.streetNumber)
        it.setString(4, person.city.name)
        it.setInt(5, person.city.code)

        it.executeUpdate()
    }
}

fun insertIntoDBPeople(person: Person, connection: Connection): Long {
    connection.prepareStatement(
        "insert into people (pn,born) values (?,?)",
        Statement.RETURN_GENERATED_KEYS
    ).use {
        it.setLong(1, person.pn)
        it.setInt(2, person.born)

        it.executeUpdate()

        val generatedKeys = it.generatedKeys
        generatedKeys.next()
        return generatedKeys.getLong(1)
    }
}

fun main(args: Array<String>) {
    val c = getConnection()
    val p = Person(
        firstNames = listOf("Arne"),
        surName = "Banan",
        pn = 1234567L,
        married = 7654321L,
        born = 1977,
        streetName = "Homer strett",
        streetNumber = 1,
        city = City(name = "MyCity", code = 12345)
    )

    insertIntoDB(p, c)
}
