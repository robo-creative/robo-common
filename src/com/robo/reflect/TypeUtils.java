/**
 * Copyright (c) 2016 Robo Creative - https://robo-creative.github.io.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.robo.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Provides utilities for working with classes and interfaces.
 *
 * @author robo-admin
 */
public final class TypeUtils {

    private TypeUtils() {

    }

    /**
     * Fetches all interfaces of a specified class, including interfaces of its supertypes.
     *
     * @param type       Type to get interfaces.
     * @param interfaces A collection that interfaces are fetched to. If null, a new collection will be created.
     * @return The interfaces.
     */
    public static Collection<Class<?>> fetchAllInterfaces(Class<?> type, Collection<Class<?>> interfaces) {
        if (null == interfaces) {
            interfaces = new ArrayList<>();
        }
        if (null == type) {
            return interfaces;
        }
        Class<?> superType = type;
        while (null != superType) {
            Class<?>[] superTypeInterfaces = superType.getInterfaces();
            for (Class<?> superTypeInterface : superTypeInterfaces) {
                if (!interfaces.contains(superTypeInterface)) {
                    interfaces.add(superTypeInterface);
                }
            }
            superType = superType.getSuperclass();
        }
        return interfaces;
    }

    /**
     * Fetches all super classes of a specified class in order going up from the class.
     *
     * @param type
     * @param superTypes
     * @return The super classes
     */
    public static Collection<Class<?>> fetchAllSuperTypes(Class<?> type, Collection<Class<?>> superTypes) {
        if (null == superTypes) {
            superTypes = new ArrayList<>();
        }
        if (null == type) {
            return superTypes;
        }
        Class<?> superType = type.getSuperclass();
        while (null != superType) {
            if (!superTypes.contains(superType)) {
                superTypes.add(superType);
            }
            superType = superType.getSuperclass();
        }
        return superTypes;
    }

    /**
     * Gets generic parameter type at specified index of a specified object.
     *
     * @param object The object to examine.
     * @param index  A zero-based index that indicates location of the generic
     *               parameter type in the given object's type.
     * @return If type of the given object is parameterized and the type
     * contains a generic parameter type at the given index, returns the
     * generic parameter type. Otherwise null.
     */
    public static Type getGenericParameterType(Object object, int index) {
        Class<?> objectClass = object.getClass();
        Type genericSuperType = objectClass.getGenericSuperclass();
        if (genericSuperType instanceof ParameterizedType) {
            return ((ParameterizedType) genericSuperType).getActualTypeArguments()[index];
        } else {
            Type[] interfaceTypes = objectClass.getGenericInterfaces();
            for (Type interfaceType : interfaceTypes) {
                if (interfaceType instanceof ParameterizedType) {
                    return ((ParameterizedType) interfaceType).getActualTypeArguments()[index];
                }
            }
        }
        return null;
    }

    /**
     * Checks if a class belongs to one of specified packages.
     *
     * @param type      The class to check.
     * @param packages The packages.
     * @return
     */
    public static boolean isClassInOneOf(Class<?> type, String[] packages) {
        String classPackage = type.getPackage().getName();
        if (null == packages || packages.length == 0)
            return false;
        for (String p : packages) {
            if (classPackage.startsWith(p))
                return true;
        }
        return false;
    }

    public static Class<?> getType(Object object) {
        if (PrimitiveUtils.isWrapperType(object.getClass())) {
            switch (object.getClass().getName()) {
                case "boolean":
                case "java.lang.Boolean":
                    return boolean.class;
                case "byte":
                case "java.lang.Byte":
                    return byte.class;
                case "char":
                case "java.lang.Character":
                    return char.class;
                case "short":
                case "java.lang.Short":
                    return short.class;
                case "int":
                case "java.lang.Integer":
                    return Integer.TYPE;
                case "long":
                case "java.lang.Long":
                    return long.class;
                case "float":
                case "java.lang.Float":
                    return float.class;
                case "double":
                case "java.lang.Double":
                    return double.class;
                default:
                    return void.class;
            }
        } else {
            return object.getClass();
        }
    }
}
