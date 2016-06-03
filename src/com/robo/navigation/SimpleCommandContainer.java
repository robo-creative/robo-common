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
package com.robo.navigation;

import com.robo.reflect.Factory;

/**
 * Implementation of {@link CommandContainer}, uses reflection to resolve
 * command instances.
 * 
 * @author robo-admin
 * 
 */
public class SimpleCommandContainer implements CommandContainer {

	@Override
	public <T> Command<T> resolve(Class<? extends Command<T>> contract) {
		return Factory.createObject(contract);
	}

	@Override
	public <T> Command<T> resolve(Class<? extends Command<T>> contract, String name) {
		throw new UnsupportedOperationException("The default CommandContainer doesn't support resolving commands by name");
	}

}
