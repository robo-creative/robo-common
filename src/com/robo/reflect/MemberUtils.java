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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Provides utilities for working with class members.
 *
 * @author robo-admin
 */
public final class MemberUtils {

    private MemberUtils() {

    }

    /**
     * Invokes a method of a specified object.
     *
     * @param target       The target object.
     * @param methodName   Name of the method to be invoked.
     * @param throwOnError If specified, all possible exceptions which might occur in
     *                     accessing method will be thrown. Note that all possible
     *                     exceptions which might occur inside the target method will
     *                     still be thrown regardless of this option.
     * @param parameters   An array of parameters that is given to the method.
     * @return Result of method invocation.
     * @throws ReflectionException If the given method could not be found, or the method is not accessible.
     */
    public static Object invokeMethod(Object target, String methodName, boolean throwOnError, Object... parameters)
            throws ReflectionException {
        Object invocationResult = null;
        try {
            Method method;
            if (parameters.length == 0) {
                method = target.getClass().getMethod(methodName);
                invocationResult = method.invoke(target);
            } else {
                Class<?>[] parameterTypes = new Class<?>[parameters.length];
                for (int i = 0; i < parameters.length; i++) {
                    parameterTypes[i] = TypeUtils.getType(parameters[i]);
                }
                method = getMethod(target, methodName, parameterTypes);
                Guard.isNotNull(method, ReflectionException.class, String.format("No such method found: %s.%s",
                        target.getClass().getName(), methodName));
                invocationResult = method.invoke(target, parameters);
            }
        } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            if (throwOnError) {
                throw new ReflectionException(e);
            }
        }
        return invocationResult;
    }

    /**
     * Gets a method from a type with specified signature.
     *
     * @param target         The target type that declares the method.
     * @param methodName     Method name.
     * @param parameterTypes Types of parameters in order.
     * @return The method if found. Otherwise null.
     */
    public static Method getMethod(Object target, String methodName, Class<?>[] parameterTypes) {
        Method[] methods = target.getClass().getMethods();
        for (Method method : methods) {
            if (methodName.equals(method.getName())) {
                Class<?>[] methodParamTypes = method.getParameterTypes();
                if (methodParamTypes.length == parameterTypes.length) {
                    for (int i = 0; i < methodParamTypes.length; i++) {
                        if (!methodParamTypes[i].isAssignableFrom(parameterTypes[i])) {
                            continue;
                        }
                    }
                    return method;
                } else {
                    continue;
                }
            }
        }
        return null;
    }

    /**
     * Gets declared constructor of a specified type.
     *
     * @param type
     * @param parameterTypes
     * @return The constructor if found. Otherwise null.
     */
    public static Constructor<?> getDeclaredConstructor(Class<?> type, Class<?>[] parameterTypes) {
        Constructor<?>[] constructors = type.getConstructors();
        for (Constructor<?> constructor : constructors) {
            Class<?>[] constructorParamTypes = constructor.getParameterTypes();
            if (constructorParamTypes.length == parameterTypes.length) {
                for (int i = 0; i < parameterTypes.length; i++) {
                    if (!constructorParamTypes[i].isAssignableFrom(parameterTypes[i])) {
                        continue;
                    }
                    return constructor;
                }
            }
        }
        return null;
    }
}
