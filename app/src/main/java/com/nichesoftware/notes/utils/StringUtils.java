package com.nichesoftware.notes.utils;

/**
 * Created by n_che on 08/04/2016.
 */
public final class StringUtils {
    /**
     * Constructeur privée
     */
    private StringUtils() {
        // Nothing
    }

    /**
     * Renvoie vrai si la chaîne est vide, faux sinon
     * @param string
     * @return vrai si la chaîne est vide, faux sinon
     */
    public static boolean isEmpty(final String string) {
        return (string == null || string.length() == 0);
    }
}
