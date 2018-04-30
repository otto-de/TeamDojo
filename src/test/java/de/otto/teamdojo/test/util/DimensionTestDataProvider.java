package de.otto.teamdojo.test.util;

import de.otto.teamdojo.domain.Dimension;

import javax.persistence.EntityManager;

public class DimensionTestDataProvider {

    private static final String DESCRIPTION = " Description";

    private static final String SECURITY = "Security";
    public static final String SECURITY_NAME = SECURITY;
    public static final String SECURITY_DESC = SECURITY + DESCRIPTION;
    private static final String OPERATIONS = "Operations";
    public static final String OPERATIONS_NAME = OPERATIONS;
    public static final String OPERATIONS_DESC = OPERATIONS + DESCRIPTION;


    public static Dimension security(EntityManager em) {
        return persistDimension(security(), em);
    }

    public static Dimension security() {
        return new Dimension().name(SECURITY_NAME).description(SECURITY_DESC);
    }

    public static Dimension operations(EntityManager em) {
        return persistDimension(operations(), em);
    }

    public static Dimension operations() {
        return new Dimension().name(OPERATIONS_NAME).description(OPERATIONS_DESC);
    }

    private static Dimension persistDimension(Dimension dimension, EntityManager em) {
        em.persist(dimension);
        return dimension;
    }
}
