/*
 * MIT License
 *
 * Copyright (c) 2023 Dennis Ochulor
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.dennisochulor.playwright_java_multithread;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.concurrent.ThreadFactory;

import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Playwright.CreateOptions;

@Internal
final class InternalPlaywrightThreadFactory implements ThreadFactory {
	
	private final Class<? extends InternalPlaywrightThread> clazz;
	private final CreateOptions createOptions;
	private final LaunchOptions launchOptions;

	InternalPlaywrightThreadFactory(Class<? extends InternalPlaywrightThread> clazz,CreateOptions createOptions,LaunchOptions launchOptions) {
		this.clazz = Objects.requireNonNull(clazz);
		this.createOptions = Objects.requireNonNull(createOptions, "createOptions");
		this.launchOptions = Objects.requireNonNull(launchOptions, "launchOptions");
	}

	@Override
	public final Thread newThread(Runnable r) {
		try {
			return clazz.getConstructor(Runnable.class,CreateOptions.class,LaunchOptions.class)
						.newInstance(r,createOptions,launchOptions);
		} 
		catch (InstantiationException e) {
			throw new RuntimeException("Cannot instantiate an abstract class!",e);
		} 
		catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}  
		catch (InvocationTargetException e) {
			throw new RuntimeException(e.getCause());
		} 
		catch (NoSuchMethodException e) {
			throw new RuntimeException("Constuctor " + e.getLocalizedMessage() + " must be public!",e);
		}
	}

}
