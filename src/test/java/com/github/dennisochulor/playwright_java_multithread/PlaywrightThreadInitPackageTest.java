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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;

final class PlaywrightThreadInitPackageTest {
	
	@Test
	void throwNPEIfPlaywrightIsNull() {
		Playwright playwright = Playwright.create();
		Browser browser = playwright.chromium().launch();
		Assertions.assertThrowsExactly(NullPointerException.class,
				   () -> new PlaywrightThreadInitPackage(null,browser,null,null));
	}
	
	@Test
	void throwNPEIfAllBrowsersAreNull() {
		Playwright playwright = Playwright.create(); 	
		Assertions.assertThrowsExactly(NullPointerException.class,
				   () -> new PlaywrightThreadInitPackage(playwright,null,null,null));
	}
	
	@Test
	void ExpectedChromiumButReceivedFirefox() {
		Playwright playwright = Playwright.create();
		Browser browser = playwright.firefox().launch();
		Assertions.assertThrowsExactly(IllegalArgumentException.class,
				   () -> new PlaywrightThreadInitPackage(playwright,browser,null,null));
	}
	
	@Test
	void ExpectedChromiumButReceivedWebkit() {
		Playwright playwright = Playwright.create();
		Browser browser = playwright.webkit().launch();
		Assertions.assertThrowsExactly(IllegalArgumentException.class,
				   () -> new PlaywrightThreadInitPackage(playwright,browser,null,null));
	}
	
	@Test
	void ExpectedFirefoxButReceivedChromium() {
		Playwright playwright = Playwright.create();
		Browser browser = playwright.chromium().launch();
		Assertions.assertThrowsExactly(IllegalArgumentException.class,
				   () -> new PlaywrightThreadInitPackage(playwright,null,browser,null));
	}
	
	@Test
	void ExpectedFirefoxButReceivedWebkit() {
		Playwright playwright = Playwright.create();
		Browser browser = playwright.webkit().launch();
		Assertions.assertThrowsExactly(IllegalArgumentException.class,
				   () -> new PlaywrightThreadInitPackage(playwright,null,browser,null));
	}
	
	@Test
	void ExpectedWebkitButReceivedChromium() {
		Playwright playwright = Playwright.create();
		Browser browser = playwright.chromium().launch();
		Assertions.assertThrowsExactly(IllegalArgumentException.class,
				   () -> new PlaywrightThreadInitPackage(playwright,null,null,browser));
	}
	
	@Test
	void ExpectedWebkitButReceivedFirefox() {
		Playwright playwright = Playwright.create();
		Browser browser = playwright.firefox().launch();
		Assertions.assertThrowsExactly(IllegalArgumentException.class,
				   () -> new PlaywrightThreadInitPackage(playwright,null,null,browser));
	}
	
}
