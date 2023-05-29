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

package io.github.dennisochulor.playwright_java_multithread;

import java.util.Objects;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;

/**
 * This class is a wrapper object for {@link Playwright} and its {@link Browser} objects.
 */
public final class PlaywrightThreadInitPackage {
	
	private final Playwright playwright;
	private final Browser chromium;
	private final Browser firefox;
	private final Browser webkit;
	
	/**
	 * Constructs a {@link PlaywrightThreadInitPackage} wrapper object.<br>
	 * <b>IMPORTANT NOTE: The parameter order of each {@link Browser} is important!</b> 
	 * 
	 * @param playwright The {@link Playwright} instance.
	 * @param chromium The Chromium {@link Browser}, or {@code null} if not utilised.
	 * @param firefox The Firefox {@link Browser}, or {@code null} if not utilised.
	 * @param webkit The Webkit {@link Browser}, or {@code null} if not utilised.
	 * 
	 * @throws NullPointerException If {@code playwright} is {@code null}, or all three browsers are {@code null}.
	 * @throws IllegalArgumentException If browser parameter order is incorrect.
	 */
	public PlaywrightThreadInitPackage(Playwright playwright, Browser chromium, Browser firefox, Browser webkit) {		
		try {
			this.playwright = Objects.requireNonNull(playwright, "playwright");
			
			if(Objects.isNull(chromium) && Objects.isNull(firefox) && Objects.isNull(webkit)) {
				throw new NullPointerException("At least one browser must be non-null!");
			}
			
			if(chromium != null && !chromium.browserType().name().equals("chromium")) {
				throw new IllegalArgumentException("chromium : Expected Chromium browser! Received "
													+ chromium.browserType().name() + " instead.");
			}
			if(firefox != null && !firefox.browserType().name().equals("firefox")) {
				throw new IllegalArgumentException("firefox : Expected Firefox browser! Received "
													+ firefox.browserType().name() + " instead.");
			}
			if(webkit != null && !webkit.browserType().name().equals("webkit")) {
				throw new IllegalArgumentException("webkit : Expected Webkit browser! Received "
													+ webkit.browserType().name() + " instead.");
			}
			
			this.chromium = chromium;
			this.firefox = firefox;
			this.webkit = webkit;
		}
		
		catch(Throwable t) {
			// close all non-null resources in event of initialisation failure
			if(chromium != null) chromium.close();
			if(firefox != null) firefox.close();
			if(webkit != null) webkit.close();
			if(playwright != null) playwright.close();
			throw t;
		}
	}

	
	public final Playwright playwright() {
		return playwright;
	}

	public final Browser chromium() {
		return chromium;
	}

	public final Browser firefox() {
		return firefox;
	}

	public final Browser webkit() {
		return webkit;
	}

}