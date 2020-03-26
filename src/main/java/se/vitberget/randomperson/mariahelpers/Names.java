package se.vitberget.randomperson.mariahelpers;

import java.util.List;

public class Names {
    final public List<String> firstNames;
    final public String surName;

    public Names(List<String> firstNames, String surName) {
        this.firstNames = firstNames;
        this.surName = surName;
    }
}
