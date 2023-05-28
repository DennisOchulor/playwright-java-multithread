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

import java.util.Objects;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Playwright.CreateOptions;

/**
 * 
 * This abstract class is a subclass of {@link Thread} that wraps {@link Playwright} and {@link Browser} directly onto a thread 
 * with the purpose of making it easier to multithread with Playwright as it is not thread-safe. The {@link Playwright} and 
 * {@link Browser} instances linked to an instance of this thread will be closed automatically when the thread terminates normally
 * or exceptionally. <br><br>
 * Default implementations of this class are provided via {@link PlaywrightThreadFactory} static methods. Users may optionally 
 * choose to create a {@link PlaywrightThreadFactory#ofCustom(Class) custom implementation} of this class by extending it.
 * 
 * @see <a href="https://playwright.dev/java/docs/multithreading">Multithreading with Playwright</a>
 * @see {@link PlaywrightThreadFactory}
 *
 */
public abstract class PlaywrightThread extends Thread {
	
	private final Playwright playwright;
	private final Browser chromium;
	private final Browser firefox;
	private final Browser webkit;
	private Runnable r;
	
	/**
	 * This method is invoked each time a new instance of this {@link PlaywrightThread} is created. 
	 * @return the user-defined PlaywrightThreadInitPackage
	 * @see PlaywrightThreadInitPackage
	 * @see PlaywrightThreadInitPackage#PlaywrightThreadInitPackage(Playwright, Browser, Browser, Browser)
	 */
	protected abstract PlaywrightThreadInitPackage init();
	
	@Internal
	PlaywrightThreadInitPackage init(CreateOptions createOptions, LaunchOptions launchOptions) {
		throw new UnsupportedOperationException(); // to be overriden by internal classes
	}

	/**
	 * Constructs a new {@link PlaywrightThread} instance using the values from {@link #init()}
	 * @param r The {@link Runnable} to run.
	 */
	public PlaywrightThread(Runnable r) {
		PlaywrightThreadInitPackage initPackage = init();
		this.r = r;
		this.playwright = initPackage.playwright();
		this.chromium = initPackage.chromium();
		this.firefox = initPackage.firefox();
		this.webkit = initPackage.webkit();
	}
	
	@Internal
	PlaywrightThread(Runnable r, CreateOptions createOptions, LaunchOptions launchOptions) {
		PlaywrightThreadInitPackage initPackage = init(createOptions, launchOptions);
		this.r = r;
		this.playwright = initPackage.playwright();
		this.chromium = initPackage.chromium();
		this.firefox = initPackage.firefox();
		this.webkit = initPackage.webkit();
	}


	@Override
	public final void run() {
		try {  // try-with-resources using variables only Java 9+ :(
			if(r != null) {
				r.run();
			}
		}
		
		finally {
			if(chromium != null) chromium.close();
			if(firefox != null) firefox.close();
			if(webkit != null) webkit.close();
			playwright.close();  // cannot be null
		}
	}
	
	/**
	 * Returns the {@link Playwright} instance of this thread.
	 * @return The {@link Playwright} instance
	 */
	public final Playwright playwright() {
		return playwright;
	}
	
	/**
	 * Returns the Chromium {@link Browser} instance of this thread.
	 * @return The Chromium {@link Browser}
	 * @throws NullPointerException If this implementation of {@link PlaywrightThread} does not utilise the Chromium browser.
	 */
	public final Browser chromium() {
		return Objects.requireNonNull(chromium, "The configuration of " + this.getClass().getName() + " does not use chromium.");
	}
	
	/**
	 * Returns the Firefox {@link Browser} instance of this thread.
	 * @return The Firefox {@link Browser}
	 * @throws NullPointerException If this implementation of {@link PlaywrightThread} does not utilise the Firefox browser.
	 */
	public final Browser firefox() {
		return Objects.requireNonNull(firefox, "The configuration of " + this.getClass().getName() + " does not use firefox.");
	}
	
	/**
	 * Returns the Webkit {@link Browser} instance of this thread.
	 * @return The Webkit {@link Browser}
	 * @throws NullPointerException If this implementation of {@link PlaywrightThread} does not utilise the Webkit browser.
	 */
	public final Browser webkit() {
		return Objects.requireNonNull(webkit, "The configuration of " + this.getClass().getName() + " does not use webkit.");
	}
	
	
}
