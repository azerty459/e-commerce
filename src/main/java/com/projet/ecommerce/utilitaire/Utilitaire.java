package com.projet.ecommerce.utilitaire;

import java.lang.reflect.Field;

public class Utilitaire {
    /**
     * Fusionne les deux objets en paramètre en un seul et mes les valeurs à jour s'il y a des différences.
     *
     * @param first  Le premier objet à fusionner
     * @param second Le deuxième à fusionner
     * @return l'objet fusionné
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> T mergeObjects(T first, T second) throws IllegalAccessException, InstantiationException {
        Class<?> clazz = first.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Object returnValue = clazz.newInstance();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value1 = field.get(first);
            Object value2 = field.get(second);
            Object value = (value1 != null) ? value1 : value2;
            field.set(returnValue, value);
        }
        return (T) returnValue;
    }
}
