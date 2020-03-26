package se.vitberget.randomperson;

import se.vitberget.randomperson.domain.City;
import se.vitberget.randomperson.domain.Person;
import se.vitberget.randomperson.mariahelpers.Adress;
import se.vitberget.randomperson.mariahelpers.Names;
import se.vitberget.randomperson.mariahelpers.PNAndBorn;
import se.vitberget.randomperson.mariahelpers.Relations;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import static java.lang.System.out;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class ExampleSearchMariaDB {
    public static void main(String[] args) throws SQLException {
        new ExampleSearchMariaDB().doASearch();
    }

    private void doASearch() throws SQLException {
        try (Connection connection = getConnection()) {

            List<Long> ids = searchToIds(connection);

            List<Person> persons = ids.stream()
                    .map(id -> populatePersonFromID(connection, id))
                    .collect(toList());

            persons.forEach(p -> out.println(p));


            int d = 45;
        }
    }

    /**
     * Where we search for surname
     */
    private List<Long> searchToIds(Connection connection) throws SQLException {
        List<Long> ids = new LinkedList<>();

        try (PreparedStatement statement = connection.prepareStatement(
                "select people_id from name where sur_name=?")) {
            out.println("Search for Sunday...");
            statement.setString(1, "Sunday");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ids.add(resultSet.getLong(1));
                }
            }
        }

        return ids;
    }

    private Person populatePersonFromID(Connection connection, Long id) {
        try {
            PNAndBorn pnAndBorn = getPNAndBorn(connection, id);
            Adress adress = getAdress(connection, id);
            Names names = getNames(connection, id);
            Relations relations = getRelations(connection, id);

            return new Person(
                    pnAndBorn.pn,
                    relations.married,
                    relations.parents,
                    names.firstNames,
                    names.surName,
                    adress.streetName,
                    adress.streetNumber,
                    adress.city,
                    pnAndBorn.born
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    private Relations getRelations(Connection connection, Long id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "select relation_pn, relation_type from relation where people_id=?")) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Long> parents = new LinkedList<>();
                Long married = null;
                while (resultSet.next()) {
                    if (resultSet.getString(2).equals("M")) {
                        married = resultSet.getLong(1);
                    } else {
                        parents.add(resultSet.getLong(1));
                    }
                }
                return new Relations(
                        parents.isEmpty() ? null : parents,
                        married);
            }
        }
    }

    private Names getNames(Connection connection, Long id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "select first_names, sur_name from name where people_id=?")) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Names(
                            asList(resultSet.getString(1).split(" ")),
                            resultSet.getString(2));
                }
            }
        }
        return null;
    }

    private PNAndBorn getPNAndBorn(Connection connection, Long id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "select pn, born from people where id=?")) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new PNAndBorn(
                            resultSet.getLong(1),
                            resultSet.getInt(2));
                }
            }
        }
        return null;
    }

    private Adress getAdress(Connection connection, Long id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "select street_name, street_number, city_name, city_number from adress where people_id=?")) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Adress(
                            resultSet.getString(1),
                            resultSet.getInt(2),
                            new City(resultSet.getString(3),
                                    resultSet.getInt(4)));
                }
            }
        }
        return null;
    }

    private Connection getConnection() {
        try {
            Properties properties = Util.getProperties();
            return DriverManager.getConnection(
                    String.format(
                            "jdbc:mariadb://%s:%s/%s",
                            properties.getProperty("mariadb.host"),
                            properties.getProperty("mariadb.port"),
                            properties.getProperty("mariadb.schema")),
                    properties.getProperty("mariadb.username"),
                    properties.getProperty("mariadb.password")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
