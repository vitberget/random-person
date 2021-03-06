package se.vitberget.randomperson.mariadb

import java.sql.Connection
import java.sql.DriverManager
import java.util.*


fun dropDBSchema(con: Connection) {
    con.createStatement().use {
        it.executeUpdate("DROP TABLE IF EXISTS adress")
        it.executeUpdate("DROP TABLE IF EXISTS name")
        it.executeUpdate("DROP TABLE IF EXISTS relation")
        it.executeUpdate("DROP TABLE IF EXISTS people")
    }
}

fun initDBSchema(con: Connection) {
    createTablePeople(con)
    createTableAdress(con)
    createTableRelation(con)
    createTableName(con)
}

fun getDBConnection(props: Properties): Connection {
    return DriverManager.getConnection(
        "jdbc:mariadb://${props.getProperty("mariadb.host")}/${props.getProperty("mariadb.schema")}",
        props.getProperty("mariadb.username"),
        props.getProperty("mariadb.password")
    )
}


fun createTableAdress(connection: Connection) {
    connection.createStatement().use {
        it.executeUpdate(
            """create table if not exists adress  
            (
                id int auto_increment,
                people_id int not null,
                street_name varchar(100) null,
                street_number int null,
                city_name varchar(100) null,
                city_number int null,
                
                constraint adress_pk primary key (id),
                constraint adress_ppl_id_fk foreign key (people_id) references people (id)
            ); 
         """
        )
    }
}

fun createTableName(connection: Connection) {
    connection.createStatement().use {
        it.executeUpdate(
            """create table if not exists name
            (
                id int auto_increment,
                people_id int not null,
                first_names varchar(100) null,
                sur_name varchar(100) null,
                
                constraint name_pk primary key (id),
                constraint name_ppl_id_fk foreign key (people_id) references people (id)
            ); 
         """
        )
    }
}

fun createTableRelation(connection: Connection) {
    connection.createStatement().use {
        it.executeUpdate(
            """create table if not exists relation
            (
                id int auto_increment,
                people_id int not null,
                
                relation_pn long not null,
                relation_type varchar(2) not null,
                
                constraint relation_pk primary key (id),
                constraint relation_ppl_id_fk foreign key (people_id) references people (id)
            ); 
         """
        )
    }
}

fun createTablePeople(connection: Connection) {
    connection.createStatement().use {
        it.executeUpdate(
            """create table if not exists people
            (
                id int auto_increment,
                pn bigint not null,
                born int not null,
                
                constraint people_pk primary key (id)
            );
        """
        )

        it.executeUpdate("create unique index if not exists people_pn_uindex on people (pn);")
    }
}


