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

import com.robo.Guard;
import com.sun.scenario.effect.Reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Provides functionality for creating class instances.
 *
 * @author robo-admin
 */
public final class Factory {

    /**
     * Creates an object of specified type.
     *
     * @param type Type of the object to be created.
     * @param args An array of parameters that is given to the constructor of the
     *             object's type.
     * @return The object.
     * @throws ReflectionException If there is no suitable constructor found from the object
     *                             type definition, or the constructor is not accessible outside
     *                             from the given type, or error occurs when invoking the type's
     *                             constructor.
     */
    @SuppressWarnings("unchecked")
    public static <T> T createObject(Class<T> type, Object... args) throws ReflectionException {
        try {
            if (args.length == 0) {
                return type.newInstance();
            } else {
                Class<?>[] parameterTypes = new Class<?>[args.length];
                for (int i = 0; i < args.length; i++) {
                    parameterTypes[i] = TypeUtils.getType(args[i]);
                }
                Constructor<?> constructor = MemberUtils.getDeclaredConstructor(type, parameterTypes);
                Guard.isNotNull(constructor, ReflectionException.class,
                        String.format("No such constructor found: %s.%s", type.getName().toString(), "ctor"));
                return (T) constructor.newInstance(args);
            }
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            throw new ReflectionException(e);
        }
    }


}
