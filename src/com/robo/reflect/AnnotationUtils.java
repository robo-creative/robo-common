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

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * Provides utilities for working with annotations.
 *
 * @author robo-admin
 */
public final class AnnotationUtils {

    private AnnotationUtils() {

    }

    /**
     * Gets an annotation of specified type that is attached to a specified
     * type.
     *
     * @param type      Target type to examine to get the annotation.
     * @param annotationType  Type of the annotation.
     * @param seekInHierarchy If true, the annotation is searched through given type's
     *                        hierarchy, including its all super classes and interfaces.
     * @param skipPackages    An array of packages which will be skipped from the search.
     * @return The annotation if found. Otherwise null.
     */
    public static <TAnnotation extends Annotation> TAnnotation getAnnotation(Class<?> type,
                                                                             Class<TAnnotation> annotationType,
                                                                             boolean seekInHierarchy,
                                                                             String[] skipPackages) {
        if (TypeUtils.isClassInOneOf(type, skipPackages)) {
            return null;
        }
        TAnnotation annotation = type.getAnnotation(annotationType);
        if (null == annotation && seekInHierarchy) {
            Collection<Class<?>> superTypesIncludingInterfaces = TypeUtils.fetchAllSuperTypes(type, null);
            superTypesIncludingInterfaces = TypeUtils.fetchAllInterfaces(type, superTypesIncludingInterfaces);
            for (Class<?> superType : superTypesIncludingInterfaces) {
                annotation = getAnnotation(superType, annotationType, false, skipPackages);
                if (null != annotation)
                {
                    return annotation;
                }
            }
        }
        return annotation;
    }
}
