package se.vitberget.randomperson.mariahelpers;

import java.util.List;

public class Relations {
    public final List<Long> parents;
    public final Long married;

    public Relations(List<Long> parents, Long married) {
        this.parents = parents;
        this.married = married;
    }
}
