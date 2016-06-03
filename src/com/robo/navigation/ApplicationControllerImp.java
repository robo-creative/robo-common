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

/**
 * Default implementation of {@link ApplicationController}.
 * 
 * @author robo-admin
 * 
 */
public class ApplicationControllerImp implements ApplicationController {

	private final CommandContainer mContainer;
	
	public ApplicationControllerImp(CommandContainer commandContainer) {
		mContainer = commandContainer;
	}
	
	@Override
	public <T> void execute(Class<? extends Command<T>> contract, T parameter) {
		execute(mContainer.resolve(contract), parameter);
	}

	@Override
	public <T> void execute(Class<? extends Command<T>> contract, String name, T parameter) {
		execute(mContainer.resolve(contract, name), parameter);
	}

	@Override
	public <T> void execute(Command<T> command, T parameter) {
		command.execute(parameter);
	}
	
}
