package se.vitberget.randomperson.mariahelpers;

import se.vitberget.randomperson.domain.City;

public class Adress {
    public final String streetName;
    public final Integer streetNumber;
    public final City city;

    public Adress(String streetName, Integer streetNumber, City city) {
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.city = city;
    }
}
