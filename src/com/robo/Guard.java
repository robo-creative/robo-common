/**
 * Copyright (c) 2016 Robo Creative - https://robo-creative.github.io.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.robo;

import com.robo.reflect.Factory;

/**
 * Provides functionality for validating objects against conditions.
 * 
 * @author robo-admin
 * 
 */
public final class Guard {
    /**
     * Throws an exception of specified type if a specified candidate object is
     * null.
     * 
     * @param candidate
     *            The object to validate.
     * @param exceptionType
     *            Type of the exception to be thrown if the given object is
     *            null.
     * @param message
     *            The error message given to the exception if the exception is
     *            thrown.
     * @throws TException
     *             Type of the exception to be thrown if the given object is
     *             null.
     * @throws ApplicationException
     *             If the expected error cannot be thrown for some reasons.
     */
    public static <TException extends Throwable> void isNotNull(Object candidate,
            Class<TException> exceptionType, String message) throws TException,
            ApplicationException {
        against(null == candidate, exceptionType, message);
    }

    /**
     * Throws an exception of specified type if a specified condition is not
     * satisfied.
     * 
     * @param failCondition
     *            The condition to validate. If false, the exception will be
     *            thrown.
     * @param exceptionType
     *            Type of the exception to be thrown if the given object is
     *            null.
     * @param message
     *            The error message given to the exception if the exception is
     *            thrown.
     * @throws TException
     *             Type of the exception to be thrown if the given object is
     *             null.
     * @throws ApplicationException
     *             If the expected error cannot be thrown for some reasons.
     */
    public static <TException extends Throwable> void against(boolean failCondition,
            Class<TException> exceptionType, String message) throws ApplicationException,
            TException {
        if (failCondition) {
            if (null != message) {
                throw Factory.createObject(exceptionType, message);
            } else {
                throw Factory.createObject(exceptionType);
            }
        }
    }

}
