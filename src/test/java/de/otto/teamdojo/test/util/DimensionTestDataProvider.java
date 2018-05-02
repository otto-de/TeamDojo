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


    public static DimensionBuilder dimension(String name) {
        return new DimensionBuilder(name);
    }

    public static DimensionBuilder security() {
        return dimension(SECURITY_NAME).description(SECURITY_DESC);
    }


    public static DimensionBuilder operations() {
        return dimension(OPERATIONS_NAME).description(OPERATIONS_DESC);
    }


    public static class DimensionBuilder {
        private final String name;
        private String description;


        public DimensionBuilder(String name) {
            this.name = name;
        }

        public DimensionBuilder description(String description) {
            this.description = description;
            return this;
        }

        public Dimension build(EntityManager em) {
            Dimension dimension = build();
            em.persist(dimension);
            return dimension;
        }

        public Dimension build() {
            return new Dimension().name(name).description(description);
        }
    }
}
