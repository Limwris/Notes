package com.nichesoftware.notes.utils;

/**
 * Created by n_che on 05/04/2016.
 */
public final class Assertion {
    /**
     * Constructeur privé
     */
    private Assertion() {
        // Nothing
    }

    /**
     * Méthode permettant de s'assurer de respecter la précondition assumant que l'objet n'est pas nul
     * @param reference
     * @param <T>
     * @return
     */
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new IllegalStateException("L'objet ne doit pas être nul.");
        }
        return reference;
    }

//    public static void checkNotNull(Object o) {
//        if (o == null) {
//            throw new IllegalStateException("L'objet ne doit pas être nul.");
//        }
//    }
}
